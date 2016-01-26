/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
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
package org.openspcoop2.utils.logger.test;

import java.util.Date;

import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.logger.Attachment;
import org.openspcoop2.utils.logger.Event;
import org.openspcoop2.utils.logger.ILogger;
import org.openspcoop2.utils.logger.LoggerFactory;
import org.openspcoop2.utils.logger.LowSeverity;
import org.openspcoop2.utils.logger.Message;
import org.openspcoop2.utils.logger.MessageType;
import org.openspcoop2.utils.logger.Property;
import org.openspcoop2.utils.logger.Severity;
import org.openspcoop2.utils.logger.basic.Log4JLogger;
import org.openspcoop2.utils.logger.proxy.Actor;
import org.openspcoop2.utils.logger.proxy.Client;
import org.openspcoop2.utils.logger.proxy.FlowMode;
import org.openspcoop2.utils.logger.proxy.Identifier;
import org.openspcoop2.utils.logger.proxy.Operation;
import org.openspcoop2.utils.logger.proxy.ProxyContext;
import org.openspcoop2.utils.logger.proxy.Result;
import org.openspcoop2.utils.logger.proxy.Role;
import org.openspcoop2.utils.logger.proxy.Server;
import org.openspcoop2.utils.logger.proxy.ServerEndpointType;
import org.openspcoop2.utils.logger.proxy.Service;

