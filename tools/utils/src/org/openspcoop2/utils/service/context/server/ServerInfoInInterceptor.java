/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
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

package org.openspcoop2.utils.service.context.server;

import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.ext.logging.event.DefaultLogEventMapper;
import org.apache.cxf.ext.logging.event.LogEvent;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;


/**
 * ServerInfoInInterceptor
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@NoJSR250Annotations
public class ServerInfoInInterceptor extends org.apache.cxf.ext.logging.LoggingInInterceptor {

	private ServerConfig serverConfig;
	private ServerInfoUtilities utilities;
	
	public ServerInfoInInterceptor() {
		super();
	}

	public ServerConfig getServerConfig() {
		return this.serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
		this.utilities = new ServerInfoUtilities(this.serverConfig);
	}
	
	@Override
	public void handleMessage(Message message) throws Fault {
		
		try {

			ServerInfoRequest request = new ServerInfoRequest();
			
			final LogEvent event = new DefaultLogEventMapper().map(message);
			
			request.setAddress(event.getAddress());
			request.setHttpRequestMethod(HttpRequestMethod.valueOf(event.getHttpMethod().toUpperCase()));
			
			this.utilities.processBeforeSend(request);
			
		} catch (Throwable e) {
			LoggerWrapperFactory.getLogger(ServerInfoInInterceptor.class).error(e.getMessage(),e);
			throw new Fault(e);
		}
	}

}

