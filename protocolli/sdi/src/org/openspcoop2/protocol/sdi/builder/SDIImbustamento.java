/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openspcoop2.protocol.sdi.builder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPPart;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2SoapMessage;
import org.openspcoop2.message.constants.Costanti;
import org.openspcoop2.message.soap.SoapUtils;
import org.openspcoop2.message.utils.MessageUtilities;
import org.openspcoop2.message.xml.XMLUtils;
import org.openspcoop2.protocol.sdi.config.SDIProperties;
import org.openspcoop2.protocol.sdi.config.SDITraduttore;
import org.openspcoop2.protocol.sdi.constants.SDICostanti;
import org.openspcoop2.protocol.sdi.constants.SDICostantiServizioRiceviFile;
import org.openspcoop2.protocol.sdi.constants.SDICostantiServizioRiceviNotifica;
import org.openspcoop2.protocol.sdi.utils.SDICompatibilitaNamespaceErrati;
import org.openspcoop2.protocol.sdi.utils.SDIFatturaUtils;
import org.openspcoop2.protocol.sdi.utils.SDIUtils;
import org.openspcoop2.protocol.sdi.validator.SDIValidazioneUtils;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.protocol.sdk.Eccezione;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.ProtocolException;
import org.openspcoop2.protocol.sdk.state.IState;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.xml.AbstractValidatoreXSD;
import org.openspcoop2.utils.xml.AbstractXMLUtils;

import it.gov.fatturapa.sdi.messaggi.v1_0.constants.TipiMessaggi;

