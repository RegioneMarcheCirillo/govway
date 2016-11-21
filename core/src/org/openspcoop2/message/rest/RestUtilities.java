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

package org.openspcoop2.message.rest;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.openspcoop2.message.OpenSPCoop2MessageProperties;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.message.exception.MessageException;
import org.openspcoop2.utils.transport.TransportRequestContext;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpConstants;
import org.openspcoop2.utils.transport.http.HttpHeaderTypes;

/**
 * RestUtilities
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */
public class RestUtilities {

	public static String buildUrl(String url,Properties p,TransportRequestContext requestContext){
		
		String baseUrl = url;
		String parameterOriginalUrl = null;
		if(url.contains("?")){
			baseUrl = url.split("\\?")[0];
			if(baseUrl!=null){
				baseUrl.trim();
			}
			parameterOriginalUrl = url.split("\\?")[1]; 
			if(parameterOriginalUrl!=null){
				parameterOriginalUrl.trim();
			}
		}
		
		StringBuffer newUrl = new StringBuffer();
		newUrl.append(baseUrl);
		
		if(requestContext!=null){
			String resourcePath = requestContext.getFunctionParameters();
			if(resourcePath!=null){
				if(resourcePath.startsWith("/")){
					resourcePath = resourcePath.substring(1);
				}
				if(requestContext.getInterfaceName()!=null && resourcePath.startsWith(requestContext.getInterfaceName())){
					resourcePath = resourcePath.substring(requestContext.getInterfaceName().length());
				}
				if(resourcePath.startsWith("/")){
					resourcePath = resourcePath.substring(1);
				}
				if(resourcePath!=null && !"".equals(resourcePath)){
					if(baseUrl.endsWith("/")==false){
						newUrl.append("/");
					}
					newUrl.append(resourcePath);
				}
			}
		}
				
		if(parameterOriginalUrl!=null){
			String [] split = parameterOriginalUrl.split("&");
			if(split!=null){
				if(p==null){
					p = new Properties();
				}
				for (int i = 0; i < split.length; i++) {
					if(split[i].contains("=")){
						String [] splitNomeValore = split[i].split("=");
						if(splitNomeValore!=null){
							String nome = splitNomeValore[0];
							if(nome!=null){
								nome = nome.trim();
							}
							String valore = splitNomeValore[1];
							if(valore!=null){
								valore = valore.trim();
							}
							if(nome!=null && valore!=null){
								p.put(nome,valore);
							}
						}
					}
				}
			}
		}
		
		return TransportUtils.buildLocationWithURLBasedParameter(p, newUrl.toString());
		
	}
	
	public static void initializeTransportHeaders(OpenSPCoop2MessageProperties op2MessageProperties, MessageRole messageRole, 
			Properties transportHeaders, List<String> whiteListHeader) throws MessageException{
		
		try{
			HttpHeaderTypes httpHeader = HttpHeaderTypes.getInstance();
			List<String> headers = httpHeader.getHeaders();
			if(transportHeaders!=null && transportHeaders.size()>0){
				Enumeration<?> enumHeader = transportHeaders.keys();
				while (enumHeader.hasMoreElements()) {
					String key = (String) enumHeader.nextElement();
					if(MessageRole.REQUEST.equals(messageRole)==false){
						if(HttpConstants.RETURN_CODE.equalsIgnoreCase(key)){
							continue;
						}
					}
					if( (headers.contains(key)==false) || (whiteListHeader.contains(key)) ){
						op2MessageProperties.addProperty(key, transportHeaders.getProperty(key));
					}
				}
			}
		}catch(Exception e){
			throw new MessageException(e.getMessage(),e);
		}
		
	}
}