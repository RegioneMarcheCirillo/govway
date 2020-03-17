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
package org.openspcoop2.core.monitor.rs.server.config;

import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.service.authorization.AuthorizationConfig;
import org.openspcoop2.web.monitor.core.config.ApplicationProperties;
import org.openspcoop2.web.monitor.core.core.Utility;

/**     
 * ServerProperties
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ServerProperties  {

	/** Logger utilizzato per errori eventuali. */
	private Logger log = null;
	
	/** Reader delle proprieta' impostate nel file 'rs-api-monitor.properties' */
	private ServerInstanceProperties reader;
	
	/** Copia Statica */
	private static ServerProperties serverProperties = null;
	
	
	public ServerProperties(String confDir,Logger log) throws Exception {

		if(log!=null)
			this.log = log;
		else
			this.log = LoggerWrapperFactory.getLogger(ServerProperties.class);
		
		/* ---- Lettura del cammino del file di configurazione ---- */
		Properties propertiesReader = new Properties();
		java.io.InputStream properties = null;
		try{  
			properties = DatasourceProperties.class.getResourceAsStream("/rs-api-monitor.properties");
			if(properties==null){
				throw new Exception("File '/rs-api-monitor.properties' not found");
			}
			propertiesReader.load(properties);
		}catch(Exception e) {
			this.log.error("Riscontrato errore durante la lettura del file 'rs-api-monitor.properties': \n\n"+e.getMessage());
		    throw new Exception("RS Api MonitorProperties initialize error: "+e.getMessage());
		}finally{
		    try{
				if(properties!=null)
				    properties.close();
		    }catch(Exception er){}
		}

		this.reader = new ServerInstanceProperties(propertiesReader, this.log, confDir);
		
		this.log.info("Inizializzazione ApplicationProperties in corso...");
		try{
			ApplicationProperties.initialize(log, "/rs-api-monitor.properties", ConstantsEnv.OPENSPCOOP2_RS_API_MONITOR_PROPERTIES, ConstantsEnv.OPENSPCOOP2_RS_API_MONITOR_LOCAL_PATH);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
		this.log.info("Inizializzazione ApplicationProperties effettuata con successo");
	}


	/**
	 * Il Metodo si occupa di inizializzare il propertiesReader 
	 *
	 * 
	 */
	public static boolean initialize(String confDir,Logger log){

		try {
		    ServerProperties.serverProperties = new ServerProperties(confDir,log);	
		    return true;
		}
		catch(Exception e) {
			log.error("Errore durante l'inizializzazione del ServerProperties: "+e.getMessage(),e);		   
		    return false;
		}
	}
    
	/**
	 * Ritorna l'istanza di questa classe
	 *
	 * @return Istanza di Properties
	 * 
	 */
	public static ServerProperties getInstance() throws UtilsException{
		if(ServerProperties.serverProperties==null){
	    	throw new UtilsException("ServerProperties non inizializzato");
	    }
	    return ServerProperties.serverProperties;
	}
    
	public static void updateLocalImplementation(Properties prop){
		ServerProperties.serverProperties.reader.setLocalObjectImplementation(prop);
	}








	/* ********  M E T O D I  ******** */

	public String readProperty(boolean required,String property) throws UtilsException{
		String tmp = this.reader.getValue_convertEnvProperties(property);
		if(tmp==null){
			if(required){
				throw new UtilsException("Property ["+property+"] not found");
			}
			else{
				return null;
			}
		}else{
			return tmp.trim();
		}
	}
	
	public Properties getProperties() throws UtilsException{
		Properties p = new Properties();
		Enumeration<?> names = this.reader.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			p.put(name, this.reader.getValue_convertEnvProperties(name));
		}
		return p;
	}
	
	private AuthorizationConfig authConfig = null;
	private synchronized void initAuthorizationConfig() throws UtilsException {
		if(this.authConfig==null) {
			this.authConfig = new AuthorizationConfig(getProperties());
		}
	}
	public AuthorizationConfig getAuthorizationConfig() throws UtilsException {
		if(this.authConfig==null) {
			this.initAuthorizationConfig();
		}
		return this.authConfig;
	}

	
	public String getConfDirectory() throws UtilsException {
		return this.readProperty(false, "confDirectory");
	}
	public String getProtocolloDefault() throws UtilsException {
		return this.readProperty(true, "protocolloDefault");
	}
	
	public boolean isFindall404() throws UtilsException {
		return Boolean.parseBoolean(this.readProperty(true, "findall_404"));
	}
	
	
	public boolean useSoggettoDefault() throws UtilsException {
		if(Utility.isMultitenantAbilitato()) {
			return Boolean.parseBoolean(this.readProperty(true, "multitenant.forzaSoggettoDefault"));
		}
		else {
			return true; // in caso di multitenant disabilitato, il soggetto di default viene sempre impostato
		}
	}
			
	public String getSoggettoDefaultIfEnabled(String protocollo) throws UtilsException {
		if(this.useSoggettoDefault()) {
			String p = this.readProperty(false, protocollo+".soggetto");
			if(p!=null) {
				return p;
			}
			return this.readProperty(true, "soggetto");
		}
		else {
			throw new UtilsException("Utilizzo del soggetto di default non abilitato");
		}
	}


	public org.openspcoop2.utils.service.context.ContextConfig getContextConfig() throws UtilsException {
		org.openspcoop2.utils.service.context.ContextConfig config = new org.openspcoop2.utils.service.context.ContextConfig();
		config.setClusterId(this.readProperty(false, "clusterId"));
		config.setDump(Boolean.parseBoolean(this.readProperty(true, "dump")));
		config.setEmitTransaction(Boolean.parseBoolean(this.readProperty(true, "transaction")));
		config.setServiceType(this.readProperty(false, "service.type"));
		config.setServiceName(this.readProperty(false, "service.name"));
		config.setServiceVersion(Integer.parseInt(this.readProperty(false, "service.version")));
		return config;
	}
	

}