/**
 * SDIImbustamento
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class SDIImbustamento {

	private SDIBustaBuilder bustaBuilder = null;
	private AbstractXMLUtils xmlUtils = null;
	private SDIValidazioneUtils sdiUtils = null;
	private SDITraduttore sdiTraduttore = null;
	private SDIProperties sdiProperties = null;
	public SDIImbustamento(SDIBustaBuilder bustaBuilder) throws ProtocolException{
		this.bustaBuilder = bustaBuilder;
		this.xmlUtils = XMLUtils.getInstance();
		this.sdiUtils = new SDIValidazioneUtils(bustaBuilder.getProtocolFactory());
		this.sdiTraduttore = (SDITraduttore) bustaBuilder.getProtocolFactory().createTraduttore();
		this.sdiProperties = SDIProperties.getInstance(bustaBuilder.getProtocolFactory().getLogger());
	}
	
	public SOAPElement creaRichiesta_ServizioSdIRiceviFile_AzioneRiceviFile(IProtocolFactory<?> protocolFactory, IState state, Busta busta, OpenSPCoop2Message msgParam) throws ProtocolException{
		
		try{
			OpenSPCoop2SoapMessage msg = msgParam.castAsSoap();
					
			// create body
			SOAPPart soapPart = msg.getSOAPPart();
			SOAPBody soapBody = msg.getSOAPBody();
			if(soapBody==null){
				soapBody = soapPart.getEnvelope().addBody();
			}
			
		
			// !!NOTA!! : Per comprendere cosa sia presente nel soapBody e negli attachments vedere org.openspcoop2.protocol.sdi.utils.PreInRequestHandler
			
			
			// childElement
			List<SOAPElement> childs = SoapUtils.getNotEmptyChildSOAPElement(soapBody);
			if(childs==null || childs.size()<=0){
				throw new Exception("FatturaPA non presente");
			}
			if(childs.size()>1){
				throw new Exception("Sono presenti piu' elementi xml. Deve essere fornita una singola FatturaPA (o file ZIP)");
			}
			SOAPElement fatturaSOAPElement = childs.get(0);
			
			
			
			// tipoInvioFattura
			String tipoInvioFattura = null;
			byte[] fatturaAllegata = null;
			String idPaese = null;
			String idCodice = null;
			String versioneFattura = null;
			if(Costanti.SOAP_TUNNEL_NAMESPACE.equals(fatturaSOAPElement.getNamespaceURI())){
				
				// Grazie alla classe org.openspcoop2.protocol.sdi.utils.PreInRequestHandler entreremo qua nel caso ZIP o P7M
				
				// versioneFattura
				if(msg.getTransportRequestContext()!=null){
					versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_VERSIONE_FATTURA);
					if(versioneFattura==null){
						versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_VERSIONE_FATTURA_1);
					}
					if(versioneFattura==null){
						versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_VERSIONE_FATTURA_2);
					}
				}
				if(versioneFattura==null){
					throw new Exception("Versione non fornita (indicare una delle seguenti versioni: "+SDICostanti.SDI_VERSIONI_FATTURA+")");
				}
				versioneFattura = versioneFattura.trim().toUpperCase();
				if(SDICostanti.SDI_VERSIONI_FATTURA.contains(versioneFattura)==false){
					throw new Exception("Versione fornita ["+versioneFattura+"] non supportata (indicare una delle seguenti versioni: "+SDICostanti.SDI_VERSIONI_FATTURA+")");
				}
				
				// idPaese
				if(msg.getTransportRequestContext()!=null){
					idPaese =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_ID_PAESE);
					if(idPaese==null){
						idPaese =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_ID_PAESE_1);
					}
					if(idPaese==null){
						idPaese =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_ID_PAESE_2);
					}
				}
				if(idPaese==null){
					throw new Exception("IdPaese non fornito");
				}
				
				// idCodice
				if(msg.getTransportRequestContext()!=null){
					idCodice =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_ID_CODICE);
					if(idCodice==null){
						idCodice =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_ID_CODICE_1);
					}
					if(idCodice==null){
						idCodice =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_ID_CODICE_2);
					}
				}
				if(idCodice==null){
					throw new Exception("IdCodice non fornito");
				}
				
				// nomeFileFattura
				String tipoFileFattura = null;
				if(msg.getTransportRequestContext()!=null){
					tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_TIPO_FILE);
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_TIPO_FILE.toLowerCase());
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_TIPO_FILE.toUpperCase());
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_1);
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_1.toLowerCase());
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_1.toUpperCase());
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_2);
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_2.toLowerCase());
					}
					if(tipoFileFattura==null){
						tipoFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_TIPO_FILE_2.toUpperCase());
					}
				}
				if(tipoFileFattura==null){
					throw new Exception("TipoFileFattura non fornito");
				}
				if(SDICostanti.SDI_TIPO_FATTURA_XML.equalsIgnoreCase(tipoFileFattura)){
					tipoInvioFattura = SDICostanti.SDI_TIPO_FATTURA_XML;
				}
				else if(SDICostanti.SDI_TIPO_FATTURA_ZIP.equalsIgnoreCase(tipoFileFattura)){
					tipoInvioFattura = SDICostanti.SDI_TIPO_FATTURA_ZIP;
				}
				else if(SDICostanti.SDI_TIPO_FATTURA_P7M.equalsIgnoreCase(tipoFileFattura)){
					tipoInvioFattura = SDICostanti.SDI_TIPO_FATTURA_P7M;
				}
				else{
					throw new Exception("TipoFileFattura fornito non supportato: "+tipoInvioFattura);
				}
				
				if(msg.countAttachments()<=0){
					throw new Exception("Atteso fattura come attachment");
				}
				AttachmentPart ap = (AttachmentPart) msg.getAttachments().next();
				fatturaAllegata = Utilities.getAsByteArray(ap.getDataHandler().getInputStream());
			}
			else{
				
				tipoInvioFattura = SDICostanti.SDI_TIPO_FATTURA_XML;
				
				// versioneFattura
				if(msg.getTransportRequestContext()!=null){
					versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_URLBASED_VERSIONE_FATTURA);
					if(versioneFattura==null){
						versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_VERSIONE_FATTURA_1);
					}
					if(versioneFattura==null){
						versioneFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviFile.RICEVI_FILE_INTEGRAZIONE_TRASPORTO_VERSIONE_FATTURA_2);
					}
				}
				if(versioneFattura==null){
					if(it.gov.fatturapa.sdi.fatturapa.v1_0.utils.ProjectInfo.getInstance().getProjectNamespace().equals(fatturaSOAPElement.getNamespaceURI())){
						versioneFattura = SDICostanti.SDI_VERSIONE_FATTURA_PA_10;
					}
					else if(it.gov.fatturapa.sdi.fatturapa.v1_1.utils.ProjectInfo.getInstance().getProjectNamespace().equals(fatturaSOAPElement.getNamespaceURI())){
						versioneFattura = SDICostanti.SDI_VERSIONE_FATTURA_PA_11;
					}
					else{
						// non e' possibile capire la versione dal namespace, la versione 1.2 contiene due versioni interne, una per private ed una per PA
						throw new Exception("Versione non fornita (indicare una delle seguenti versioni: "+SDICostanti.SDI_VERSIONI_FATTURA+")");
					}
				}
				else{
					if(SDICostanti.SDI_VERSIONI_FATTURA.contains(versioneFattura)==false){
						throw new Exception("Versione fornita ["+versioneFattura+"] non supportata (indicare una delle seguenti versioni: "+SDICostanti.SDI_VERSIONI_FATTURA+")");
					}
					// check in accordo alla versione
					if(SDICostanti.SDI_VERSIONE_FATTURA_PA_10.equals(versioneFattura)){
						if(!it.gov.fatturapa.sdi.fatturapa.v1_0.utils.ProjectInfo.getInstance().getProjectNamespace().equals(fatturaSOAPElement.getNamespaceURI())){
							throw new Exception("Versione fornita ["+versioneFattura+"] non è utilizzabile con la fattura fornita che appartiene ad una differente versione di namespace ["+fatturaSOAPElement.getNamespaceURI()+"]");
						}
					}
					else if(SDICostanti.SDI_VERSIONE_FATTURA_PA_11.equals(versioneFattura)){
						if(!it.gov.fatturapa.sdi.fatturapa.v1_1.utils.ProjectInfo.getInstance().getProjectNamespace().equals(fatturaSOAPElement.getNamespaceURI())){
							throw new Exception("Versione fornita ["+versioneFattura+"] non è utilizzabile con la fattura fornita che appartiene ad una differente versione di namespace ["+fatturaSOAPElement.getNamespaceURI()+"]");
						}
					}
					else{
						if(!it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.utils.ProjectInfo.getInstance().getProjectNamespace().equals(fatturaSOAPElement.getNamespaceURI())){
							throw new Exception("Versione fornita ["+versioneFattura+"] non è utilizzabile con la fattura fornita che appartiene ad una differente versione di namespace ["+fatturaSOAPElement.getNamespaceURI()+"]");
						}
					}
				}
				
			}
			
			// Add Property in Busta
			busta.addProperty(SDICostanti.SDI_BUSTA_EXT_FORMATO_ARCHIVIO_INVIO_FATTURA, tipoInvioFattura);
			busta.addProperty(SDICostanti.SDI_BUSTA_EXT_VERSIONE_FATTURA_PA, versioneFattura);
			

			
			// Leggo Fattura
			byte[]fatturaBytes = fatturaAllegata;
			if(fatturaBytes==null && SDICostanti.SDI_TIPO_FATTURA_XML.equals(tipoInvioFattura)){
				try{
					fatturaBytes = this.xmlUtils.toByteArray(fatturaSOAPElement);
				}catch(Exception e){
					throw new Exception("Fattura non valida: "+e.getMessage(),e);
				}
			}
			
			
			
			// effettuo validazione del messaggio ricevuto
			if(this.sdiProperties.isEnableValidazioneXsdFattura()){
				AbstractValidatoreXSD validatore = null;
				try{			
					if(SDICostanti.SDI_VERSIONE_FATTURA_PA_10.equals(versioneFattura)){
						validatore = it.gov.fatturapa.sdi.fatturapa.v1_0.utils.XSDValidatorWithSignature.getOpenSPCoop2MessageXSDValidator(this.bustaBuilder.getProtocolFactory().getLogger());
					}
					else if(SDICostanti.SDI_VERSIONE_FATTURA_PA_11.equals(versioneFattura)){
						validatore = it.gov.fatturapa.sdi.fatturapa.v1_1.utils.XSDValidatorWithSignature.getOpenSPCoop2MessageXSDValidator(this.bustaBuilder.getProtocolFactory().getLogger());
					}
					else{
						validatore = it.gov.agenziaentrate.ivaservizi.docs.xsd.fatture.v1_2.utils.XSDValidatorWithSignature.getOpenSPCoop2MessageXSDValidator(this.bustaBuilder.getProtocolFactory().getLogger());
					}
				}catch(Exception e){
					throw new Exception("Inizializzazione schema per validazione fattura non riuscita: "+e.getMessage(),e);
				}
				if(SDICostanti.SDI_TIPO_FATTURA_XML.equals(tipoInvioFattura)){
					try{
						// Fix Validazione versione
						// axiom non funziona
	//					Attr attr = fatturaSOAPElement.getAttributeNode("versione");
	//					if(attr!=null){
	//						fatturaSOAPElement.removeAttributeNode(attr);
	//						Node nAttr = soapPart.createAttributeNS(null, "versione");
	//						nAttr.setNodeValue(attr.getNodeValue());
	//						if(fatturaSOAPElement.getAttributes()!=null){
	//							fatturaSOAPElement.getAttributes().setNamedItem(nAttr);
	//						}
	//					}
	//					validatore.valida(fatturaSOAPElement);
						
						validatore.valida(new ByteArrayInputStream(fatturaBytes));
						
					}catch(Exception e){
						throw new Exception("Fattura non valida: "+e.getMessage(),e);
					}
				}else{
					// TODO: capirlo aprendo lo zip o ignorarlo per p7m
				}
			}
			
			
			// Leggo Fattura
			if(SDICostanti.SDI_TIPO_FATTURA_XML.equals(tipoInvioFattura)){
				try{
					List<Eccezione> erroriValidazione = new ArrayList<Eccezione>();
					boolean forceDisableValidazioneXsd = true; // la validazione se abilitata e' stata fatta prima
					SDIFatturaUtils.validazioneFattura(fatturaBytes,this.sdiProperties,
							erroriValidazione,
							this.sdiUtils,protocolFactory,
							busta,msg,false,false,forceDisableValidazioneXsd);
					if(erroriValidazione!=null && erroriValidazione.size()>0){
						StringBuffer bf = new StringBuffer();
						for(int k=0; k<erroriValidazione.size();k++){
							Eccezione error = erroriValidazione.get(k);
							try{
								bf.append("Processamento["+this.sdiTraduttore.toString(error.getCodiceEccezione(),error.getSubCodiceEccezione())+
										"] "+error.getDescrizione(protocolFactory)+"\n");
							}catch(Exception e){
								protocolFactory.getLogger().error("getDescrizione Error:"+e.getMessage(),e);
							}
						}
						throw new Exception(bf.toString());
					}
				}catch(Exception e){
					throw new Exception("Fattura non valida: "+e.getMessage(),e);
				}
			}else{
				// TODO: capirlo aprendo lo zip o ignorarlo per p7m
			}
			
			
			// Costruisco Messaggio Protocollo
			it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.ObjectFactory of = new it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.ObjectFactory();
			it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.FileSdIBaseType fileSdi = new it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.FileSdIBaseType();
			fileSdi.setFile(fatturaBytes);
			if(SDICostanti.SDI_TIPO_FATTURA_XML.equals(tipoInvioFattura)){
				fileSdi.setNomeFile(SDIUtils.getNomeFileFattura(protocolFactory, state, 
						busta.getProperty(SDICostanti.SDI_BUSTA_EXT_TRASMITTENTE_ID_PAESE),
						busta.getProperty(SDICostanti.SDI_BUSTA_EXT_TRASMITTENTE_ID_CODICE),
						tipoInvioFattura));
			}else{
				fileSdi.setNomeFile(SDIUtils.getNomeFileFattura(protocolFactory, state, 
						idPaese,
						idCodice,
						tipoInvioFattura));
			}
			
			// detach body
			soapBody.removeContents();
			
			// se sono presenti degli attachments li elimino
			if(msg.countAttachments()>0){
				msg.removeAllAttachments();
			}
			
			// add MessaggioProtocollo
			it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.utils.serializer.JaxbSerializer serializer = new it.gov.fatturapa.sdi.ws.trasmissione.v1_0.types.utils.serializer.JaxbSerializer();
			String xmlRichiesta = serializer.toString(of.createFileSdIAccoglienza(fileSdi));
			SOAPElement element = SoapUtils.getSoapFactory(msg.getMessageType()).createElement(this.xmlUtils.newElement(xmlRichiesta.getBytes()));
			soapBody.addChildElement(element);
			
			// salvo nomeFile
			busta.addProperty(SDICostanti.SDI_BUSTA_EXT_NOME_FILE, fileSdi.getNomeFile());
			
			// soapAction
			msg.setSoapAction(SDICostantiServizioRiceviFile.SDI_SOAP_ACTION_SERVIZIO_RICEVI_FILE_AZIONE_RICEVI_FILE);
			
			return SDIUtils.readHeader(msg); // l'header ritornato viene usato solo per il tracciamento e cosi' facendo viene ripulito
			
		}catch(Exception e){
			throw new ProtocolException(e.getMessage(),e);
		}
		
	}
	
	public SOAPElement creaRisposta_ServizioRicezioneFatture_AzioneRiceviFatture(IProtocolFactory<?> protocolFactory, IState state, Busta busta, OpenSPCoop2Message msgParam) throws ProtocolException{
		
		try{
			OpenSPCoop2SoapMessage msg = msgParam.castAsSoap();
			
			// create body
			SOAPPart soapPart = msg.getSOAPPart();
			SOAPBody soapBody = msg.getSOAPBody();
			if(soapBody==null){
				soapBody = soapPart.getEnvelope().addBody();
			}
			else{
				if(soapBody.hasFault()){
					return null; // vi e' stato un errore in fase di consegna
				}
			}
			
			// detach body
			soapBody.removeContents();
			
			// se sono presenti degli attachments li elimino
			if(msg.countAttachments()>0){
				msg.removeAllAttachments();
			}
			
			// imposto content type
			msg.setContentType(MessageUtilities.getDefaultContentType(msg.getMessageType()));
			
			// add MessaggioProtocollo
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.ObjectFactory of = new it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.ObjectFactory();
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.RispostaRiceviFattureType rispostaRiceviFatture = of.createRispostaRiceviFattureType();
			rispostaRiceviFatture.setEsito(it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.constants.EsitoRicezioneType.ER01);
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.utils.serializer.JaxbSerializer serializer = new it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.utils.serializer.JaxbSerializer();
			String xmlRisposta = serializer.toString(of.createRispostaRiceviFatture(rispostaRiceviFatture));
			SOAPElement element = SoapUtils.getSoapFactory(msg.getMessageType()).createElement(this.xmlUtils.newElement(xmlRisposta.getBytes()));
			soapBody.addChildElement(element);
			
			return element; // non vi sono base64
						
		}catch(Exception e){
			throw new ProtocolException(e.getMessage(),e);
		}
		
	}
	
	public SOAPElement creaRichiesta_ServizioSdIRiceviNotifica_AzioneNotificaEsito(IProtocolFactory<?> protocolFactory, IState state, Busta busta, OpenSPCoop2Message msgParam,
			boolean isEnableGenerazioneMessaggiCompatibilitaNamespaceSenzaGov,
			boolean isEnableValidazioneMessaggiCompatibilitaNamespaceSenzaGov) throws ProtocolException{
		
		try{
			OpenSPCoop2SoapMessage msg = msgParam.castAsSoap();
		
			// create body
			SOAPPart soapPart = msg.getSOAPPart();
			SOAPBody soapBody = msg.getSOAPBody();
			if(soapBody==null){
				soapBody = soapPart.getEnvelope().addBody();
			}
			
			// nomeFileFattura
			String nomeFileFattura = null;
			if(msg.getTransportRequestContext()!=null){
				nomeFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviNotifica.NOTIFICA_ESITO_INTEGRAZIONE_URLBASED_NOME_FILE);
				if(nomeFileFattura==null){
					nomeFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviNotifica.NOTIFICA_ESITO_INTEGRAZIONE_TRASPORTO_NOME_FILE_1);
				}
				if(nomeFileFattura==null){
					nomeFileFattura =  msg.getTransportRequestContext().getParameterFormBased(SDICostantiServizioRiceviNotifica.NOTIFICA_ESITO_INTEGRAZIONE_TRASPORTO_NOME_FILE_2);
				}
			}
			if(nomeFileFattura==null){
				throw new Exception("NomeFileFattura non fornito");
			}
			
			// childElement
			List<SOAPElement> childs = SoapUtils.getNotEmptyChildSOAPElement(soapBody);
			if(childs==null || childs.size()<=0){
				throw new Exception("Notifica di Esito Committente non presente");
			}
			if(childs.size()>1){
				throw new Exception("Sono presenti piu' elementi xml. Deve essere fornita una singola Notifica di Esito Committente");
			}
			SOAPElement notificaEsitoCommittenteSOAPElement = childs.get(0);
			
			// effettuo validazione del messaggio ricevuto
			byte[]notificaEsitoCommittenteBytes = null;
			try{
				// Fix Validazione versione
//				Attr attr = notificaEsitoCommittenteSOAPElement.getAttributeNode("versione");
//				if(attr!=null){
//					notificaEsitoCommittenteSOAPElement.removeAttributeNode(attr);
//					Node nAttr = soapPart.createAttributeNS(null, "versione");
//					nAttr.setNodeValue(attr.getNodeValue());
//					if(notificaEsitoCommittenteSOAPElement.getAttributes()!=null){
//						notificaEsitoCommittenteSOAPElement.getAttributes().setNamedItem(nAttr);
//					}
//				}				
//				AbstractValidatoreXSD validatore = it.gov.fatturapa.sdi.messaggi.v1_0.utils.XSDValidatorWithSignature.getOpenSPCoop2MessageXSDValidator(this.bustaBuilder.getProtocolFactory().getLogger());
//				validatore.valida(notificaEsitoCommittenteSOAPElement);
				
				// effettuo validazione con bytes, per ovviare al problema del namespace 'gov'
				notificaEsitoCommittenteBytes = this.xmlUtils.toByteArray(notificaEsitoCommittenteSOAPElement);
				if(isEnableValidazioneMessaggiCompatibilitaNamespaceSenzaGov){
					notificaEsitoCommittenteBytes = SDICompatibilitaNamespaceErrati.convertiXmlNamespaceSenzaGov(protocolFactory.getLogger(), notificaEsitoCommittenteBytes);
				}
				
				// validazione
				if(this.sdiProperties.isEnableValidazioneXsdMessaggi()){
					AbstractValidatoreXSD validatore = it.gov.fatturapa.sdi.messaggi.v1_0.utils.XSDValidatorWithSignature.getOpenSPCoop2MessageXSDValidator(this.bustaBuilder.getProtocolFactory().getLogger());
					validatore.valida(new ByteArrayInputStream(notificaEsitoCommittenteBytes));
				}
				
			}catch(Exception e){
				throw new Exception("Notifica di Esito Committente non valida: "+e.getMessage(),e);
			}
			
			// Leggo NotificaEsitoCommittente
			it.gov.fatturapa.sdi.messaggi.v1_0.NotificaEsitoCommittenteType notificaEsitoCommittente = null;
			try{
				// fatto prima in validazion: notificaEsitoCommittenteBytes = this.xmlUtils.toByteArray(notificaEsitoCommittenteSOAPElement);
				it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer deserializer = new 
						it.gov.fatturapa.sdi.messaggi.v1_0.utils.serializer.JaxbDeserializer();
				notificaEsitoCommittente = deserializer.readNotificaEsitoCommittenteType(notificaEsitoCommittenteBytes);
			}catch(Exception e){
				throw new Exception("Notifica di Esito Committente non valida: "+e.getMessage(),e);
			}
			
			
			
			// Aggiunto info alla busta
			
			// Esito.IdentificativoSdI
			if(notificaEsitoCommittente.getIdentificativoSdI()!=null){
				busta.addProperty(SDICostanti.SDI_BUSTA_EXT_IDENTIFICATIVO_SDI, notificaEsitoCommittente.getIdentificativoSdI().toString());
			}			
			// Esito.RiferimentoFattura
			if(notificaEsitoCommittente.getRiferimentoFattura()!=null){
				if(notificaEsitoCommittente.getRiferimentoFattura().getAnnoFattura()!=null){
					busta.addProperty(SDICostanti.SDI_BUSTA_EXT_RIFERIMENTO_FATTURA_ANNO, notificaEsitoCommittente.getRiferimentoFattura().getAnnoFattura().toString());
				}
				if(notificaEsitoCommittente.getRiferimentoFattura().getNumeroFattura()!=null){
					busta.addProperty(SDICostanti.SDI_BUSTA_EXT_RIFERIMENTO_FATTURA_NUMERO, notificaEsitoCommittente.getRiferimentoFattura().getNumeroFattura());
				}
				if(notificaEsitoCommittente.getRiferimentoFattura().getPosizioneFattura()!=null){
					busta.addProperty(SDICostanti.SDI_BUSTA_EXT_RIFERIMENTO_FATTURA_POSIZIONE, notificaEsitoCommittente.getRiferimentoFattura().getPosizioneFattura().toString());
				}
			}			
			// Esito.Esito
			if(notificaEsitoCommittente.getEsito()!=null){
				busta.addProperty(SDICostanti.SDI_BUSTA_EXT_ESITO, notificaEsitoCommittente.getEsito().name());
			}				
			// Esito.Descrizione
			if(notificaEsitoCommittente.getDescrizione()!=null){
				busta.addProperty(SDICostanti.SDI_BUSTA_EXT_DESCRIZIONE, notificaEsitoCommittente.getDescrizione());
			}	
			// Esito.MessageId
			if(notificaEsitoCommittente.getMessageIdCommittente()!=null){
				busta.addProperty(SDICostanti.SDI_BUSTA_EXT_MESSAGE_ID_COMMITTENTE, notificaEsitoCommittente.getMessageIdCommittente());
			}
					
			
			// Costruisco Messaggio Protocollo
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.ObjectFactory of = new it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.ObjectFactory();
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.FileSdIType fileSdi = new it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.FileSdIType();
			byte [] fileSent = notificaEsitoCommittenteBytes;
			if(isEnableGenerazioneMessaggiCompatibilitaNamespaceSenzaGov){
				fileSent = SDICompatibilitaNamespaceErrati.produciXmlNamespaceSenzaGov(protocolFactory.getLogger(), fileSent);
			}
			fileSdi.setFile(fileSent);
			fileSdi.setIdentificativoSdI(notificaEsitoCommittente.getIdentificativoSdI());
			fileSdi.setNomeFile(SDIUtils.getNomeFileMessaggi(protocolFactory, state, nomeFileFattura, TipiMessaggi.EC));
			
			// detach body
			soapBody.removeContents();
			
			// se sono presenti degli attachments li elimino
			if(msg.countAttachments()>0){
				msg.removeAllAttachments();
			}
			
			// add MessaggioProtocollo
			it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.utils.serializer.JaxbSerializer serializer = new it.gov.fatturapa.sdi.ws.ricezione.v1_0.types.utils.serializer.JaxbSerializer();
			String xmlRisposta = serializer.toString(of.createFileSdI(fileSdi));
			SOAPElement element = SoapUtils.getSoapFactory(msg.getMessageType()).createElement(this.xmlUtils.newElement(xmlRisposta.getBytes()));
			soapBody.addChildElement(element);
			
			// soapAction
			msg.setSoapAction(SDICostantiServizioRiceviNotifica.SDI_SOAP_ACTION_SERVIZIO_NOTIFICA_ESITO_AZIONE_NOTIFICA_ESITO);
				
			return SDIUtils.readHeader(msg); // l'header ritornato viene usato solo per il tracciamento e cosi' facendo viene ripulito
			
		}catch(Exception e){
			throw new ProtocolException(e.getMessage(),e);
		}
		
	}
}
