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

package org.openspcoop2.core.registry.ws.client.portadominio.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-12-12T11:38:03.427+01:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "registry-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class RegistryNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.registry.ws.client.portadominio.search.RegistryNotAuthorizedException registryNotAuthorizedException;

    public RegistryNotAuthorizedException_Exception() {
        super();
    }
    
    public RegistryNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public RegistryNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryNotAuthorizedException_Exception(String message, org.openspcoop2.core.registry.ws.client.portadominio.search.RegistryNotAuthorizedException registryNotAuthorizedException) {
        super(message);
        this.registryNotAuthorizedException = registryNotAuthorizedException;
    }

    public RegistryNotAuthorizedException_Exception(String message, org.openspcoop2.core.registry.ws.client.portadominio.search.RegistryNotAuthorizedException registryNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.registryNotAuthorizedException = registryNotAuthorizedException;
    }

    public org.openspcoop2.core.registry.ws.client.portadominio.search.RegistryNotAuthorizedException getFaultInfo() {
        return this.registryNotAuthorizedException;
    }
}
