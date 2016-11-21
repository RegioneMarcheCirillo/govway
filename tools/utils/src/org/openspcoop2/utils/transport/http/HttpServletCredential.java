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
package org.openspcoop2.utils.transport.http;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.NotImplementedException;
import org.apache.soap.encoding.soapenc.Base64;
import org.openspcoop2.utils.transport.Credential;
import org.slf4j.Logger;

/**
 * HttpServletCredentials
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */
public class HttpServletCredential extends Credential implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Servlet Request
	private transient HttpServletRequest httpServletRequest;
	
	public HttpServletCredential(){
		super();
	}
	public HttpServletCredential(HttpServletRequest req,Logger log){
		this(req, log, false);
	}
	public HttpServletCredential(HttpServletRequest req,Logger log,boolean debug){
		
		super();
		
		this.httpServletRequest = req;
		
		// Basic (HTTP-Based)
		String auth = req.getHeader(HttpConstants.AUTHORIZATION);
		if(auth != null){
			// Sbustring(6): elimina la parte "Basic "
			String decodeAuth = new String(Base64.decode(auth.substring(6)));
			String [] decodeAuthSplit = decodeAuth.split(":");
			if(decodeAuthSplit.length>1){
				this.username = decodeAuthSplit[0];
				this.password = decodeAuthSplit[1];
			}
			if(debug && log!=null){
				log.info("BasicAuthentication presente nella richiesta, username ["+this.username+"] e password ["+this.password+"]");
			}
		}
		
		// SSL (HTTPS)
		java.security.cert.X509Certificate[] certs =
			(java.security.cert.X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");

		if(certs!=null) {
			if(debug && log!=null){
				try{
					StringBuffer bf = new StringBuffer();
					Credential.printCertificate(bf, certs);
					log.info(bf.toString());
				}catch(Throwable e){
					log.error("Print info certs error: "+e.getMessage(),e);
				}
			}
			if(certs.length > 0){
				//System.out.println("toString ["+certs[0].getSubjectX500Principal().toString()+"]"); // toString e' equivalente a RFC1779
				//System.out.println("getName ["+certs[0].getSubjectX500Principal().getName()+"]");
				//System.out.println("CANONICAL ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.CANONICAL)+"]");
				//System.out.println("RFC1779 ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.RFC1779)+"]");
				//System.out.println("RFC2253 ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.RFC2253)+"]");
				this.subject = certs[0].getSubjectX500Principal().toString();
				this.certs = certs;
			}
		}else{
			if(debug && log!=null){
				log.info("Certificati non presenti nella richiesta");
			}
		}
		
		// getUserPrincipal (SERVLET API)
		if( req.getUserPrincipal()!=null ){
			this.principal = req.getUserPrincipal();
			this.principalName = this.principal.getName();
		}
	}
	
	
	@Override
	public boolean isUserInRole(String role){
		if(this.httpServletRequest!=null){
			return this.httpServletRequest.isUserInRole(role);
		}
		else{
			throw new NotImplementedException();
		}
	}
	
	@Override
	public Object getAttribute(String attributeName){
		if(this.httpServletRequest!=null){
			return this.httpServletRequest.getAttribute(attributeName);
		}
		else{
			throw new NotImplementedException();
		}
	}

}