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

package org.openspcoop2.protocol.modipa.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.constants.RuoloContesto;
import org.openspcoop2.core.constants.CostantiDB;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.core.registry.Fruitore;
import org.openspcoop2.core.registry.Property;
import org.openspcoop2.core.registry.ProtocolProperty;
import org.openspcoop2.core.registry.driver.IDServizioFactory;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.pdd.config.ConfigurazionePdDManager;
import org.openspcoop2.pdd.config.UrlInvocazioneAPI;
import org.openspcoop2.protocol.engine.utils.NamingUtils;
import org.openspcoop2.protocol.modipa.config.ModIProperties;
import org.openspcoop2.protocol.modipa.constants.ModIConsoleCostanti;
import org.openspcoop2.protocol.modipa.constants.ModICostanti;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.ProtocolException;
import org.openspcoop2.protocol.sdk.properties.ProtocolPropertiesUtils;
import org.openspcoop2.security.message.constants.SignatureDigestAlgorithm;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.transport.http.HttpConstants;

/**
 * ModIKeystoreConfig
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ModISecurityConfig {

	private String algorithm = null;
	private String digestAlgorithm = null;
	private String c14nAlgorithm = null;
	private boolean x5c = false;
	private boolean x5t = false;
	private boolean x5u = false;
	private String x5url = null;
	private String keyIdentifierMode = null;
	private boolean useSingleCertificate = true;
	private boolean includeSignatureToken = false;
	private int ttl;
	private String audience;
	private boolean checkAudience;
	private String clientId;
	private String issuer;
	private String subject;
	private List<String> httpHeaders = new ArrayList<String>();
	
	public ModISecurityConfig(OpenSPCoop2Message msg, IDSoggetto soggettoFruitore, AccordoServizioParteSpecifica aspsParam, ServizioApplicativo sa, 
			boolean rest, boolean fruizione,  boolean request,
			Busta busta, Busta bustaRichiesta) throws ProtocolException {
		
		// METODO USATO IN IMBUSTAMENTO
		
		ModIProperties modiProperties = ModIProperties.getInstance();
		
		IDServizio idServizio = null;
		try {
			idServizio = IDServizioFactory.getInstance().getIDServizioFromAccordo(aspsParam);
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
		
		List<ProtocolProperty> listProtocolProperties = null;
		Fruitore fruitore = null;
		if(fruizione) {
			if(soggettoFruitore==null) {
				throw new ProtocolException("Fruitore non fornito");
			}
			boolean find = false;
			for (Fruitore fruitoreCheck : aspsParam.getFruitoreList()) {
				if(fruitoreCheck.getTipo().equals(soggettoFruitore.getTipo()) && fruitoreCheck.getNome().equals(soggettoFruitore.getNome())) {
					fruitore = fruitoreCheck;
					listProtocolProperties = fruitoreCheck.getProtocolPropertyList();
					find = true;
					break;
				}
			}
			if(!find) {
				throw new ProtocolException("Fruitore '"+soggettoFruitore+"' non registrato come fruitore dell'accordo parte specifica");
			}
		}
		else {
			listProtocolProperties = aspsParam.getProtocolPropertyList();
		}
		
		if(rest) {
			this.initSharedRest(listProtocolProperties,fruizione,request);
			
			String httpHeaders = ProtocolPropertiesUtils.getOptionalStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HTTP_HEADERS_REST);
			if(httpHeaders!=null && httpHeaders.length()>0) {
				this.httpHeaders = Arrays.asList(httpHeaders.split(","));
			}
		}
		else {
			this.initSharedSoap(listProtocolProperties,fruizione,request);
		}
				
		if(fruizione) {
			if(request) {
				this.ttl = ProtocolPropertiesUtils.getRequiredNumberValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_EXPIRED).intValue();
			}
		}
		else {
			if(!request) {
				this.ttl = ProtocolPropertiesUtils.getRequiredNumberValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_EXPIRED).intValue();
			}
		}
		
		if(fruizione && request) {
			
			/* Audience */
			
			this.audience = ProtocolPropertiesUtils.getOptionalStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_AUDIENCE);
			if(this.audience==null) {
				if(fruitore.getConnettore()!=null && fruitore.getConnettore().sizePropertyList()>0) {
					for (Property p : fruitore.getConnettore().getPropertyList()) {
						if(CostantiDB.CONNETTORE_HTTP_LOCATION.equals(p.getNome())) {
							this.audience = p.getValore();
						}
					}
				}
			}
			if(this.audience==null) {
				throw new ProtocolException("Audience undefined");
			}
			
			
			/* ClientId */
			
			if(sa!=null) {
				this.clientId = ProtocolPropertiesUtils.getOptionalStringValuePropertyConfig(sa.getProtocolPropertyList(), ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE);
				if(this.clientId==null) {
					try {
						this.clientId = NamingUtils.getLabelSoggetto(new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario()))+"/"+sa.getNome();
					}catch(Exception e) {
						throw new ProtocolException(e.getMessage(),e);
					}
				}
			}
			
			
			/* Issuer */
			
			try {
				if(modiProperties.isRestSecurityTokenClaimsIssuerEnabled()) {
					String valore = modiProperties.getRestSecurityTokenClaimsIssuerHeaderValue();
					if(valore!=null && !"".equals(valore)) {
						this.issuer = valore;
					}
					else {
						this.issuer = NamingUtils.getLabelSoggetto(new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario()));
					}
				}
			}catch(Exception e) {
				throw new ProtocolException(e.getMessage(),e);
			}
			
			
			/* Subject */
			
			try {
				if(modiProperties.isRestSecurityTokenClaimsSubjectEnabled()) {
					String valore = modiProperties.getRestSecurityTokenClaimsSubjectHeaderValue();
					if(valore!=null && !"".equals(valore)) {
						this.subject = valore;
					}
					else {
						this.subject = sa.getNome();
					}
				}
			}catch(Exception e) {
				throw new ProtocolException(e.getMessage(),e);
			}
		}
		else if(!fruizione && !request) {
			
			/* Audience */
			
			// 1. Provo ad utilizzare quello definito nell'applicativo
			if(sa!=null) {
				this.audience = ProtocolPropertiesUtils.getOptionalStringValuePropertyConfig(sa.getProtocolPropertyList(), ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE);
			}
			
			// 2. Provo ad utilizzare il clientId presente nel token
			if(this.audience==null) {
				if(bustaRichiesta!=null) {
					this.audience = bustaRichiesta.getProperty(rest ? 
							ModICostanti.MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_CLIENT_ID :
							ModICostanti.MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SOAP_WSA_FROM);
				}
			}
			
			// 3. Provo ad utilizzare il sub presente nel token
			if(this.audience==null) {
				if(bustaRichiesta!=null) {
					this.audience = bustaRichiesta.getProperty(ModICostanti.MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_SUBJECT);
				}
			}
			
			// 4. Utilizzo quello di default
			if(this.audience==null && sa!=null) {
				try {
					this.audience=NamingUtils.getLabelSoggetto(new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario()))+"/"+sa.getNome();
				}catch(Exception e) {
					throw new ProtocolException(e.getMessage(),e);
				}
			}
			
			
			/* ClientId */
			try {
				this.clientId = NamingUtils.getLabelAccordoServizioParteSpecificaSenzaErogatore(idServizio).replace(" ", "/");
			}catch(Exception e) {
				throw new ProtocolException(e.getMessage(),e);
			}
			
			
			/* Issuer */
			
			try {
				if(modiProperties.isRestSecurityTokenClaimsIssuerEnabled()) {
					String valore = modiProperties.getRestSecurityTokenClaimsIssuerHeaderValue();
					if(valore!=null && !"".equals(valore)) {
						this.issuer = valore;
					}
					else {
						this.issuer = NamingUtils.getLabelSoggetto(new IDSoggetto(aspsParam.getTipoSoggettoErogatore(), aspsParam.getNomeSoggettoErogatore()));
					}
				}
			}catch(Exception e) {
				throw new ProtocolException(e.getMessage(),e);
			}
			
			/* Subject */
			
			try {
				if(modiProperties.isRestSecurityTokenClaimsSubjectEnabled()) {
					String valore = modiProperties.getRestSecurityTokenClaimsSubjectHeaderValue();
					if(valore!=null && !"".equals(valore)) {
						this.subject = valore;
					}
					else {
						this.subject = NamingUtils.getLabelAccordoServizioParteSpecificaSenzaErogatore(idServizio).replace(" ", "/");
					}
				}
			}catch(Exception e) {
				throw new ProtocolException(e.getMessage(),e);
			}
		}
		else {
			
			throw new ProtocolException("Use unsupported");
			
		}
		
	}
	
	public ModISecurityConfig(OpenSPCoop2Message msg, IProtocolFactory<?> protocolFactory, IDSoggetto soggettoFruitore, AccordoServizioParteSpecifica aspsParam, ServizioApplicativo sa, boolean rest, boolean fruizione, boolean request) throws ProtocolException {
		
		// METODO USATO IN VALIDAZIONE
		
		List<ProtocolProperty> listProtocolProperties = ModIPropertiesUtils.getProtocolProperties(fruizione, soggettoFruitore, aspsParam);
		
		if(rest) {
			this.initSharedRest(listProtocolProperties,fruizione,request);
		}
		else {
			this.initSharedSoap(listProtocolProperties,fruizione,request);
		}
		
		try {
		
			if(!fruizione && request) {
				this.checkAudience = true;
				this.audience = ProtocolPropertiesUtils.getOptionalStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_AUDIENCE);
				if(this.audience==null) {
					UrlInvocazioneAPI urlInvocazioneApi = ConfigurazionePdDManager.getInstance().getConfigurazioneUrlInvocazione(protocolFactory, 
							RuoloContesto.PORTA_APPLICATIVA,
							rest ? org.openspcoop2.message.constants.ServiceBinding.REST : org.openspcoop2.message.constants.ServiceBinding.SOAP,
									msg.getTransportRequestContext().getInterfaceName(),
									new IDSoggetto(aspsParam.getTipoSoggettoErogatore(), aspsParam.getNomeSoggettoErogatore()));		 
					String prefixGatewayUrl = urlInvocazioneApi.getBaseUrl();
					String contesto = urlInvocazioneApi.getContext();
					this.audience = Utilities.buildUrl(prefixGatewayUrl, contesto);
				}
				if(this.audience==null) {
					throw new ProtocolException("Audience undefined");
				}
			}
			else if(fruizione && !request) {
				if(sa!=null) {
					this.audience = ProtocolPropertiesUtils.getOptionalStringValuePropertyConfig(sa.getProtocolPropertyList(), ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE);
					// 	Se non definito sull'applicativo, non viene serializzato.
					if(this.audience==null) {
						// provo a vedere se viene utilizzato il valore di default
						this.audience=NamingUtils.getLabelSoggetto(new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario()))+"/"+sa.getNome();
					}
				}
				this.checkAudience = ProtocolPropertiesUtils.getBooleanValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE, false);
				if(this.checkAudience) {
					if(this.audience==null) {
						throw new ProtocolException("Configurazione errata; Audience non definito sull'applicativo e verifica abilitata sulla fruizione");
					}
				}
			}
			else {
				
				throw new ProtocolException("Use unsupported");
				
			}
			
		}catch(Exception e) {
			throw new ProtocolException(e);
		}
		
	}
	
	private void initSharedRest(List<ProtocolProperty> listProtocolProperties, boolean fruizione, boolean request) throws ProtocolException {
		
		if(fruizione) {
			if(request) {
				this.algorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_ALG);
			}
		}
		else {
			if(!request) {
				this.algorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_ALG);
			}
		}
		
		if(this.algorithm!=null) {
			if(this.algorithm.contains("256")) {
				this.digestAlgorithm = HttpConstants.DIGEST_ALGO_SHA_256;
			}
			else if(this.algorithm.contains("384")) {
				this.digestAlgorithm = HttpConstants.DIGEST_ALGO_SHA_384;
			}
			else if(this.algorithm.contains("512")) {
				this.digestAlgorithm = HttpConstants.DIGEST_ALGO_SHA_512;
			}
			else {
				throw new ProtocolException("Digest algorithm compatible with signature '"+this.algorithm+"' not found");
			}
		}
		
		String x509 = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_RIFERIMENTO_X509);
		if(!request) {
			String profiloSicurezzaMessaggioRifX509AsRequestItemValue =  ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModIConsoleCostanti.MODIPA_API_IMPL_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_AS_REQUEST_ID);
			if(ModIConsoleCostanti.MODIPA_API_IMPL_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_AS_REQUEST_VALUE_FALSE.equals(profiloSicurezzaMessaggioRifX509AsRequestItemValue)){
				x509 = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509);
			}
		}
		List<String> vX509 = ProtocolPropertiesUtils.getListFromMultiSelectValue(x509);
		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5U)) {
			this.x5u = true;
			if(fruizione && request) {
				this.x5url = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_X509_VALUE_X5URL);
			}
			else if(!fruizione && !request) {
				this.x5url = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_X509_VALUE_X5URL);
			}
		}
		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5C)) {
			this.x5c = true;
		}
		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5T)) {
			this.x5t = true;
		}
		
	}
	
	private void initSharedSoap(List<ProtocolProperty> listProtocolProperties, boolean fruizione, boolean request) throws ProtocolException {
		
		if(fruizione) {
			if(request) {
				this.algorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_ALG);
				this.c14nAlgorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_CANONICALIZATION_ALG);
				this.keyIdentifierMode = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509);
				try {
					this.useSingleCertificate = !ProtocolPropertiesUtils.getBooleanValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_USE_CERTIFICATE_CHAIN, true);
				}catch(ProtocolException pNotFound) {
					// lascio il default true 
				}
				try {
					this.includeSignatureToken = ProtocolPropertiesUtils.getBooleanValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_INCLUDE_SIGNATURE_TOKEN, true);
				}catch(ProtocolException pNotFound) {
					// lascio il default false 
				}
			}
		}
		else {
			if(!request) {
				this.algorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_ALG);
				this.c14nAlgorithm = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_CANONICALIZATION_ALG);
				this.keyIdentifierMode = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509);
				try{
					this.useSingleCertificate = !ProtocolPropertiesUtils.getBooleanValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_USE_CERTIFICATE_CHAIN, true);
				}catch(ProtocolException pNotFound) {
					// lascio il default true 
				}
				try{
					this.includeSignatureToken = ProtocolPropertiesUtils.getBooleanValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_INCLUDE_SIGNATURE_TOKEN, true);
				}catch(ProtocolException pNotFound) {
					// lascio il default false 
				}
			}
		}
		
		if(this.algorithm!=null) {
			if(this.algorithm.contains("224")) {
				this.digestAlgorithm = SignatureDigestAlgorithm.SHA224.getUri();
			}
			else if(this.algorithm.contains("256")) {
				this.digestAlgorithm = SignatureDigestAlgorithm.SHA256.getUri();
			}
			else if(this.algorithm.contains("384")) {
				this.digestAlgorithm = SignatureDigestAlgorithm.SHA384.getUri();
			}
			else if(this.algorithm.contains("512")) {
				this.digestAlgorithm = SignatureDigestAlgorithm.SHA512.getUri();
			}
			else {
				throw new ProtocolException("Digest algorithm compatible with signature '"+this.algorithm+"' not found");
			}
		}
		
