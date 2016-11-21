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

import java.io.Serializable;

import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.message.exception.MessageException;

/**
 * AbstractConfigurationServiceBinding
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */
public abstract class AbstractConfigurationServiceBinding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ServiceBinding serviceBinding;
	protected boolean enabled;
	protected AbstractMediaTypeCollection request;
	protected AbstractMediaTypeCollection response;
	protected IntegrationErrorCollection internalIntegrationErrorConfiguration;
	protected IntegrationErrorCollection externalIntegrationErrorConfiguration;

	public AbstractConfigurationServiceBinding(ServiceBinding serviceBinding,boolean enabled,
			IntegrationErrorCollection internalIntegrationErrorConfiguration,
			IntegrationErrorCollection externalIntegrationErrorConfiguration) throws MessageException{
		if(serviceBinding==null){
			throw new MessageException("ServiceBinding not defined");
		}
		this.serviceBinding = serviceBinding;
		this.enabled = enabled;
		if(enabled){
			this.initMediaTypeCollection();
			
			if(internalIntegrationErrorConfiguration==null){
				throw new MessageException("InternalIntegrationErrorConfiguration for binding "+serviceBinding.name()+" not defined");
			}
			if(externalIntegrationErrorConfiguration==null){
				throw new MessageException("ExternalIntegrationErrorConfiguration for binding "+serviceBinding.name()+" not defined");
			}
		}
	}
	
	protected abstract void initMediaTypeCollection();
	
	public ServiceBinding getServiceBinding() {
		return this.serviceBinding;
	}
	public boolean isEnabled() {
		return this.enabled;
	}
	public AbstractMediaTypeCollection getRequest() {
		return this.request;
	}
	public AbstractMediaTypeCollection getResponse() {
		return this.response;
	}
	public IntegrationErrorCollection getInternalIntegrationErrorConfiguration() {
		return this.internalIntegrationErrorConfiguration;
	}
	public IntegrationErrorCollection getExternalIntegrationErrorConfiguration() {
		return this.externalIntegrationErrorConfiguration;
	}
}