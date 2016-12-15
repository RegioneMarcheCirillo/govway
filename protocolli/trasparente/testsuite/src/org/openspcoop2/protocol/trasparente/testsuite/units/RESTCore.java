/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it).
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

package org.openspcoop2.protocol.trasparente.testsuite.units;

import java.util.Properties;

import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.protocol.sdk.constants.Inoltro;
import org.openspcoop2.protocol.trasparente.testsuite.core.CooperazioneTrasparenteBase;
import org.openspcoop2.protocol.trasparente.testsuite.core.CostantiTestSuite;
import org.openspcoop2.protocol.trasparente.testsuite.core.DatabaseProperties;
import org.openspcoop2.protocol.trasparente.testsuite.core.TrasparenteTestsuiteLogger;
import org.openspcoop2.protocol.trasparente.testsuite.core.Utilities;
import org.openspcoop2.protocol.trasparente.testsuite.units.utils.FileCache;
import org.openspcoop2.protocol.trasparente.testsuite.units.utils.TestFileEntry;
import org.openspcoop2.testsuite.core.Repository;
import org.openspcoop2.testsuite.core.TestSuiteException;
import org.openspcoop2.testsuite.core.TestSuiteProperties;
import org.openspcoop2.testsuite.db.DatabaseComponent;
import org.openspcoop2.testsuite.units.CooperazioneBase;
import org.openspcoop2.testsuite.units.CooperazioneBaseInformazioni;
import org.openspcoop2.utils.mime.MimeTypes;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpBodyParameters;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.xml.XMLDiff;
import org.openspcoop2.utils.xml.XMLDiffImplType;
import org.openspcoop2.utils.xml.XMLDiffOptions;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * RESTPost
 * 
 * @author Andreea Poli (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */
public class RESTCore {

	public static final String REST_CORE = "REST";
	
	private HttpRequestMethod method;
	private String servizioRichiesto;
	private String portaApplicativaDelegata;
	private boolean isDelegata;
	
	/** Gestore della Collaborazione di Base */
	private CooperazioneBase collaborazioneTrasparenteBase;

	public RESTCore(HttpRequestMethod method, boolean delegata) {
		this.method = method;
		this.isDelegata = delegata;
		IDSoggetto idSoggettoMittente = null;
		IDSoggetto idSoggettoDestinatario = null;
		if(this.isDelegata) {
			this.servizioRichiesto = Utilities.testSuiteProperties.getServizioRicezioneContenutiApplicativiFruitore();
			this.portaApplicativaDelegata = CostantiTestSuite.PORTA_DELEGATA_REST_API;
			idSoggettoMittente = CostantiTestSuite.PROXY_SOGGETTO_FRUITORE;
			idSoggettoDestinatario = CostantiTestSuite.PROXY_SOGGETTO_EROGATORE;
		} else {
			this.servizioRichiesto = Utilities.testSuiteProperties.getServizioRicezioneBusteErogatore();
			this.portaApplicativaDelegata = CostantiTestSuite.PORTA_APPLICATIVA_REST_API;
			idSoggettoMittente = CostantiTestSuite.PROXY_SOGGETTO_FRUITORE_ANONIMO;
			idSoggettoDestinatario = CostantiTestSuite.PROXY_SOGGETTO_EROGATORE;
		}
		
		CooperazioneBaseInformazioni info = CooperazioneTrasparenteBase.getCooperazioneBaseInformazioni(idSoggettoMittente,
				idSoggettoDestinatario,
				false,CostantiTestSuite.PROXY_PROFILO_TRASMISSIONE_CON_DUPLICATI,Inoltro.CON_DUPLICATI);	
		this.collaborazioneTrasparenteBase = 
			new CooperazioneBase(false,MessageType.SOAP_11,  info, 
					org.openspcoop2.protocol.trasparente.testsuite.core.TestSuiteProperties.getInstance(), 
					DatabaseProperties.getInstance(), TrasparenteTestsuiteLogger.getInstance(), this.isDelegata);

	}
	
	
	public void postInvoke(Repository repository) throws TestSuiteException, Exception{
		String id=repository.getNext();
		if(org.openspcoop2.protocol.trasparente.testsuite.core.Utilities.testSuiteProperties.attendiTerminazioneMessaggi_verificaDatabase()==false){
			try {
				Thread.sleep(org.openspcoop2.protocol.trasparente.testsuite.core.Utilities.testSuiteProperties.timeToSleep_verificaDatabase());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		DatabaseComponent data = null;
		if(this.isDelegata) {
			data = DatabaseProperties.getDatabaseComponentFruitore();
		} else {
			data = DatabaseProperties.getDatabaseComponentErogatore();
		}
		
		try{
			this.collaborazioneTrasparenteBase.testSincrono(data,id, CostantiTestSuite.REST_TIPO_SERVIZIO,
					CostantiTestSuite.SOAP_NOME_SERVIZIO_API, null, !this.isDelegata,null);
		}catch(Exception e){
			throw e;
		}finally{
			data.close();
		}
	}
	
	
	public void invoke(String tipoTest, int returnCodeAtteso, Repository repository, boolean isRichiesta, boolean isRisposta, String contentType) throws TestSuiteException, Exception{
		
		if(contentType != null && contentType.equals("NULL")) {
			contentType = null;
		}
		TestFileEntry fileEntry = FileCache.get(tipoTest);

		try{
		
			// 1. Tipo di richiesta da inviare: XML, JSON, DOC, PDF, ZIP e anche vedere di inviare un Multipart (il multipart va aggiunto.... chiedermi come fare)
			byte[] richiesta = fileEntry.getBytesRichiesta();
			// Definiire un content type corretto rispetto al tipo di file che si invia
			// Per conoscere il tipo di file corretto è possibile utilizzare l'utility sottostante
			String contentTypeRichiesta = contentType != null ? contentType : MimeTypes.getInstance().getMimeType(fileEntry.getExtRichiesta());
			
			// 2. Variabile sul tipo di HttpRequestMethod
			// Per sapere se il metodo prevede un input od un output come contenuto è possibile usare la seguente utility
			
			
			boolean rispostaOk = returnCodeAtteso == 200;

			HttpBodyParameters httpBody = new HttpBodyParameters(this.method, fileEntry.getExtRichiesta());
			boolean contenutoRichiesta = httpBody.isDoOutput();
			boolean contenutoRisposta =  httpBody.isDoInput() && (isRisposta || !rispostaOk);

			HttpRequest request = new HttpRequest();
			request.setMethod(this.method);
			if(contenutoRichiesta) {
				if(isRichiesta){
					request.setContent(richiesta);
					request.setContentType(contentTypeRichiesta);
				} else {
					request.setContent("".getBytes());
					request.setContentType(MimeTypes.getInstance().getMimeType("txt"));
				}
			}
			
			// Se richiesto dalla porta
			//request.setUsername(username);
			//request.setPassword(password);
			
			// Header HTTP da inviare
			String nomeHeaderHttpInviato = "ProvaHeaderRequest";
			String valoreHeaderRichiesto = "TEST_"+org.openspcoop2.utils.id.IDUtilities.getUniqueSerialNumber();
			request.addHeader(nomeHeaderHttpInviato, valoreHeaderRichiesto);
 			
			// Header HTTP da ricevere
			String nomeHeaderHttpDaRicevere = "ProvaHeaderResponse";
			String valoreHeaderHttpDaRicevere = "TEST_RESPONSE_"+org.openspcoop2.utils.id.IDUtilities.getUniqueSerialNumber();
			
			String action = null;
			if(contenutoRisposta) {
				action = "echo";
			} else {
				action = "ping";
			}
			
			// String url
			// 3. Costruzione url
			// NOTA: il file che si invia e quello che si riceve in alcuni test è differente
			StringBuffer bf = new StringBuffer();
			bf.append(this.servizioRichiesto);
			bf.append(this.portaApplicativaDelegata);
			bf.append("/service/").append(action);//.append("?").
			Properties propertiesURLBased = new Properties();
			propertiesURLBased.put("checkEqualsHttpMethod", this.method.name());
			propertiesURLBased.put("checkEqualsHttpHeader", nomeHeaderHttpInviato + ":" + valoreHeaderRichiesto);
			propertiesURLBased.put("returnHttpHeader", nomeHeaderHttpDaRicevere +":" + valoreHeaderHttpDaRicevere);
			
			if(returnCodeAtteso != 200) {
				propertiesURLBased.put("returnCode", returnCodeAtteso + "");
			}

			if(contenutoRisposta) {
				if(rispostaOk) {
					propertiesURLBased.put("destFileContentType", contentType != null ? contentType : MimeTypes.getInstance().getMimeType(fileEntry.getExtRichiesta()));
					propertiesURLBased.put("destFile", fileEntry.getFilenameRichiesta());
				} else {
					propertiesURLBased.put("destFileContentType", contentType != null ? contentType : MimeTypes.getInstance().getMimeType(fileEntry.getExtRispostaKo()));
					propertiesURLBased.put("destFile", fileEntry.getFilenameRispostaKo());
				}
			}
			
			String urlDaUtilizzare = TransportUtils.buildLocationWithURLBasedParameter(propertiesURLBased, bf.toString());

			request.setUrl(urlDaUtilizzare);
			
			// invocazione
			HttpResponse httpResponse = HttpUtilities.httpInvoke(request);
			
			// Raccolgo identificativo per verifica traccia
			String idMessaggio = httpResponse.getHeader(TestSuiteProperties.getInstance().getIdMessaggioTrasporto());
			Assert.assertTrue(idMessaggio!=null);
			Reporter.log("Ricevuto id ["+idMessaggio+"]");
			repository.add(idMessaggio);
			
			Reporter.log("["+idMessaggio+"] Atteso ["+returnCodeAtteso+"] ritornato ["+httpResponse.getResultHTTPOperation()+"]");
			Assert.assertTrue(returnCodeAtteso == httpResponse.getResultHTTPOperation());
			
			// Controllo header di risposta atteso
			String headerRispostaRitornatoValore = httpResponse.getHeader(nomeHeaderHttpDaRicevere);
			Reporter.log("["+idMessaggio+"] Atteso Header ["+nomeHeaderHttpDaRicevere+"] con valore atteso ["+valoreHeaderHttpDaRicevere+"] e valore ritornato ["+headerRispostaRitornatoValore+"]");
			Assert.assertTrue(headerRispostaRitornatoValore!=null);
			Assert.assertTrue(headerRispostaRitornatoValore.equals(valoreHeaderHttpDaRicevere));
			
			// Controllo risposta
			if(contenutoRisposta){
				
				byte[] contentAttesoRisposta = rispostaOk ? fileEntry.getBytesRichiesta(): fileEntry.getBytesRispostaKo();
				String contentTypeAttesoRisposta = contentType != null ? contentType : MimeTypes.getInstance().getMimeType((rispostaOk) ? fileEntry.getExtRichiesta(): fileEntry.getExtRispostaKo());

				
				String contentTypeRisposta = httpResponse.getContentType();
				Assert.assertEquals(contentTypeRisposta, contentTypeAttesoRisposta, "Content-Type del file di risposta ["+contentTypeRisposta+"] diverso da quello atteso ["+contentTypeAttesoRisposta+"]");

				byte[] contentRisposta = httpResponse.getContent();

				if(tipoTest.equals("xml")) {
					XMLDiff xmlDiffEngine = new XMLDiff();
					XMLDiffOptions xmlDiffOptions = new XMLDiffOptions();
					xmlDiffEngine.initialize(XMLDiffImplType.XML_UNIT, xmlDiffOptions);
					Assert.assertTrue(xmlDiffEngine.diff(xmlDiffEngine.getXMLUtils().newDocument(contentRisposta),xmlDiffEngine.getXMLUtils().newDocument(contentAttesoRisposta))
							, "File di risposta ["+new String(contentRisposta)+"]diverso da quello atteso ["+new String(contentAttesoRisposta)+"]: " + xmlDiffEngine.getDifferenceDetails());
				} else {
					Assert.assertEquals(contentRisposta,contentAttesoRisposta, "File di risposta ["+new String(contentRisposta)+"]diverso da quello atteso ["+new String(contentAttesoRisposta)+"]");
				}
				
			}
	
			
		}catch(Exception e){
			throw e;
		}
		
		
	}
	
}