//		String x509 = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_RIFERIMENTO_X509);
//		if(!request) {
//			String profiloSicurezzaMessaggioRifX509AsRequestItemValue =  ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModIConsoleCostanti.MODIPA_API_IMPL_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_AS_REQUEST_ID);
//			if(ModIConsoleCostanti.MODIPA_API_IMPL_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_AS_REQUEST_VALUE_FALSE.equals(profiloSicurezzaMessaggioRifX509AsRequestItemValue)){
//				x509 = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509);
//			}
//		}
//		List<String> vX509 = ProtocolPropertiesUtils.getListFromMultiSelectValue(x509);
//		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5U)) {
//			this.x5u = true;
//			if(fruizione && request) {
//				this.x5url = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_X509_VALUE_X5URL);
//			}
//			else if(!fruizione && !request) {
//				this.x5url = ProtocolPropertiesUtils.getRequiredStringValuePropertyRegistry(listProtocolProperties, ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_X509_VALUE_X5URL);
//			}
//		}
//		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5C)) {
//			this.x5c = true;
//		}
//		if(vX509.contains(ModICostanti.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5T)) {
//			this.x5t = true;
//		}
		
	}
	

	public String getAlgorithm() {
		return this.algorithm;
	}
	
	public String getDigestAlgorithm() {
		return this.digestAlgorithm;
	}
	
	public String getC14nAlgorithm() {
		return this.c14nAlgorithm;
	}

	public boolean isX5c() {
		return this.x5c;
	}

	public boolean isX5t() {
		return this.x5t;
	}

	public boolean isX5u() {
		return this.x5u;
	}
	public String getX5url() {
		return this.x5url;
	}

	public String getKeyIdentifierMode() {
		return this.keyIdentifierMode;
	}

	public boolean isUseSingleCertificate() {
		return this.useSingleCertificate;
	}
	
	public boolean isIncludeSignatureToken() {
		return this.includeSignatureToken;
	}
	
	public int getTtlSeconds() {
		return this.ttl;
	}

	public String getAudience() {
		return this.audience;
	}
	
	public String getClientId() {
		return this.clientId;
	}
	
	public String getIssuer() {
		return this.issuer;
	}

	public String getSubject() {
		return this.subject;
	}
	
	public List<String> getHttpHeaders() {
		return this.httpHeaders;
	}
}
