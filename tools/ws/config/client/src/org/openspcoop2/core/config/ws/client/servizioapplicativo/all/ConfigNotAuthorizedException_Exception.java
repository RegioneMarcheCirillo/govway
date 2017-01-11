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

package org.openspcoop2.core.config.ws.client.servizioapplicativo.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-12-12T11:25:12.611+01:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "config-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.config.ws.client.servizioapplicativo.all.ConfigNotAuthorizedException configNotAuthorizedException;

    public ConfigNotAuthorizedException_Exception() {
        super();
    }
    
    public ConfigNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public ConfigNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigNotAuthorizedException_Exception(String message, org.openspcoop2.core.config.ws.client.servizioapplicativo.all.ConfigNotAuthorizedException configNotAuthorizedException) {
        super(message);
        this.configNotAuthorizedException = configNotAuthorizedException;
    }

    public ConfigNotAuthorizedException_Exception(String message, org.openspcoop2.core.config.ws.client.servizioapplicativo.all.ConfigNotAuthorizedException configNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.configNotAuthorizedException = configNotAuthorizedException;
    }

    public org.openspcoop2.core.config.ws.client.servizioapplicativo.all.ConfigNotAuthorizedException getFaultInfo() {
        return this.configNotAuthorizedException;
    }
}
