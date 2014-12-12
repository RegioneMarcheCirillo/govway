/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

package org.openspcoop2.pdd.core.integrazione;

import javax.xml.soap.SOAPHeaderElement;

import org.apache.log4j.Logger;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.message.ValidatoreXSD;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.AbstractCore;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.protocol.sdk.constants.TipoIntegrazione;
import org.openspcoop2.utils.xml.XSDResourceResolver;

/**
 * Classe utilizzata per la ricezione di informazioni di integrazione 
 * dai servizi applicativi verso la porta di dominio.
 *
 * @author Poli Andrea (apoli@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class GestoreIntegrazionePDSoap extends AbstractCore implements IGestoreIntegrazionePDSoap{

	/** Utility per l'integrazione */
	UtilitiesIntegrazione utilities = UtilitiesIntegrazione.getInstance();
	private ValidatoreXSD validatoreXSD = null;
	/** OpenSPCoopProperties */
	OpenSPCoop2Properties openspcoopProperties = OpenSPCoop2Properties.getInstance();
	
	/** Logger utilizzato per debug. */
	private Logger log = null;

	
	public GestoreIntegrazionePDSoap(){
		this.log = OpenSPCoop2Logger.getLoggerOpenSPCoopCore();
		if(this.log==null){
			this.log = Logger.getLogger(GestoreIntegrazionePDSoap.class);
		}
		try{
			XSDResourceResolver xsdResourceResolver = new XSDResourceResolver();
			xsdResourceResolver.addResource("soapEnvelope.xsd", UtilitiesIntegrazione.class.getResourceAsStream("/soapEnvelope.xsd"));
			this.validatoreXSD = new ValidatoreXSD(this.log,xsdResourceResolver,UtilitiesIntegrazione.class.getResourceAsStream("/integrazione.xsd"));
		}catch(Exception e){
			this.log.error("Integrazione.xsd, errore durante la costruzione del validatore xsd: "+e.getMessage(),e);
		}
	}
	
	// IN - Request
	
	@Override
	public void readInRequestHeader(HeaderIntegrazione integrazione,
			InRequestPDMessage inRequestPDMessage) throws HeaderIntegrazioneException{
		try{
			this.utilities.readHeader(inRequestPDMessage.getMessage(), integrazione,this.validatoreXSD,this.openspcoopProperties.getHeaderSoapActorIntegrazione());
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}	
	
	@Override
	public void deleteInRequestHeader(InRequestPDMessage inRequestPDMessage) throws HeaderIntegrazioneException{
		try{
			this.utilities.deleteHeader(inRequestPDMessage.getMessage(),this.openspcoopProperties.getHeaderSoapActorIntegrazione());
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}

	
	@Override
	public void updateInRequestHeader(InRequestPDMessage inRequestPDMessage,
			IDServizio idServizio,
			String idMessaggio,String servizioApplicativo,String correlazioneApplicativa) throws HeaderIntegrazioneException{
		try{
			this.utilities.updateHeader(inRequestPDMessage.getMessage(), 
					inRequestPDMessage.getSoggettoPropeprietarioPortaDelegata(), idServizio, idMessaggio, 
					servizioApplicativo, correlazioneApplicativa, null,
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(),  // actor
					this.openspcoopProperties.getHeaderSoapNameIntegrazione(),  // header name 
					this.openspcoopProperties.getHeaderSoapPrefixIntegrazione(),  // prefix
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(), // namespace
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeElementoIntegrazione(), // nomeElemento ExtInfoProtocol
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeAttributoIntegrazione(), // nomeAttributo ExtInfoProtocol
					this.getProtocolFactory().createProtocolManager().buildIntegrationProperties(inRequestPDMessage.getBustaRichiesta(), true, TipoIntegrazione.SOAP)
				);  
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}

	
	// OUT - Request
	
	@Override
	public void setOutRequestHeader(HeaderIntegrazione integrazione,
			OutRequestPDMessage outRequestPDMessage) throws HeaderIntegrazioneException{
	
		// nop;
		
	}
	
	
	// IN - Response
	
	@Override
	public void readInResponseHeader(HeaderIntegrazione integrazione,
			InResponsePDMessage inResponsePDMessage) throws HeaderIntegrazioneException{
		try{
			this.utilities.readHeader(inResponsePDMessage.getMessage(), integrazione,this.validatoreXSD,this.openspcoopProperties.getHeaderSoapActorIntegrazione());
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}
	
	@Override
	public void deleteInResponseHeader(InResponsePDMessage inResponsePDMessage) throws HeaderIntegrazioneException{
		try{
			this.utilities.deleteHeader(inResponsePDMessage.getMessage(),this.openspcoopProperties.getHeaderSoapActorIntegrazione());
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}
	
	@Override
	public void updateInResponseHeader(InResponsePDMessage inResponsePDMessage,
			String idMessageRequest,String idMessageResponse,String servizioApplicativo,String correlazioneApplicativa,String riferimentoCorrelazioneApplicativaRichiesta) throws HeaderIntegrazioneException{
		try{
			this.utilities.updateHeader(inResponsePDMessage.getMessage(), 
					inResponsePDMessage.getSoggettoMittente(),
					inResponsePDMessage.getServizio(), idMessageRequest, idMessageResponse, 
					servizioApplicativo, correlazioneApplicativa, riferimentoCorrelazioneApplicativaRichiesta,
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(),  // actor
					this.openspcoopProperties.getHeaderSoapNameIntegrazione(),  // header name 
					this.openspcoopProperties.getHeaderSoapPrefixIntegrazione(),  // prefix
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(),  // namespace
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeElementoIntegrazione(), // nomeElemento ExtInfoProtocol
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeAttributoIntegrazione(), // nomeAttributo ExtInfoProtocol
					this.getProtocolFactory().createProtocolManager().buildIntegrationProperties(inResponsePDMessage.getBustaRichiesta(), false, TipoIntegrazione.SOAP)
				);
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
	}
	
	// OUT - Response
	
	@Override
	public void setOutResponseHeader(HeaderIntegrazione integrazione,
			OutResponsePDMessage outResponsePDMessage) throws HeaderIntegrazioneException{
		
		try{
			
			SOAPHeaderElement header = this.utilities.buildHeader(integrazione, 
					this.openspcoopProperties.getHeaderSoapNameIntegrazione(), // header name 
					this.openspcoopProperties.getHeaderSoapPrefixIntegrazione(), // prefix
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(), // namespace
					this.openspcoopProperties.getHeaderSoapActorIntegrazione(), // actor
					outResponsePDMessage.getMessage(),
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeElementoIntegrazione(), // nomeElemento ExtInfoProtocol
					this.openspcoopProperties.getHeaderSoapExtProtocolInfoNomeAttributoIntegrazione(), // nomeAttributo ExtInfoProtocol
					this.getProtocolFactory().createProtocolManager().buildIntegrationProperties(outResponsePDMessage.getBustaRichiesta(), false, TipoIntegrazione.SOAP)
				);
			//System.out.println((new org.openspcoop.dao.message.OpenSPCoopMessageFactory().createMessage().getAsString(header)));
			if(outResponsePDMessage.getMessage().getSOAPHeader() == null){
				outResponsePDMessage.getMessage().getSOAPPart().getEnvelope().addHeader();
			}
			//outResponsePDMessage.getMessage().getSOAPHeader().addChildElement(header);
			outResponsePDMessage.getMessage().addHeaderElement(outResponsePDMessage.getMessage().getSOAPHeader(), header);
			
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDSoap, "+e.getMessage(),e);
		}
		
	}
}
