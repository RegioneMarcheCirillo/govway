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

package org.openspcoop2.message;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;

import org.openspcoop2.utils.io.notifier.NotifierInputStream;
import org.openspcoop2.utils.io.notifier.NotifierInputStreamParams;
import org.openspcoop2.utils.resources.Loader;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;


/**
 * Factory per la costruzione di messaggi OpenSPCoop2Message. 
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public abstract class OpenSPCoop2MessageFactory {
	public abstract String getEncryptedDataHeaderBlockClass();
	public abstract String getDocumentBuilderFactoryClass();
	public abstract String getProcessPartialEncryptedMessageClass();
	public abstract String getSignPartialMessageProcessorClass();
	public static String messageFactoryImpl = org.openspcoop2.message.OpenSPCoop2MessageFactory_impl.class.getName();
	
	public static void setMessageFactoryImpl(String messageFactoryImpl) {
		if(messageFactoryImpl != null)
			OpenSPCoop2MessageFactory.messageFactoryImpl = messageFactoryImpl;
	}

	protected static OpenSPCoop2MessageFactory openspcoopMessageFactory = null;
	public static OpenSPCoop2MessageFactory getMessageFactory() {
		if(OpenSPCoop2MessageFactory.openspcoopMessageFactory == null)
			try { OpenSPCoop2MessageFactory.initMessageFactory(); } catch (Exception e) { throw new RuntimeException(e); }
		return OpenSPCoop2MessageFactory.openspcoopMessageFactory;
	}
	
	public static void initMessageFactory() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(OpenSPCoop2MessageFactory.openspcoopMessageFactory==null){
			OpenSPCoop2MessageFactory.openspcoopMessageFactory = (OpenSPCoop2MessageFactory) Loader.getInstance().newInstance(OpenSPCoop2MessageFactory.messageFactoryImpl);
		}
	}
	
	protected static SOAPFactory soapFactory11 = null;
	protected static SOAPFactory soapFactory12 = null;
	public SOAPFactory getSoapFactory11(){
		if(OpenSPCoop2MessageFactory.soapFactory11==null){
			initSoapFactory();
		}
		return OpenSPCoop2MessageFactory.soapFactory11;
	}
	
	public SOAPFactory getSoapFactory12(){
		if(OpenSPCoop2MessageFactory.soapFactory12==null){
			initSoapFactory();
		}
		return OpenSPCoop2MessageFactory.soapFactory12;
	}
	protected abstract void initSoapFactory();
	
	
	protected static MessageFactory soapMessageFactory = null;
	public MessageFactory getSoapMessageFactory() throws SOAPException {
		if(OpenSPCoop2MessageFactory.soapMessageFactory==null){
			initSoapMessageFactory();
		}
		return OpenSPCoop2MessageFactory.soapMessageFactory;
	}
	protected abstract void initSoapMessageFactory() throws SOAPException; 
	
	protected final String getContentType(MimeHeaders headers)
    {
        String values[] = headers.getHeader(Costanti.CONTENT_TYPE);
        if(values == null)
            return null;
        else
            return values[0];
    }
	
	public abstract SOAPConnectionFactory getSOAPConnectionFactory() throws SOAPException;
	
	public abstract OpenSPCoop2Message createMessage(SOAPVersion versioneSoap) throws SOAPException,IOException;
	public abstract OpenSPCoop2Message createMessage(SOAPMessage msg) throws SOAPException,IOException;
	protected abstract OpenSPCoop2Message _createMessage(MimeHeaders mhs, InputStream is,  boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold, long overhead) throws SOAPException, IOException;	
	
	@SuppressWarnings("resource")
	private OpenSPCoop2Message _createMessage(MimeHeaders mhs, InputStream is, NotifierInputStreamParams notifierInputStreamParams,
			boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold, long overhead) throws SOAPException, IOException, ParseException{	
		
		InputStream nis = null;
		
		if(notifierInputStreamParams!=null){
			String [] headerContentType = null;
			if(mhs!=null){
				headerContentType = mhs.getHeader(Costanti.CONTENT_TYPE);
			}
			String contentType = null;
			if(headerContentType!=null && headerContentType.length>0){
				contentType = headerContentType[0];
			}
			else{
				contentType = Costanti.CONTENT_TYPE_SOAP_1_1;
			}
	
			nis = new NotifierInputStream(is,contentType,notifierInputStreamParams);
		}
		else{
			nis = is;
		}
		
		OpenSPCoop2Message msg = this._createMessage(mhs, nis, fileCacheEnable, attachmentRepoDir, fileThreshold, overhead);
		
		if(notifierInputStreamParams!=null){
			msg.setNotifierInputStream((NotifierInputStream)nis);
		}
		
		return msg;
		
	}
	
	public OpenSPCoop2Message createMessage(MimeHeaders mhs, InputStream is, NotifierInputStreamParams notifierInputStreamParams, 
			boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold, long overhead) throws SOAPException, IOException, ParseException{	
		return _createMessage(mhs, is, notifierInputStreamParams,  fileCacheEnable, attachmentRepoDir, fileThreshold, overhead);
	}
	
	public OpenSPCoop2Message createMessage(HttpServletRequest req, NotifierInputStreamParams notifierInputStreamParams,  
			boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold) throws IOException, SOAPException, ParseException{
		return createMessage(req, notifierInputStreamParams, fileCacheEnable, attachmentRepoDir, fileThreshold, 0);
	}
	
	public OpenSPCoop2Message createMessage(HttpServletRequest req, NotifierInputStreamParams notifierInputStreamParams, 
			boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold, long overhead) throws IOException, SOAPException, ParseException{
		
		MimeHeaders mhs = new MimeHeaders();
		mhs.addHeader(Costanti.CONTENT_TYPE, req.getContentType());
		mhs.addHeader(Costanti.SOAP_ACTION, req.getHeader(Costanti.SOAP_ACTION));
					
		return _createMessage(mhs, req.getInputStream(), notifierInputStreamParams,  fileCacheEnable, attachmentRepoDir, fileThreshold, overhead);
		
	}


	public OpenSPCoop2Message createMessage(InputStream messageInput,NotifierInputStreamParams notifierInputStreamParams, 
			boolean isBodyStream, String contentType, String contentLocation,  boolean fileCacheEnable, String attachmentRepoDir, String fileThreshold) throws IOException, SOAPException, ParseException{
		long diff = 0;
		if(isBodyStream){
			Vector<InputStream> streams = new Vector<InputStream> ();
			byte[] start = null, end = null;
			if(contentType.equals(Costanti.CONTENT_TYPE_SOAP_1_2)){
				start = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\"><SOAP-ENV:Body>".getBytes();
				end = "</SOAP-ENV:Body></SOAP-ENV:Envelope>".getBytes();
			} else {
				start = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body>".getBytes();
				end = "</SOAP-ENV:Body></SOAP-ENV:Envelope>".getBytes();				
			}
			diff += start.length + end.length;
			streams.add(new ByteArrayInputStream(start));
			streams.add(messageInput);
			streams.add(new ByteArrayInputStream (end));
			messageInput = new SequenceInputStream (streams.elements());
		}
		MimeHeaders mhs = new MimeHeaders();
		mhs.addHeader(Costanti.CONTENT_TYPE, contentType);
		if(contentLocation != null) 
			mhs.addHeader(Costanti.CONTENT_LOCATION, contentLocation);
		
		OpenSPCoop2Message msg = _createMessage(mhs, messageInput, notifierInputStreamParams, fileCacheEnable, attachmentRepoDir, fileThreshold, diff);
		
		// Verifico la costruzione del messaggio SOAP
		try{
			msg.getSOAPPart().getEnvelope();
		} catch (SOAPException e) {
			if(e.getCause() instanceof TransformerException) {
				TransformerException cause = (TransformerException) e.getCause();
				if(cause.getException() instanceof SAXParseException)
					throw new SOAPException(cause.getException().getMessage(),e);
			}
		}
		
		return msg;
	}
	
	
	public OpenSPCoop2Message createMessage(SOAPVersion versioneSoap, byte[] xml) throws IOException, SOAPException, ParseException{
		return this.createMessage(versioneSoap,xml,null);
	}
	public OpenSPCoop2Message createMessage(SOAPVersion versioneSoap, byte[] xml,NotifierInputStreamParams notifierInputStreamParams) throws IOException, SOAPException, ParseException{
		ByteArrayInputStream bais = new ByteArrayInputStream(xml);
		MimeHeaders mhs = new MimeHeaders();
		if(versioneSoap.equals(SOAPVersion.SOAP12)){
			mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_2);
		} else {
			mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_1);
		}
		return _createMessage(mhs, bais, notifierInputStreamParams, false, null, null, 0);
	}
	
	public OpenSPCoop2Message createMessage(SOAPVersion versioneSoap, String xml) throws IOException, SOAPException, ParseException{
		return this.createMessage(versioneSoap,xml,null);
	}
	public OpenSPCoop2Message createMessage(SOAPVersion versioneSoap, String xml,NotifierInputStreamParams notifierInputStreamParams) throws IOException, SOAPException, ParseException{
		return createMessage(versioneSoap,xml.getBytes(),notifierInputStreamParams);
	}
	
	
	

	public OpenSPCoop2Message createEmptySOAPMessage(SOAPVersion versioneSoap) {
		return this.createEmptySOAPMessage(versioneSoap,null);
	}
	public OpenSPCoop2Message createEmptySOAPMessage(SOAPVersion versioneSoap,NotifierInputStreamParams notifierInputStreamParams) {
		try{
			byte[] xml = null;
			MimeHeaders mhs = new MimeHeaders();
			if(versioneSoap.equals(SOAPVersion.SOAP12)){
				xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\"><SOAP-ENV:Body/></SOAP-ENV:Envelope>".getBytes();
				mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_2);
			} else {
				xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body/></SOAP-ENV:Envelope>".getBytes();
				mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_1);
			}
			
			ByteArrayInputStream bais = new ByteArrayInputStream(xml);
			return _createMessage(mhs, bais, notifierInputStreamParams, false, null, null, xml.length);
		}
		catch(SOAPException soape){
			System.err.println("SOAPException non gestibile" + soape);
		}
		catch(IOException ioe){
			System.err.println("IOException non gestibile" + ioe);
		}
		catch(ParseException ioe){
			System.err.println("ParseException non gestibile" + ioe);
		}
		return null;
		
	}
	
	/*
	 * Messaggi di Errore
	 */
	public OpenSPCoop2Message createFaultMessage(SOAPVersion versioneSoap, Throwable t) {
		return this.createFaultMessage(versioneSoap, t,null);
	}
	public OpenSPCoop2Message createFaultMessage(SOAPVersion versioneSoap, Throwable t,NotifierInputStreamParams notifierInputStreamParams) {
		return createFaultMessage(versioneSoap, t.getMessage(),notifierInputStreamParams);
	}
	
	public OpenSPCoop2Message createFaultMessage(SOAPVersion versioneSoap, String errore) {
		return this.createFaultMessage(versioneSoap, errore,null);
	}
	public OpenSPCoop2Message createFaultMessage(SOAPVersion versioneSoap, String errore,NotifierInputStreamParams notifierInputStreamParams){
		try{
			String xml = null;
			MimeHeaders mhs = new MimeHeaders();
			if(versioneSoap.equals(SOAPVersion.SOAP12)){
				xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://www.w3.org/2003/05/soap-envelope\">"
						+"<SOAP-ENV:Header/><SOAP-ENV:Body>"
						+"<SOAP-ENV:Fault>"
						+"<SOAP-ENV:Code><SOAP-ENV:Value>SOAP-ENV:Server</SOAP-ENV:Value></SOAP-ENV:Code>"
						+"<SOAP-ENV:Reason><SOAP-ENV:Text xml:lang=\"en-US\">" + errore + "</SOAP-ENV:Text></SOAP-ENV:Reason>"
						+"<SOAP-ENV:Role>"+org.openspcoop2.utils.Costanti.OPENSPCOOP2+"</SOAP-ENV:Role>"
						+"</SOAP-ENV:Fault>"
						+"</SOAP-ENV:Body></SOAP-ENV:Envelope>";
				mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_2);
			} else {
				xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+"<SOAP-ENV:Header/><SOAP-ENV:Body>"
						+"<SOAP-ENV:Fault>"
						+"<faultcode>SOAP-ENV:Server</faultcode>"
						+"<faultstring>" + errore + "</faultstring>"
						+"<faultactor>"+org.openspcoop2.utils.Costanti.OPENSPCOOP2+"</faultactor>"
						+"</SOAP-ENV:Fault>"
						+"</SOAP-ENV:Body></SOAP-ENV:Envelope>";
				mhs.addHeader(Costanti.CONTENT_TYPE, Costanti.CONTENT_TYPE_SOAP_1_1);
			}
				
			byte[] xmlByte = xml.getBytes();
			ByteArrayInputStream bais = new ByteArrayInputStream(xmlByte);
			return _createMessage(mhs, bais, notifierInputStreamParams, false, null, null, xmlByte.length);
		}
		catch(SOAPException soape){
			System.err.println("SOAPException non gestibile durante la creazione di un SOAPFault. " + soape);
		}
		catch(IOException ioe){
			System.err.println("IOException non gestibile durante la creazione di un SOAPFault. " + ioe);
		}
		catch(ParseException ioe){
			System.err.println("ParseException non gestibile durante la creazione di un SOAPFault. " + ioe);
		}
		return null;
	}
	
	
	/*
	 * Utility per debugging. Prende uno Stream e lo porta in String
	 */
    public String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        return sb.toString();
    }
	

    public abstract Element convertoForXPathSearch(Element contenutoAsElement);
}
