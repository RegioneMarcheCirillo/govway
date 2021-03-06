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


package org.openspcoop2.pdd.config.loader.cli;

import java.io.InputStream;
import java.util.Properties;

/**
* LoaderProperties
*
* @author Poli Andrea (apoli@link.it)
* @author $Author$
* @version $Rev$, $Date$
*/
public class LoaderProperties {
	
	private static LoaderProperties staticInstance = null;
	private static synchronized void init() throws Exception{
		if(LoaderProperties.staticInstance == null){
			LoaderProperties.staticInstance = new LoaderProperties();
		}
	}
	public static LoaderProperties getInstance() throws Exception{
		if(LoaderProperties.staticInstance == null){
			LoaderProperties.init();
		}
		return LoaderProperties.staticInstance;
	}
	
	
	
	
	private static String PROPERTIES_FILE = "/config_loader.cli.properties";
	
	private String protocolloDefault = null;
	
	private boolean policy_enable = false;
	private boolean configurazioneGenerare_enable = false;
	
	private String nomePddOperativa = null;
	private String tipoPddArchivio = null;
	
	private boolean isAbilitatoControlloUnicitaImplementazioneAccordoPerSoggetto;
	private boolean isAbilitatoControlloUnicitaImplementazionePortTypePerSoggetto;
	
	private String utente = null;
		
	private String utenze_password = null;

	private String applicativi_password = null;
	private int applicativi_apiKey_passwordGenerated_length = -1;
	private boolean applicativi_basic_password_enableConstraints = false;
	
	private String soggetti_password = null;
	private int soggetti_apiKey_passwordGenerated_length = -1;
	private boolean soggetti_basic_password_enableConstraints = false;
	
	
	public LoaderProperties() throws Exception {

		Properties props = new Properties();
		try {
			InputStream is = LoaderProperties.class.getResourceAsStream(LoaderProperties.PROPERTIES_FILE);
			props.load(is);
		} catch(Exception e) {
			throw new Exception("Errore durante l'init delle properties", e);
		}
		
		// PROPERTIES
				
		this.protocolloDefault = this.getProperty(props, "protocolloDefault", true);
		
		this.policy_enable = this.getBooleanProperty(props, "policy.enable", true);
		this.configurazioneGenerare_enable = this.getBooleanProperty(props, "configurazioneGenerare.enable", true);
		
		this.nomePddOperativa = this.getProperty(props, "nomePddOperativa", false);
		this.tipoPddArchivio = this.getProperty(props, "tipoPddArchivio", true);
		
		this.utente = this.getProperty(props, "utente", true);
		
		this.isAbilitatoControlloUnicitaImplementazioneAccordoPerSoggetto = this.getBooleanProperty(props, "accordi.implementazioneUnicaPerSoggetto", true);
		this.isAbilitatoControlloUnicitaImplementazionePortTypePerSoggetto = this.getBooleanProperty(props, "accordi.portType.implementazioneUnicaPerSoggetto", true);
		
		this.utenze_password = this.getProperty(props, "utenze.password", true);

		this.applicativi_password = this.getProperty(props, "applicativi.password", true);
		this.applicativi_apiKey_passwordGenerated_length = this.getIntProperty(props, "applicativi.api_key.passwordGenerated.length", true);
		this.applicativi_basic_password_enableConstraints = this.getBooleanProperty(props, "applicativi.basic.password.enableConstraints", true);
		
		this.soggetti_password = this.getProperty(props, "soggetti.password", true);
		this.soggetti_apiKey_passwordGenerated_length = this.getIntProperty(props, "soggetti.api_key.passwordGenerated.length", true);
		this.soggetti_basic_password_enableConstraints = this.getBooleanProperty(props, "soggetti.basic.password.enableConstraints", true);

	}
	
	private String getProperty(Properties props,String name,boolean required) throws Exception{
		String tmp = props.getProperty(name);
		if(tmp==null){
			if(required){
				throw new Exception("Property '"+name+"' not found");
			}
			else{
				return null;
			}
		}
		else{
			return tmp.trim();
		}
	}
	private boolean getBooleanProperty(Properties props,String name,boolean required) throws Exception{
		String tmp = this.getProperty(props, name, required);
		if(tmp!=null){
			try{
				return Boolean.parseBoolean(tmp);
			}catch(Exception e){
				throw new Exception("Property '"+name+"' wrong int format: "+e.getMessage());
			}
		}
		else{
			return false;
		}
	}
	private int getIntProperty(Properties props,String name,boolean required) throws Exception{
		String tmp = this.getProperty(props, name, required);
		if(tmp!=null){
			try{
				return Integer.valueOf(tmp);
			}catch(Exception e){
				throw new Exception("Property '"+name+"' wrong int format: "+e.getMessage());
			}
		}
		else{
			return -1;
		}
	}
	
	
	public String getProtocolloDefault() {
		return this.protocolloDefault;
	}
	
	public boolean isPolicy_enable() {
		return this.policy_enable;
	}
	public boolean isConfigurazioneGenerare_enable() {
		return this.configurazioneGenerare_enable;
	}
	
	public String getNomePddOperativa() {
		return this.nomePddOperativa;
	}
	public String getTipoPddArchivio() {
		return this.tipoPddArchivio;
	}
	
	public boolean isAbilitatoControlloUnicitaImplementazioneAccordoPerSoggetto() {
		return this.isAbilitatoControlloUnicitaImplementazioneAccordoPerSoggetto;
	}
	public boolean isAbilitatoControlloUnicitaImplementazionePortTypePerSoggetto() {
		return this.isAbilitatoControlloUnicitaImplementazionePortTypePerSoggetto;
	}
	
	public String getUtente() {
		return this.utente;
	}
	
	public String getUtenze_password() {
		return this.utenze_password;
	}
	public String getApplicativi_password() {
		return this.applicativi_password;
	}
	public int getApplicativi_apiKey_passwordGenerated_length() {
		return this.applicativi_apiKey_passwordGenerated_length;
	}
	public boolean isApplicativi_basic_password_enableConstraints() {
		return this.applicativi_basic_password_enableConstraints;
	}
	public String getSoggetti_password() {
		return this.soggetti_password;
	}
	public int getSoggetti_apiKey_passwordGenerated_length() {
		return this.soggetti_apiKey_passwordGenerated_length;
	}
	public boolean isSoggetti_basic_password_enableConstraints() {
		return this.soggetti_basic_password_enableConstraints;
	}
}