/**
 * Test
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Test {

	public static void main(String[] args) throws Exception {
		
		LoggerFactory.initialize(Log4JLogger.class.getName(),
				"/org/openspcoop2/utils/logger/test/example.msgDiagnostici.properties",
				false,
				"/org/openspcoop2/utils/logger/test/example.log4j.properties");
		
		//Log4JLogger.setDiagnosticSeverity(Severity.INFO);
		//Log4JLogger.setEventSeverity(Severity.ERROR);
		
		
		ILogger logger = LoggerFactory.newLogger();		

		
		logger.log("Prova altro log con messaggio inserito qua",LowSeverity.DEBUG_HIGH);
		logger.log("Prova altro log con messaggio inserito qua con function",LowSeverity.DEBUG_MEDIUM,"core");
		
		logger.log("000002");
		logger.log("core.inizializzazione"); 
		
		logger.log("001001","21 Ottobre 2015","GovPay");
		
		
		
		
		ProxyContext context = (ProxyContext) logger.getContext();
		
		context.getTransaction().setClusterId("Cluster1");
		
		logger.log("000003",context.getTransaction());
		
		
		
		
		try{
			
			long httpRequestSize = 2345;
			context.getRequest().setInDate(new Date());
			context.getRequest().setInSize(httpRequestSize);
		
			// .... TODO DUMP RICHIESTA
			boolean dump = true;
			if(dump){
				
				byte[] content = "<prova>CIAO</PROVA>".getBytes();
				
				Message message = new Message();
				message.setType(MessageType.REQUEST_IN);
				message.setContent(content);
				message.setContentType("text/xml");
				
				Property header1 = new Property("Transfer-Encoding","Chunked");
				message.addHeader(header1);
				Property header2 = new Property("WebClient","Firefox");
				message.addHeader(header2);
				
				Property xpath1 = new Property("RisorsaNome","Rossi");
				message.addResource(xpath1);
				
				Attachment att = new Attachment();
				att.setContent("HELLO WORLD".getBytes());
				att.setContentType("text/plain");
				att.setContentId("cid:XXX");
				message.addAttachment(att);
				
				logger.log(message);
				
			}
			
			
		
			// ... TODO Processo di autenticazione ...
	
			Client client = new Client();
			client.setPrincipal("C=IT,O=ComuneRoma");
			client.setName("ServizioApplicativoAutenticato");
			context.getTransaction().setClient(client);
			
			// ... TODO PRoceso di identificaizone funzione smistatore (es. PortaDlegata)
			
			context.getTransaction().getClient().setInvocationEndpoint("/openspcoop/PD/NomePortaDelegta?prova=si");
			context.getTransaction().getClient().setInterfaceName("NomePortaDelegata");
			
			// .... TODO DOMINIO PER CUI AGISCO
			context.getTransaction().setDomain("GovPayFunction");
			context.getTransaction().setRole(Role.CLIENT);
			
			
			// ... TODO Identificazione attori, servizio, azione
		
			Actor from = new Actor();
			from.setType("SPC");
			from.setName("MinisteroFruitore");
			from.setAddress("http://pddMinisteroFruitore");
			context.getTransaction().setFrom(from);
			
			Actor to = new Actor();
			to.setType("SPC");
			to.setName("MinisteroErogatore");
			to.setAddress("http://pddMinisteroErogatore");
			context.getTransaction().setTo(to);
			
			Service service = new Service();
			service.setType("SPC");
			service.setName("VariazioneAnagrafica");
			service.setVersion(1);
			context.getTransaction().setService(service);
			
			Operation operation = new Operation();
			operation.setName("aggiornamento");
			operation.setMode(FlowMode.INPUT_OUTPUT);
			context.getTransaction().setOperation(operation);
		
	
			
			logger.log("001002");
			
			
			
			// .... TODO LOGICA INTERNA
			
			Identifier idRequest = new Identifier();
			idRequest.setId("Prova_238231232_3232");
			context.getRequest().setIdentifier(idRequest);
			context.getRequest().setCorrelationIdentifier("ID_CORRELAZIONE_APPLICATIVA");
			
			
			logger.log("002001");
			
			
			// .... TODO DUMP RICHIESTA USCITA
			if(dump){
				
				byte[] content = "<prova>CIAO</PROVA>".getBytes();
				
				Message message = new Message();
				message.setType(MessageType.REQUEST_OUT);
				message.setContent(content);
				message.setContentType("text/xml");
				
				Property header1 = new Property("Transfer-Encoding","Chunked");
				message.addHeader(header1);
				Property header2 = new Property("WebClient","Firefox");
				message.addHeader(header2);
				
				Property xpath1 = new Property("RisorsaNome","Rossi");
				message.addResource(xpath1);
				
				Attachment att = new Attachment();
				att.setContent("HELLO WORLD".getBytes());
				att.setContentType("text/plain");
				att.setContentId("cid:XXX");
				message.addAttachment(att);
				
				logger.log(message);
				
			}
			
			
			
			// .... TODO INFORMAZIONI SERVER A CUI DEVO INOLTRARE IL PACCHETTO
			Server server = new Server();
			server.setEndpoint("http://127.0.0.1:8080/server");
			server.setEndpointType(ServerEndpointType.HTTP.name());
			server.setName("ApplicativoServer");
			context.getTransaction().setServer(server);
			
			logger.log("003001");
			
			long httpRequestModifySize = 231232;
			context.getRequest().setOutDate(new Date());
			context.getRequest().setOutSize(httpRequestModifySize);
			
			// .... TODO SEND E VERIFICA RISPOSTA RITORNATA
			
			int returnCode = 500;
			server.setTransportCode(returnCode+"");
			if(returnCode == 200){
				logger.log("003002");
			}
			else{
				logger.log("003003");
			}
			
			
			context.getResponse().setInDate(new Date());
			context.getResponse().setInSize(httpRequestSize);
	
			// .... TODO DUMP IN RISPOSTA
			if(dump){
				
				byte[] content = "<prova>CIAO</PROVA>".getBytes();
				
				Message message = new Message();
				message.setType(MessageType.RESPONSE_IN);
				message.setContent(content);
				message.setContentType("text/xml");
				
				Property header1 = new Property("Transfer-Encoding","Chunked");
				message.addHeader(header1);
				Property header2 = new Property("WebClient","Firefox");
				message.addHeader(header2);
				
				Property xpath1 = new Property("RisorsaNome","Rossi");
				message.addResource(xpath1);
				
				Attachment att = new Attachment();
				att.setContent("HELLO WORLD".getBytes());
				att.setContentType("text/plain");
				att.setContentId("cid:XXX");
				message.addAttachment(att);
				
				logger.log(message);
			}
			
			
			
			// .... TODO LOGICA INTERNA PER ANALISI RISPOSTA
			
			Identifier idResponse = new Identifier();
			idResponse.setId("ProvaResponse_238231232_3232");
			context.getResponse().setIdentifier(idResponse);
			context.getResponse().setCorrelationIdentifier("ID_CORRELAZIONE_APPLICATIVA");
			
			int returnCodeClient = 200;
			boolean fault = true; // SIMULAZIONE TODO...
			if(fault){
				returnCodeClient = 500;
			}
			
			
			
			
			// .... TODO DUMP OUT RISPOSTA
			if(dump){
				
				byte[] content = "<prova>CIAO</PROVA>".getBytes();
				
				Message message = new Message();
				message.setType(MessageType.RESPONSE_OUT);
				message.setContent(content);
				message.setContentType("text/xml");
				
				Property header1 = new Property("Transfer-Encoding","Chunked");
				message.addHeader(header1);
				Property header2 = new Property("WebClient","Firefox");
				message.addHeader(header2);
				
				Property xpath1 = new Property("RisorsaNome","Rossi");
				message.addResource(xpath1);
				
				Attachment att = new Attachment();
				att.setContent("HELLO WORLD".getBytes());
				att.setContentType("text/plain");
				att.setContentId("cid:XXX");
				message.addAttachment(att);
				
				logger.log(message);
			}
			
			
			// TODO IF FAULT

			if(fault){
				byte[] faultRicevuto = null;
				context.getResponse().setInFault(faultRicevuto);
				
				byte[] faultVerraInoltratoClient = null;
				context.getResponse().setOutFault(faultVerraInoltratoClient);
				
			}
			
			context.getTransaction().getClient().setTransportResponseCode(returnCodeClient+"");
			if(fault){
				context.getTransaction().setResult(Result.SERVER_ERROR);
			}
			else{
				context.getTransaction().setResult(Result.SUCCESS);
			}
			
			// Ritorno risposta al client su http post
			if(fault)
				logger.log("001004");
			else
				logger.log("001003");
			
			
			context.getResponse().setOutDate(new Date());
			context.getResponse().setOutSize(httpRequestSize);
			
			logger.log();
			
			
			Event event = new Event();
			event.setDate(DateManager.getDate());
			event.setClusterId("ClusteId");
			event.setCode("XXX");
			event.setSource("ModuloControllo");
			event.setSeverity(Severity.ERROR);
			logger.log(event);
			
		}catch(Exception e){
			logger.log("000001",e.getMessage()!=null?e.getMessage():"NullPointer");
			e.printStackTrace(System.out);
		}
	}

}
