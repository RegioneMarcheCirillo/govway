/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
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

package org.openspcoop2.protocol.engine;

import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.message.config.ServiceBindingConfiguration;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.protocol.sdk.IProtocolFactory;

/**
 * RequestInfo
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RequestInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private URLProtocolContext protocolContext;
	private ServiceBindingConfiguration bindingConfig;
	private ServiceBinding integrationServiceBinding;
	private MessageType integrationRequestMessageType;
	private ServiceBinding protocolServiceBinding;
	private MessageType protocolRequestMessageType;
	private IProtocolFactory<?> protocolFactory;
	private IDSoggetto identitaPdD;
	private IDSoggetto fruitore;
	private IDServizio idServizio;
	private String idTransazione;
	private String messageFactory;
	
	public String getMessageFactory() {
		return this.messageFactory;
	}
	public void setMessageFactory(String messageFactory) {
		this.messageFactory = messageFactory;
	}
	public String getIdTransazione() {
		return this.idTransazione;
	}
	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}
	public IProtocolFactory<?> getProtocolFactory() {
		return this.protocolFactory;
	}
	public void setProtocolFactory(IProtocolFactory<?> protocolFactory) {
		this.protocolFactory = protocolFactory;
	}
	public URLProtocolContext getProtocolContext() {
		return this.protocolContext;
	}
	public void setProtocolContext(URLProtocolContext protocolContext) {
		this.protocolContext = protocolContext;
	}
	public ServiceBindingConfiguration getBindingConfig() {
		return this.bindingConfig;
	}
	public void setBindingConfig(ServiceBindingConfiguration bindingConfig) {
		this.bindingConfig = bindingConfig;
	}
	public ServiceBinding getIntegrationServiceBinding() {
		return this.integrationServiceBinding;
	}
	public void setIntegrationServiceBinding(ServiceBinding integrationServiceBinding) {
		this.integrationServiceBinding = integrationServiceBinding;
	}
	public ServiceBinding getProtocolServiceBinding() {
		return this.protocolServiceBinding;
	}
	public void setProtocolServiceBinding(ServiceBinding protocolServiceBinding) {
		this.protocolServiceBinding = protocolServiceBinding;
	}
	public MessageType getIntegrationRequestMessageType() {
		return this.integrationRequestMessageType;
	}
	public void setIntegrationRequestMessageType(MessageType integrationRequestMessageType) {
		this.integrationRequestMessageType = integrationRequestMessageType;
	}
	public MessageType getProtocolRequestMessageType() {
		return this.protocolRequestMessageType;
	}
	public void setProtocolRequestMessageType(MessageType protocolRequestMessageType) {
		this.protocolRequestMessageType = protocolRequestMessageType;
	}
	public IDSoggetto getIdentitaPdD() {
		return this.identitaPdD;
	}
	public void setIdentitaPdD(IDSoggetto identitaPdD) {
		this.identitaPdD = identitaPdD;
	}
	public IDSoggetto getFruitore() {
		return this.fruitore;
	}
	public void setFruitore(IDSoggetto fruitore) {
		this.fruitore = fruitore;
	}
	public IDServizio getIdServizio() {
		return this.idServizio;
	}
	public void setIdServizio(IDServizio idServizio) {
		this.idServizio = idServizio;
	}
}
