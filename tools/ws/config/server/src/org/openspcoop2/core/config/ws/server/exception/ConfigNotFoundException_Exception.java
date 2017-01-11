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
package org.openspcoop2.core.config.ws.server.exception;

import java.io.Serializable;

/**     
 * ConfigNotFoundException_Exception (contains FaultInfo ConfigNotFoundException)
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */


@javax.xml.ws.WebFault(name = "config-not-found-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigNotFoundException_Exception extends Exception implements Serializable {
	
	private static final long serialVersionUID = -1L;

	private org.openspcoop2.core.config.ws.server.exception.ConfigNotFoundException faultInfo;

	// Deprecated: Costruttori utili se i beans vengono usati al posto di quelli generati tramite tools automatici
	@Deprecated
	public ConfigNotFoundException_Exception(String message, java.lang.Throwable t){
		super(message, t);
	}
	@Deprecated
	public ConfigNotFoundException_Exception(String message){
		super(message);
	}
	
	public ConfigNotFoundException_Exception(String message, org.openspcoop2.core.config.ws.server.exception.ConfigNotFoundException faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
    }

	public ConfigNotFoundException_Exception(String message, org.openspcoop2.core.config.ws.server.exception.ConfigNotFoundException faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public org.openspcoop2.core.config.ws.server.exception.ConfigNotFoundException getFaultInfo() {
		return this.faultInfo;
	}

}