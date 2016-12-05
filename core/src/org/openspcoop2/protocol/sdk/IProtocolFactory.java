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

package org.openspcoop2.protocol.sdk;

import java.io.Serializable;

import org.slf4j.Logger;
import org.openspcoop2.core.config.driver.IDriverConfigurazioneGet;
import org.openspcoop2.core.registry.driver.IDriverRegistroServiziGet;
import org.openspcoop2.protocol.manifest.Openspcoop2;
import org.openspcoop2.protocol.sdk.archive.IArchive;
import org.openspcoop2.protocol.sdk.builder.IErroreApplicativoBuilder;
import org.openspcoop2.protocol.sdk.builder.IEsitoBuilder;
import org.openspcoop2.protocol.sdk.builder.IBustaBuilder;
import org.openspcoop2.protocol.sdk.config.IProtocolConfiguration;
import org.openspcoop2.protocol.sdk.config.IProtocolManager;
import org.openspcoop2.protocol.sdk.config.IProtocolVersionManager;
import org.openspcoop2.protocol.sdk.config.ITraduttore;
import org.openspcoop2.protocol.sdk.diagnostica.IDiagnosticDriver;
import org.openspcoop2.protocol.sdk.diagnostica.IDiagnosticProducer;
import org.openspcoop2.protocol.sdk.diagnostica.IDiagnosticSerializer;
import org.openspcoop2.protocol.sdk.properties.IConsoleDynamicConfiguration;
import org.openspcoop2.protocol.sdk.registry.IConfigIntegrationReader;
import org.openspcoop2.protocol.sdk.registry.IRegistryReader;
import org.openspcoop2.protocol.sdk.state.IState;
import org.openspcoop2.protocol.sdk.tracciamento.ITracciaDriver;
import org.openspcoop2.protocol.sdk.tracciamento.ITracciaProducer;
import org.openspcoop2.protocol.sdk.tracciamento.ITracciaSerializer;
import org.openspcoop2.protocol.sdk.validator.IValidatoreErrori;
import org.openspcoop2.protocol.sdk.validator.IValidazioneAccordi;
import org.openspcoop2.protocol.sdk.validator.IValidazioneConSchema;
import org.openspcoop2.protocol.sdk.validator.IValidazioneDocumenti;
import org.openspcoop2.protocol.sdk.validator.IValidazioneSemantica;
import org.openspcoop2.protocol.sdk.validator.IValidazioneSintattica;

/**
 * L'implementazione di questa classe fornisce alla Porta di Dominio 
 * l'accesso alle classi che gestiscono gli aspetti di cooperazione, tracciamento
 * e diagnostica dipendenti dal protocollo in uso.
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IProtocolFactory<BustaRawType> extends Serializable {
	
	/* ** INIT ** */
	
	public void init(Logger log,String protocol,ConfigurazionePdD configPdD,Openspcoop2 openspcoop2Manifest) throws ProtocolException;
	
	/* ** INFO SERVIZIO ** */
	
	public String getProtocol();
	public Logger getLogger();
	public ConfigurazionePdD getConfigurazionePdD();
	public Openspcoop2 getManifest();
	
	/* ** PROTOCOL BUILDER ** */
	
	public IBustaBuilder<BustaRawType> createBustaBuilder() throws ProtocolException;
	public IErroreApplicativoBuilder createErroreApplicativoBuilder() throws ProtocolException;
	public IEsitoBuilder createEsitoBuilder() throws ProtocolException;
	
	/* ** PROTOCOL VALIDATOR ** */
	
	public IValidatoreErrori createValidatoreErrori() throws ProtocolException;
	public IValidazioneSintattica<BustaRawType> createValidazioneSintattica() throws ProtocolException;
	public IValidazioneSemantica createValidazioneSemantica() throws ProtocolException;
	public IValidazioneConSchema createValidazioneConSchema() throws ProtocolException;
	public IValidazioneDocumenti createValidazioneDocumenti() throws ProtocolException;
	public IValidazioneAccordi createValidazioneAccordi() throws ProtocolException;
	
	/* ** DIAGNOSTICI ** */
	
	public IDiagnosticDriver createDiagnosticDriver() throws ProtocolException;
	public IDiagnosticProducer createDiagnosticProducer() throws ProtocolException;
	public IDiagnosticSerializer createDiagnosticSerializer() throws ProtocolException;
	
	/* ** TRACCE ** */
	
	public ITracciaDriver createTracciaDriver() throws ProtocolException;
	public ITracciaProducer createTracciaProducer() throws ProtocolException;
	public ITracciaSerializer createTracciaSerializer() throws ProtocolException;
	
	/* ** ARCHIVE ** */
	
	public IArchive createArchive() throws ProtocolException;
	
	/* ** CONFIG ** */
	
	public IProtocolVersionManager createProtocolVersionManager(String version) throws ProtocolException;
	public IProtocolManager createProtocolManager() throws ProtocolException;
	public ITraduttore createTraduttore() throws ProtocolException;
	public IProtocolConfiguration createProtocolConfiguration() throws ProtocolException;
	
	/* ** CONSOLE ** */
	
	public IConsoleDynamicConfiguration createDynamicConfigurationConsole() throws ProtocolException;
	
	/* ** REGISTRY  ** */
	
	public IRegistryReader getRegistryReader(IDriverRegistroServiziGet driver) throws ProtocolException;
	public IRegistryReader getCachedRegistryReader(IState state) throws ProtocolException;
	public IConfigIntegrationReader getConfigIntegrationReader(IDriverConfigurazioneGet driver) throws ProtocolException;
	public IConfigIntegrationReader getCachedConfigIntegrationReader(IState state) throws ProtocolException;
	
}
