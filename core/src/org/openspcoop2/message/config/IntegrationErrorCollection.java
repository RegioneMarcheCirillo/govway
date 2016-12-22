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



package org.openspcoop2.message.config;

import java.util.Hashtable;

import org.openspcoop2.message.constants.IntegrationError;
import org.openspcoop2.message.constants.IntegrationErrorMessageType;

/**
 * IntegrationErrorConfiguration
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class IntegrationErrorCollection implements java.io.Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	private Hashtable<String, org.openspcoop2.message.config.IntegrationErrorConfiguration> map = new Hashtable<String, org.openspcoop2.message.config.IntegrationErrorConfiguration>();

	private IntegrationErrorMessageType defaultError;
	
	public void addIntegrationError(IntegrationError errorType, IntegrationErrorMessageType integrationErrorMessageType, Integer httpErrorCode){
		if(this.map.containsKey(errorType.name())){
			this.map.remove(errorType.name());
		}
		org.openspcoop2.message.config.IntegrationErrorConfiguration error = 
				new org.openspcoop2.message.config.IntegrationErrorConfiguration(integrationErrorMessageType,httpErrorCode);
		this.map.put(errorType.name(), error);
		if(IntegrationError.DEFAULT.equals(errorType)){
			this.defaultError = integrationErrorMessageType;
		}
	}
	
	public org.openspcoop2.message.config.IntegrationErrorConfiguration getIntegrationError(IntegrationError errorType){
		if(this.map.containsKey(errorType.name())){
			org.openspcoop2.message.config.IntegrationErrorConfiguration error = this.map.get(errorType.name());
			error.setDefaultErrorType(this.defaultError);
			return error;
		}
		return null;
	}
}





