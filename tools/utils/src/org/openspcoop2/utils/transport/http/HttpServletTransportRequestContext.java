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

package org.openspcoop2.utils.transport.http;

import javax.servlet.http.HttpServletRequest;

import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.Credential;
import org.slf4j.Logger;

/**
 * URL Protocol Context
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */


public class HttpServletTransportRequestContext extends org.openspcoop2.utils.transport.TransportRequestContext implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	public HttpServletTransportRequestContext() throws UtilsException{
		super();
	}
	public HttpServletTransportRequestContext(HttpServletRequest req,Logger logCore) throws UtilsException{
		this(req,logCore,false);
	}
	public HttpServletTransportRequestContext(HttpServletRequest req,Logger logCore, boolean debug) throws UtilsException{
		super();
		
		try {
			
			// Properties FORM Based
			this.parametersFormBased = new java.util.Properties();	       
			java.util.Enumeration<?> en = req.getParameterNames();
			while(en.hasMoreElements()){
				String nomeProperty = (String)en.nextElement();
				this.parametersFormBased.setProperty(nomeProperty,req.getParameter(nomeProperty));
				//log.info("Proprieta': nome["+nomeProperty+"] valore["+req.getParameter(nomeProperty)+"]");
			}

			// Hedear Trasporto
			this.parametersTrasporto = new java.util.Properties();	    
			java.util.Enumeration<?> enTrasporto = req.getHeaderNames();
			while(enTrasporto.hasMoreElements()){
				String nomeProperty = (String)enTrasporto.nextElement();
				this.parametersTrasporto.setProperty(nomeProperty,req.getHeader(nomeProperty));
				//log.info("Proprieta' Trasporto: nome["+nomeProperty+"] valore["+req.getHeader(nomeProperty)+"]");
			}
			
			
			this.webContext = req.getContextPath();
			this.requestURI = req.getRequestURI();
			this.requestType = req.getMethod();
			
			this.credential = new HttpServletCredential(req,logCore,debug);
			
			this.source = buildSource(req, this.credential);
			
		}catch(Exception e){
			throw new UtilsException(e.getMessage(),e);
		}
	}
	
	public static String buildSource(HttpServletRequest req, Credential credenziali){
		String protocollo = "http";
		if(credenziali.getSubject()!=null)
			protocollo = "https";
		String ip = req.getRemoteAddr();
		String port = ""+req.getRemotePort();
		String user = "";
		if(credenziali.getUsername()!=null || credenziali.getSubject()!=null){
			user = HttpConstants.SEPARATOR_SOURCE;
			if(credenziali.getSubject()!=null)
				user=user+credenziali.getSubject();
			else
				user=user+credenziali.getUsername();
		}
		return protocollo+HttpConstants.SEPARATOR_SOURCE+
				ip+HttpConstants.SEPARATOR_SOURCE+port+user;
	}
}
