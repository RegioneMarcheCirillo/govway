/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
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

package org.openspcoop2.pdd.core.connettori;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.openspcoop2.core.config.Connettore;
import org.openspcoop2.core.config.driver.IDriverConfigurazioneGet;
import org.openspcoop2.core.config.driver.db.DriverConfigurazioneDB;
import org.openspcoop2.core.constants.CostantiConnettori;
import org.openspcoop2.core.constants.TipiConnettore;
import org.openspcoop2.core.registry.driver.IDriverRegistroServiziGet;
import org.openspcoop2.core.registry.driver.db.DriverRegistroServiziDB;
import org.openspcoop2.pdd.config.ConfigurazionePdDManager;
import org.openspcoop2.pdd.config.ConfigurazionePdDReader;
import org.openspcoop2.pdd.core.token.Costanti;
import org.openspcoop2.pdd.core.token.PolicyNegoziazioneToken;
import org.openspcoop2.protocol.registry.RegistroServiziReader;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.io.Base64Utilities;
import org.openspcoop2.utils.resources.Loader;
import org.openspcoop2.utils.transport.http.HttpConstants;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.SSLUtilities;

/**
 * ConnettoreCheck
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConnettoreCheck {

	public static boolean checkSupported(org.openspcoop2.core.registry.Connettore connettore) {
		return checkSupported(connettore.mappingIntoConnettoreConfigurazione());
	}
	public static boolean checkSupported(Connettore connettore) {
		
		TipiConnettore tipo = null;
		try{
			tipo = TipiConnettore.valueOf(connettore.getTipo().toUpperCase());
			if(tipo!=null) {
				switch (tipo) {
				case HTTP:
				case HTTPS:
					return true;

				default:
					return false;
				}
			}
		}catch(Exception e) {
		}
		return false;
	}
	
	public static void check(long idConnettore, boolean registro) throws ConnettoreException{
		if(registro) {
			Enumeration<IDriverRegistroServiziGet> drivers = RegistroServiziReader.getDriverRegistroServizi().elements();
			while (drivers.hasMoreElements()) {
				IDriverRegistroServiziGet iDriverRegistroServiziGet = (IDriverRegistroServiziGet) drivers.nextElement();
				if(iDriverRegistroServiziGet instanceof DriverRegistroServiziDB) {
					try {
						org.openspcoop2.core.registry.Connettore connettore = ((DriverRegistroServiziDB)iDriverRegistroServiziGet).getConnettore(idConnettore);
						check(connettore);
						return;
					}catch(Throwable e) {
						throw new ConnettoreException(e.getMessage(),e);
					}
				}
			}
		}
		else {
			IDriverConfigurazioneGet iDriverConfigurazioneGet = ConfigurazionePdDReader.getDriverConfigurazionePdD();
			if(iDriverConfigurazioneGet instanceof DriverConfigurazioneDB) {
				try {
					Connettore connettore = ((DriverConfigurazioneDB)iDriverConfigurazioneGet).getConnettore(idConnettore);
					check(connettore);
					return;
				}catch(Throwable e) {
					throw new ConnettoreException(e.getMessage(),e);
				}
			}
		}
		throw new ConnettoreException("Connettore con id '"+idConnettore+"' non trovato");
	}
	public static void check(String nomeConnettore, boolean registro) throws ConnettoreException{
		if(registro) {
			Enumeration<IDriverRegistroServiziGet> drivers = RegistroServiziReader.getDriverRegistroServizi().elements();
			while (drivers.hasMoreElements()) {
				IDriverRegistroServiziGet iDriverRegistroServiziGet = (IDriverRegistroServiziGet) drivers.nextElement();
				if(iDriverRegistroServiziGet instanceof DriverRegistroServiziDB) {
					try {
						org.openspcoop2.core.registry.Connettore connettore = ((DriverRegistroServiziDB)iDriverRegistroServiziGet).getConnettore(nomeConnettore);
						check(connettore);
						return;
					}catch(Throwable e) {
						throw new ConnettoreException(e.getMessage(),e);
					}
				}
			}
		}
		else {
			IDriverConfigurazioneGet iDriverConfigurazioneGet = ConfigurazionePdDReader.getDriverConfigurazionePdD();
			if(iDriverConfigurazioneGet instanceof DriverConfigurazioneDB) {
				try {
					Connettore connettore = ((DriverConfigurazioneDB)iDriverConfigurazioneGet).getConnettore(nomeConnettore);
					check(connettore);
					return;
				}catch(Throwable e) {
					throw new ConnettoreException(e.getMessage(),e);
				}
			}
		}
		
		throw new ConnettoreException("Connettore con nome '"+nomeConnettore+"' non trovato");
	}

	public static void check(org.openspcoop2.core.registry.Connettore connettore) throws ConnettoreException{
		_check(connettore.mappingIntoConnettoreConfigurazione());
	}
	public static void check(Connettore connettore) throws ConnettoreException{
		_check(connettore);
	}
	private static void _check(Connettore connettore) throws ConnettoreException{
		
		if(checkSupported(connettore)==false) {
			throw new ConnettoreException("Tipo '"+connettore.getTipo()+"' non supportato");
		}
		
		try {
			_checkTokenPolicy(connettore);
		}catch(Throwable e) {
			throw new ConnettoreException(e.getMessage(),e);
		}
		
		TipiConnettore tipo = TipiConnettore.valueOf(connettore.getTipo().toUpperCase());
		switch (tipo) {
		case HTTP:
		case HTTPS:
			try {
				_checkHTTP(tipo, connettore);
			}catch(Throwable e) {
				throw new ConnettoreException(e.getMessage(),e);
			}
			break;

		default:
			break;
		}
		
	}
	
	private static void _checkTokenPolicy(Connettore connettore) throws Exception {
		
		Map<String,String> properties = connettore.getProperties();
		
		PolicyNegoziazioneToken policyNegoziazioneToken = null;
		if(properties!=null && !properties.isEmpty()) {
			Iterator<String> it = properties.keySet().iterator();
			while (it.hasNext()) {
				String propertyName = (String) it.next();
				if(CostantiConnettori.CONNETTORE_TOKEN_POLICY.equals(propertyName)) {
					String tokenPolicy = properties.get(propertyName);
					if(tokenPolicy!=null && !"".equals(tokenPolicy)) {
						boolean forceNoCache = true;
						policyNegoziazioneToken = ConfigurazionePdDManager.getInstance().getPolicyNegoziazioneToken(forceNoCache, tokenPolicy);
					}
				}
			}
		}
		
		if(policyNegoziazioneToken!=null) {
			
			String endpoint = policyNegoziazioneToken.getEndpoint();
			
			// Nell'endpoint config ci finisce i timeout e la configurazione proxy
			Properties endpointConfig = policyNegoziazioneToken.getProperties().get(Costanti.POLICY_ENDPOINT_CONFIG);
			
			boolean https = policyNegoziazioneToken.isEndpointHttps();
			boolean httpsClient = false;
			Properties sslConfig = null;
			Properties sslClientConfig = null;
			if(https) {
				sslConfig = policyNegoziazioneToken.getProperties().get(Costanti.POLICY_ENDPOINT_SSL_CONFIG);
				httpsClient = policyNegoziazioneToken.isHttpsAuthentication();
				if(httpsClient) {
					sslClientConfig = policyNegoziazioneToken.getProperties().get(Costanti.POLICY_ENDPOINT_SSL_CLIENT_CONFIG);
				}
			}
			boolean basic = policyNegoziazioneToken.isBasicAuthentication();
			String username = null;
			String password = null;
			if(basic) {
				username = policyNegoziazioneToken.getBasicAuthentication_username();
				password = policyNegoziazioneToken.getBasicAuthentication_password();
			}
			
			Connettore connettoreTestPolicy = new Connettore();
			Map<String, String> mapProperties = new HashMap<>();
			mapProperties.put(CostantiConnettori.CONNETTORE_LOCATION, endpoint);
			putAll(endpointConfig, mapProperties);
			if(https) {
				putAll(sslConfig, mapProperties);
				if(httpsClient) {
					putAll(sslClientConfig, mapProperties);
				}
			}
			if(basic) {
				mapProperties.put(CostantiConnettori.CONNETTORE_USERNAME, username);
				mapProperties.put(CostantiConnettori.CONNETTORE_PASSWORD, password);
			}
			connettoreTestPolicy.setProperties(mapProperties);

			try {
				_checkHTTP(https ? TipiConnettore.HTTPS : TipiConnettore.HTTP, connettoreTestPolicy);
			}catch(Exception e) {
				String prefixConnettore = "[EndpointNegoziazioneToken: "+endpoint+"] ";
				if(endpointConfig.containsKey(CostantiConnettori.CONNETTORE_HTTP_PROXY_HOSTNAME)) {
					String hostProxy = endpointConfig.getProperty(CostantiConnettori.CONNETTORE_HTTP_PROXY_HOSTNAME);
					String portProxy = endpointConfig.getProperty(CostantiConnettori.CONNETTORE_HTTP_PROXY_PORT);
					prefixConnettore = prefixConnettore+" [via Proxy: "+hostProxy+":"+portProxy+"] ";
				}
				throw new Exception(prefixConnettore+e.getMessage(),e);
			}
		}
	}
	private static void putAll(Properties config, Map<String, String> mapProperties) {
		if(config!=null && !config.isEmpty()) {
			Iterator<?> it = config.keySet().iterator();
			while (it.hasNext()) {
				Object object = (Object) it.next();
				if(object instanceof String) {
					String key = (String) object;
					mapProperties.put(key, config.getProperty(key));			
				}
			}
		}
	}
	
	private static void _checkHTTP(TipiConnettore tipoConnettore, Connettore connettore) throws Exception {
		
		ConnettoreHTTPSProperties sslContextProperties = null;
		
		Map<String,String> properties = connettore.getProperties();
		
		if(TipiConnettore.HTTPS.equals(tipoConnettore)){
			sslContextProperties = ConnettoreHTTPSProperties.readProperties(properties);
		}
		
		Proxy.Type proxyType = null;
		String proxyHostname = null;
		int proxyPort = -1;
		String proxyUsername= null;
		String proxyPassword= null;
		if(properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE)!=null){
			
			String tipo = properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE).trim();
			if(CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE_VALUE_HTTP.equals(tipo)){
				proxyType = Proxy.Type.HTTP;
			}
			else if(CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE_VALUE_HTTPS.equals(tipo)){
				proxyType = Proxy.Type.HTTP;
			}
			else{
				throw new Exception( "Proprieta' '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE
						+"' non corretta. Impostato un tipo sconosciuto ["+tipo+"] (valori ammessi: "+CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE_VALUE_HTTP
						+","+CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE_VALUE_HTTPS+")");
			}
			
			proxyHostname = properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_HOSTNAME);
			if(proxyHostname!=null){
				proxyHostname = proxyHostname.trim();
			}else{
				throw new Exception( "Proprieta' '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_HOSTNAME+
						"' non impostata, obbligatoria in presenza della proprietà '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE+"'");
			}
			
			String proxyPortTmp = properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_PORT);
			if(proxyPortTmp!=null){
				proxyPortTmp = proxyPortTmp.trim();
			}else{
				throw new Exception( "Proprieta' '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_PORT+
						"' non impostata, obbligatoria in presenza della proprietà '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_TYPE+"'");
			}
			try{
				proxyPort = Integer.parseInt(proxyPortTmp);
			}catch(Exception e){
				throw new Exception( "Proprieta' '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_PORT+"' non corretta: "+ConnettoreBase._readExceptionMessageFromException(e));
			}
			
			
			proxyUsername = properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_USERNAME);
			if(proxyUsername!=null){
				proxyUsername = proxyUsername.trim();
			}
			
			proxyPassword = properties.get(CostantiConnettori.CONNETTORE_HTTP_PROXY_PASSWORD);
			if(proxyPassword!=null){
				proxyPassword = proxyPassword.trim();
			}else{
				if(proxyUsername!=null){
					throw new Exception(  "Proprieta' '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_PASSWORD
							+"' non impostata, obbligatoria in presenza della proprietà '"+CostantiConnettori.CONNETTORE_HTTP_PROXY_USERNAME+"'");
				}
			}
		}
		
		// Gestione https
		SSLContext sslContext = null;
		if(sslContextProperties!=null){
			
			StringBuffer bfSSLConfig = new StringBuffer();
			sslContext = SSLUtilities.generateSSLContext(sslContextProperties, bfSSLConfig);
			
		}
		

		// Creazione URL
		String location = properties.get(CostantiConnettori.CONNETTORE_LOCATION);			
		URL url = new URL( location );
		
		
		// Creazione Connessione
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		boolean connect =  false;
		try {
			if(proxyType==null){
				connection = url.openConnection();
			}
			else{
				if(proxyUsername!=null){
					//The problem with the 2nd code is that it sets a new default Authenticator and 
					// I don't want to do that, because this proxy is only used by a part of the application 
					// and a different part of the application could be using a different proxy.
					// Vedi articolo: http://stackoverflow.com/questions/34877470/basic-proxy-authentification-for-https-urls-returns-http-1-0-407-proxy-authentic
					// Authenticator.setDefault(new HttpAuthenticator(this.proxyUsername, this.proxyPassword));
					
					// Soluzione attuale:
					// Dopo aver instaurato la connesione, più sotto nel codice, viene creato l'header Proxy-Authorization
					// NOTA: Works for HTTP only! Doesn't work for HTTPS!
				}
				
				Proxy proxy = new Proxy(proxyType, new InetSocketAddress(proxyHostname, proxyPort));
				connection = url.openConnection(proxy);
			}
			httpConn = (HttpURLConnection) connection;	
			
			
			// Imposta Contesto SSL se attivo
			if(sslContextProperties!=null){
				HttpsURLConnection httpsConn = (HttpsURLConnection) httpConn;
				httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());
				
				StringBuffer bfLog = new StringBuffer();
				HostnameVerifier hostnameVerifier = SSLUtilities.generateHostnameVerifier(sslContextProperties, bfLog, 
						LoggerWrapperFactory.getLogger(ConnettoreCheck.class), new Loader());
				if(hostnameVerifier!=null){
					httpsConn.setHostnameVerifier(hostnameVerifier);
				}
			}
	
			
			// Gestione automatica del redirect
			// The HttpURLConnection‘s follow redirect is just an indicator, in fact it won’t help you to do the “real” http redirection, you still need to handle it manually.
			/*
			if(followRedirect){
				this.httpConn.setInstanceFollowRedirects(true);
			}
			*/
			// Deve essere impostato a false, altrimenti nel caso si intenda leggere gli header o l'input stream di un 302
			// si ottiene il seguente errore:
			//    java.net.HttpRetryException: cannot retry due to redirection, in streaming mode
			httpConn.setInstanceFollowRedirects(false);
			
			// Proxy Authentication BASIC
			if(proxyType!=null && proxyUsername!=null){
				if(proxyUsername!=null && proxyPassword!=null){
					String authentication = proxyUsername + ":" + proxyPassword;
					authentication = HttpConstants.AUTHORIZATION_PREFIX_BASIC + Base64Utilities.encodeAsString(authentication.getBytes());
					httpConn.setRequestProperty(HttpConstants.PROXY_AUTHORIZATION,authentication);
				}
			}
			
			// Impostazione timeout
			int connectionTimeout = -1;
			int readConnectionTimeout = -1;
			if(properties.get(CostantiConnettori.CONNETTORE_CONNECTION_TIMEOUT)!=null){
				try{
					connectionTimeout = Integer.parseInt(properties.get(CostantiConnettori.CONNETTORE_CONNECTION_TIMEOUT));
				}catch(Exception e){
				}
			}
			if(connectionTimeout==-1){
				connectionTimeout = HttpUtilities.HTTP_CONNECTION_TIMEOUT;
			}
			if(properties.get(CostantiConnettori.CONNETTORE_READ_CONNECTION_TIMEOUT)!=null){
				try{
					readConnectionTimeout = Integer.parseInt(properties.get(CostantiConnettori.CONNETTORE_READ_CONNECTION_TIMEOUT));
				}catch(Exception e){
				}
			}
			if(readConnectionTimeout==-1){
				readConnectionTimeout = HttpUtilities.HTTP_READ_CONNECTION_TIMEOUT;
			}
			httpConn.setConnectTimeout(connectionTimeout);
			httpConn.setReadTimeout(readConnectionTimeout);
			
			// Authentication BASIC
			String user = properties.get(CostantiConnettori.CONNETTORE_USERNAME);
			String password = properties.get(CostantiConnettori.CONNETTORE_PASSWORD);
			if(user!=null && password!=null){
				String authentication = user + ":" + password;
				authentication = HttpConstants.AUTHORIZATION_PREFIX_BASIC + Base64Utilities.encodeAsString(authentication.getBytes());
				httpConn.setRequestProperty(HttpConstants.AUTHORIZATION,authentication);
			}
			
			// Check
			connect = true;
			httpConn.connect();
		}
		catch(Exception e) {
			String msgException = ConnettoreBase._readExceptionMessageFromException(e);
			throw new Exception(msgException, e);
		}
		finally {
			try {
				if(httpConn!=null && connect) {
					httpConn.disconnect();
				}
			}catch(Exception e) {}
		}
	}
	
}
