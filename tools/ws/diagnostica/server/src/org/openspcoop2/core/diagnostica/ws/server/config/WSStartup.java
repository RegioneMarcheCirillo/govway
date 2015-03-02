/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it). 
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


package org.openspcoop2.core.diagnostica.ws.server.config;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
/**
 * Questa classe si occupa di inizializzare tutte le risorse necessarie al webService.
 * 
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 10489 $, $Date: 2015-01-13 10:15:51 +0100 (Tue, 13 Jan 2015) $
 * 
 */

public class WSStartup implements ServletContextListener {

	private static Logger log = null;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(WSStartup.log!=null)
			WSStartup.log.info("Undeploy loader in corso...");

		if(WSStartup.log!=null)
			WSStartup.log.info("Undeploy loader effettuato.");

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		WSStartup.initLog();
		
		WSStartup.initResources();

	}
	
	
	// LOG
	
	public static boolean initializedLog = false;
	
	public static synchronized String initLog(){
		
		String confDir = null;
		try{
			InputStream is = WSStartup.class.getResourceAsStream("/wsdiagnostica.properties");
			try{
				if(is!=null){
					Properties p = new Properties();
					p.load(is);
					confDir = p.getProperty("confDirectory");
					if(confDir!=null){
						confDir = confDir.trim();
					}
				}
			}finally{
				try{
					if(is!=null){
						is.close();
					}
				}catch(Exception eClose){}
			}

		}catch(Exception e){}
		
		if(WSStartup.initializedLog==false){
			
			try{
				WSStartup.log = Logger.getLogger(WSStartup.class);
				LoggerProperties.initialize(WSStartup.log, confDir, null);
				WSStartup.initializedLog = true;
				WSStartup.log = LoggerProperties.getLoggerWS();
				
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		
		return confDir;
	}
	
	
	// RESOURCES
	
	public static boolean initializedResources = false;
	
	public static synchronized void initResources(){
		if(WSStartup.initializedResources==false){
			
			String confDir = WSStartup.initLog();
			
			WSStartup.log.info("Inizializzazione ws diagnostica in corso...");
			
			if(ServerProperties.initialize(confDir,WSStartup.log)==false){
				return;
			}
			
			if(DatasourceProperties.initialize(confDir,WSStartup.log)==false){
				return;
			}
			
			if(DriverDiagnostica.initialize(WSStartup.log)==false){
				return;
			}
			
			WSStartup.initializedResources = true;
			
			WSStartup.log.info("Inizializzazione ws diagnostica effettuata con successo.");
		}
	}

}
