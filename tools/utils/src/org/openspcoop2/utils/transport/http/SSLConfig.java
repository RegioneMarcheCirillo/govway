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


package org.openspcoop2.utils.transport.http;

import java.io.Serializable;
import java.security.KeyStore;
import java.security.cert.CertStore;


/**
 * SSLConfig
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class SSLConfig implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// AUTENTICAZIONE SERVER:
	// TrustStore
	private KeyStore trustStore;
	// Path del trustStore che contiene il certificato del server.
	private String trustStoreLocation;
	// Password del trustStore che contiene il certificato del server.
	private String trustStorePassword;
	// the standard name of the requested trust management algorithm
	private String trustManagementAlgorithm;
	// tipo del truststore
	private String trustStoreType;
	// CRLs
	private String trustStoreCRLsLocation;
	// CertStore
	private CertStore trustStoreCRLs;
	
	
	// AUTENTICAZIONE CLIENT:
	// KeyStore
	private KeyStore keyStore;
	// Path del keyStore che contiene il certificato del client e la chiave privata del client.
	private String keyStoreLocation;
	// Password del keyStore che contiene il certificato del client
	private String keyStorePassword;
	// Alias della chiave privata
	private String keyAlias;
	// Password della chiave privata
	private String keyPassword;
	// the standard name of the requested key management algorithm
	private String keyManagementAlgorithm;
	// tipo del keystore
	private String keyStoreType;

	// HostName verifier
	private boolean hostnameVerifier = true;
	// Eventuale classe da utilizzare per effettuare hostnameVerifier al posto di quella di default
	private String classNameHostnameVerifier;
	
	// TipologiaSSL
	private String sslType= null;
	

	
	public KeyStore getTrustStore() {
		return this.trustStore;
	}

	public void setTrustStore(KeyStore trustStore) {
		this.trustStore = trustStore;
	}

	public KeyStore getKeyStore() {
		return this.keyStore;
	}

	public void setKeyStore(KeyStore keyStore) {
		this.keyStore = keyStore;
	}
	
	public String getTrustStoreLocation() {
		return this.trustStoreLocation;
	}

	public void setTrustStoreLocation(String trustStoreLocation) {
		this.trustStoreLocation = trustStoreLocation;
	}

	public String getTrustStorePassword() {
		return this.trustStorePassword;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getTrustManagementAlgorithm() {
		return this.trustManagementAlgorithm;
	}

	public void setTrustManagementAlgorithm(String trustManagementAlgorithm) {
		this.trustManagementAlgorithm = trustManagementAlgorithm;
	}

	public String getTrustStoreType() {
		return this.trustStoreType;
	}

	public void setTrustStoreType(String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}
	
	public String getTrustStoreCRLsLocation() {
		return this.trustStoreCRLsLocation;
	}

	public void setTrustStoreCRLsLocation(String trustStoreCRLsLocation) {
		this.trustStoreCRLsLocation = trustStoreCRLsLocation;
	}

	public CertStore getTrustStoreCRLs() {
		return this.trustStoreCRLs;
	}

	public void setTrustStoreCRLs(CertStore trustStoreCRLs) {
		this.trustStoreCRLs = trustStoreCRLs;
	}

	public String getKeyStoreLocation() {
		return this.keyStoreLocation;
	}

	public void setKeyStoreLocation(String keyStoreLocation) {
		this.keyStoreLocation = keyStoreLocation;
	}

	public String getKeyStorePassword() {
		return this.keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyAlias() {
		return this.keyAlias;
	}

	public void setKeyAlias(String keyAlias) {
		this.keyAlias = keyAlias;
	}
	
	public String getKeyPassword() {
		return this.keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public String getKeyManagementAlgorithm() {
		return this.keyManagementAlgorithm;
	}

	public void setKeyManagementAlgorithm(String keyManagementAlgorithm) {
		this.keyManagementAlgorithm = keyManagementAlgorithm;
	}

	public String getKeyStoreType() {
		return this.keyStoreType;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public boolean isHostnameVerifier() {
		return this.hostnameVerifier;
	}

	public void setHostnameVerifier(boolean hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}

	public String getClassNameHostnameVerifier() {
		return this.classNameHostnameVerifier;
	}

	public void setClassNameHostnameVerifier(String classNameHostnameVerifier) {
		this.classNameHostnameVerifier = classNameHostnameVerifier;
	}

	public String getSslType() {
		return this.sslType;
	}

	public void setSslType(String sslType) {
		this.sslType = sslType;
	}
}
