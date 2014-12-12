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



package org.openspcoop2.testsuite.clients;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.utils.ByteArrayOutputStream;
import org.openspcoop2.testsuite.core.FatalTestSuiteException;
import org.openspcoop2.testsuite.core.Utilities;
import org.openspcoop2.testsuite.core.asincrono.RepositoryCorrelazioneIstanzeAsincrone;

/**
 * Client per la gestione del profilo di collaborazione Asincrono Asimmetrico, in modalita asincrona.
 * La richiesta viene invocata con una interazione asincrona.
 * La risposta viene richiesta attraverso una interazione sincrona.
 * 
 * @author Andi Rexha (rexha@openspcoop.org)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class ClientAsincronoAsimmetrico_ModalitaAsincrona extends ClientCore{
	
	/** Nome della Porta Correlata */
	private String portaDelegataCorrelata;
	
	private boolean generaIDUnivoco = true;
	public boolean isGeneraIDUnivoco() {
		return this.generaIDUnivoco;
	}

	public void setGeneraIDUnivoco(boolean generaIDUnivoco) {
		this.generaIDUnivoco = generaIDUnivoco;
	}
	
	/** Repository per la correlazione delle due istanze asincrone */
	private RepositoryCorrelazioneIstanzeAsincrone repositoryCorrelazioneIstanzeAsincrone;
	
	public ClientAsincronoAsimmetrico_ModalitaAsincrona(RepositoryCorrelazioneIstanzeAsincrone rep) throws FatalTestSuiteException {
		super();
		this.repositoryCorrelazioneIstanzeAsincrone=rep;
	}
	
	/**
	 * Imposta il nome della porta delegata correlata
	 * 
	 * @param nomePorta
	 */
	public void setPortaDelegataCorrelata(String nomePorta){
		this.portaDelegataCorrelata=nomePorta;
	}

	public void run() throws FatalTestSuiteException, AxisFault{	
		
		// Backup della richiesta per un successivo invio
		String contentType = this.soapEngine.getRequestMessage().getContentType(new org.apache.axis.soap.SOAP11Constants());
		byte[] msg = null;
		MimeHeaders mh = this.soapEngine.getRequestMessage().getMimeHeaders();
		try{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			this.soapEngine.getRequestMessage().writeTo(bout);
			msg = bout.toByteArray();
			
			// reimposto richiesta
			ByteArrayInputStream messageInput = new ByteArrayInputStream(msg);
			Message tmp = new Message(messageInput,false,contentType,null);
			Iterator<?> it = mh.getAllHeaders();
			while(it.hasNext()){
				MimeHeader mhi = (MimeHeader) it.next();
				tmp.getMimeHeaders().addHeader(mhi.getName(), mhi.getValue());
			}
			if(tmp.countAttachments()==0){
				tmp.getSOAPPartAsBytes();
			}
			this.setMessage(tmp,false);
			
		}catch(Exception e){
			throw new FatalTestSuiteException(e.getMessage());
		}
		
		// imposta la url e l'autenticazione per il soapEngine
	    this.soapEngine.setConnectionParameters();
	    // Effettua una invocazione asincrona
	    invocazioneAsincrona();
	    // Messaggio spedito
	    this.sentMessage=this.soapEngine.getRequestMessage();
	    // Check id
	    if(this.idMessaggio==null)
	    	throw new FatalTestSuiteException("ID messaggio non presenta nell'header del trasporto della ricevuta.");
	    // Preleva l'id dal body della risposta
	    try{
	    	// Il body puo' essere null in caso si voglia che il oneway non ritorna contenuto applicativo.
		    if(this.receivedMessage.getSOAPBody()!=null && this.receivedMessage.getSOAPBody().hasChildNodes()){
			    String idBody=Utilities.getIDFromOpenSPCoopOKMessage(this.log,this.receivedMessage);
			    // Controlla che sia uguale a quello ritornato nell'header della risposta
			    if(idBody==null)
			    	throw new FatalTestSuiteException("ID messaggio non presenta nella ricevuta OpenSPCoopOK.");
			    if(this.idMessaggio.equals(idBody)==false)
			    	throw new FatalTestSuiteException("ID messaggio presente nell'header del trasporto della risposta differisce dall'id presente nel messaggio OpenSPCoopOK della ricevuta.");
				
		    }
	    }catch(Exception e){
	    	throw new FatalTestSuiteException("Check msg openspcoop ok non riuscita: "+e.getMessage());
	    }
	    // Identificativo della richiesta
	    String identificativoRichiestaAsincrona=this.idMessaggio;
        
	    this.log.info("[AsincronoAsimmetrico_modalitaAsincrona] Gestione richiesta con id="+identificativoRichiestaAsincrona+" effettata.");

		// Attesa prima della generazione della risposta asincrona
	    if(this.attesaTerminazioneMessaggi == false){
	    	try{
	    		Thread.sleep(this.testsuiteProperties.timeToSleep_generazioneRispostaAsincrona());
	    	}
	    	catch(InterruptedException i){}
	    }else{
	    	if(this.dbAttesaTerminazioneMessaggiFruitore!=null){
				long countTimeout = System.currentTimeMillis() + (1000*this.testsuiteProperties.getTimeoutProcessamentoMessaggiOpenSPCoop());
				while(this.dbAttesaTerminazioneMessaggiFruitore.getVerificatoreMessaggi().countMsgOpenSPCoop(identificativoRichiestaAsincrona)!=0 && countTimeout>System.currentTimeMillis()){
					try{
					Thread.sleep(this.testsuiteProperties.getCheckIntervalProcessamentoMessaggiOpenSPCoop());
					}catch(Exception e){}
				}
			}
			if(this.dbAttesaTerminazioneMessaggiErogatore!=null){
				long countTimeout = System.currentTimeMillis() + (1000*this.testsuiteProperties.getTimeoutProcessamentoMessaggiOpenSPCoop());
				while(this.dbAttesaTerminazioneMessaggiErogatore.getVerificatoreMessaggi().countMsgOpenSPCoop(identificativoRichiestaAsincrona)!=0 && countTimeout>System.currentTimeMillis()){
					try{
						Thread.sleep(this.testsuiteProperties.getCheckIntervalProcessamentoMessaggiOpenSPCoop());
					}catch(Exception e){}
				}
			}
			try{
	    		Thread.sleep(4000);
	    	}
	    	catch(InterruptedException i){}
	    }
        
        // Impostazione porta delegata correlata da invocare
        this.portaDelegata=this.portaDelegataCorrelata;
        // Effettua la connessione al SoapEngine
        connectToSoapEngine();
        // Messaggio di richiesta stato da spedire
        ByteArrayInputStream messageInput = new ByteArrayInputStream(msg);
		Message tmp = new Message(messageInput,false,contentType,null);
		Iterator<?> it = mh.getAllHeaders();
		while(it.hasNext()){
			MimeHeader mhi = (MimeHeader) it.next();
			tmp.getMimeHeaders().addHeader(mhi.getName(), mhi.getValue());
		}
		if(tmp.countAttachments()==0){
			tmp.getSOAPPartAsBytes();
		}
		this.setMessage(tmp,this.generaIDUnivoco);
        // refresh dei parametri di autenticazione
		this.soapEngine.setUserName(this.username);
		this.soapEngine.setPassword(this.password);
        // Imposta parametri di connessione
        this.soapEngine.setConnectionParameters();
        this.soapEngine.addMimeHeader(this.testsuiteProperties.getRiferimentoAsincronoTrasporto(), identificativoRichiestaAsincrona);
        // Effettua una invocazione sincrona
        invocazioneSincrona();
        // Identificativo della richiesta-stato
	    String identificativoRichiestaStatoAsincrona=this.idMessaggio;
        // Messaggio spedito
	    this.sentMessage=this.soapEngine.getRequestMessage();
	    // Check riferimentoAsincrono uguale a identificativo richiesta
	    if(identificativoRichiestaAsincrona.equals(this.riferimentoAsincrono)==false){
	    	throw new FatalTestSuiteException("RiferimentoAsincrono ritornato con la richiesta stato ["+this.riferimentoAsincrono+"] non e' uguale all'identificativo di correlazione atteso ["+identificativoRichiestaAsincrona+"]");
	    }
	    
	    // Attesa terminazione messaggi
	    if(this.attesaTerminazioneMessaggi){
			if(this.dbAttesaTerminazioneMessaggiFruitore!=null){
				long countTimeout = System.currentTimeMillis() + (1000*this.testsuiteProperties.getTimeoutProcessamentoMessaggiOpenSPCoop());
				while(this.dbAttesaTerminazioneMessaggiFruitore.getVerificatoreMessaggi().countMsgOpenSPCoop_profiloAsincrono(identificativoRichiestaAsincrona,identificativoRichiestaStatoAsincrona)!=0 && countTimeout>System.currentTimeMillis()){
					try{
					Thread.sleep(this.testsuiteProperties.getCheckIntervalProcessamentoMessaggiOpenSPCoop());
					}catch(Exception e){}
				}
			}
			if(this.dbAttesaTerminazioneMessaggiErogatore!=null){
				long countTimeout = System.currentTimeMillis() + (1000*this.testsuiteProperties.getTimeoutProcessamentoMessaggiOpenSPCoop());
				while(this.dbAttesaTerminazioneMessaggiErogatore.getVerificatoreMessaggi().countMsgOpenSPCoop_profiloAsincrono(identificativoRichiestaAsincrona,identificativoRichiestaStatoAsincrona)!=0 && countTimeout>System.currentTimeMillis()){
					try{
						Thread.sleep(this.testsuiteProperties.getCheckIntervalProcessamentoMessaggiOpenSPCoop());
					}catch(Exception e){}
				}
			}
		}
	    
	    // Metto i due id nel repository
	    this.repositoryCorrelazioneIstanzeAsincrone.add(identificativoRichiestaAsincrona,identificativoRichiestaStatoAsincrona);
	    
	    this.log.info("[AsincronoAsimmetrico_modalitaAsincrona] Gestione richiesta-stato con id="+identificativoRichiestaStatoAsincrona+" , correlata a "+identificativoRichiestaAsincrona+" effettata.");

	
	}


}
