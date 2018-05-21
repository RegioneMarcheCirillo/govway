/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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


package org.openspcoop2.security.message.xml;


import java.security.KeyStore;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2RestXmlMessage;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.security.SecurityException;
import org.openspcoop2.security.message.AbstractRESTMessageSecuritySender;
import org.openspcoop2.security.message.MessageSecurityContext;
import org.openspcoop2.security.message.constants.SecurityConstants;
import org.openspcoop2.security.message.utils.EncryptionBean;
import org.openspcoop2.security.message.utils.KeystoreUtils;
import org.openspcoop2.security.message.utils.SignatureBean;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.security.XmlEncrypt;
import org.openspcoop2.utils.security.XmlSignature;

/**
 * MessageSecuritySender_xml
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 13743 $, $Date: 2018-03-16 10:59:08 -0400 (Fri, 16 Mar 2018) $
 */

public class MessageSecuritySender_xml extends AbstractRESTMessageSecuritySender{

	
     @Override
	public void process(MessageSecurityContext messageSecurityContext,OpenSPCoop2Message messageParam) throws SecurityException{
		try{ 	
			
			if(ServiceBinding.REST.equals(messageParam.getServiceBinding())==false){
				throw new SecurityException(XMLCostanti.XML_ENGINE_DESCRIPTION+" usable only with REST Binding");
			}
			if(MessageType.XML.equals(messageParam.getMessageType())==false) {
				throw new SecurityException(XMLCostanti.XML_ENGINE_DESCRIPTION+" usable only with REST Binding and a xml message, found: "+messageParam.getMessageType());
			}
			OpenSPCoop2RestXmlMessage restXmlMessage = messageParam.castAsRestXml();
			
			
			
			// ********** Leggo operazioni ***************
			boolean encrypt = false;
			boolean signature = false;

			String[]actions = ((String)messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ACTION)).split(" ");
			for (int i = 0; i < actions.length; i++) {
				if(SecurityConstants.ENCRYPT_ACTION.equals(actions[i].trim())){
					encrypt = true;
				}
				else if(SecurityConstants.SIGNATURE_ACTION.equals(actions[i].trim())){
					signature = true;
				}
				else {
					throw new SecurityException(XMLCostanti.XML_ENGINE_DESCRIPTION+"; action '"+actions[i]+"' unsupported");
				}
			}
			
			if(encrypt && signature) {
				throw new SecurityException(XMLCostanti.XML_ENGINE_DESCRIPTION+" usable only with one function beetwen encrypt or signature");
			}
			if(!encrypt && !signature) {
				throw new SecurityException(XMLCostanti.XML_ENGINE_DESCRIPTION+" require one function beetwen encrypt or signature");
			}
			

			
			
			if(signature) {
				
				
				// **************** Leggo parametri signature store **************************

				SignatureBean bean = null;
				try {
					bean = KeystoreUtils.getSenderSignatureBean(messageSecurityContext);
				}catch(Exception e) {
					throw e;
				}
				
				KeyStore signatureKS = bean.getKeystore();
				String aliasSignatureUser = bean.getUser();
				String aliasSignaturePassword = bean.getPassword();

				if(signatureKS==null) {
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require keystore");
				}
				if(aliasSignatureUser==null) {
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require alias private key");
				}
				if(aliasSignaturePassword==null) {
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require password private key");
				}
								
				String signatureAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.SIGNATURE_ALGORITHM);
				if(signatureAlgorithm==null || "".equals(signatureAlgorithm.trim())){
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require '"+SecurityConstants.SIGNATURE_ALGORITHM+"' property");
				}
				
				String digestMethodAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.SIGNATURE_DIGEST_ALGORITHM);
				if(digestMethodAlgorithm==null || "".equals(digestMethodAlgorithm.trim())){
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require '"+SecurityConstants.SIGNATURE_DIGEST_ALGORITHM+"' property");
				}
				
				String signatureCanonicalizationMethod = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.SIGNATURE_C14N_ALGORITHM);
				if(signatureCanonicalizationMethod==null || "".equals(signatureCanonicalizationMethod.trim())){
					throw new SecurityException(XMLCostanti.XML_ENGINE_SIGNATURE_DESCRIPTION+" require '"+SecurityConstants.SIGNATURE_C14N_ALGORITHM+"' property");
				}
				
				XmlSignature xmlSignature = new XmlSignature(signatureKS, aliasSignatureUser, aliasSignaturePassword);
				
				String signatureKeyInfo = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.SIGNATURE_XML_KEY_INFO);
				String signatureKeyInfoAlias = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.SIGNATURE_XML_KEY_INFO_ALIAS);
				
				
				
				

				
				
				
				
				// **************** Process **************************
				
				if(signatureKeyInfo!=null && !"".equals(signatureKeyInfo.trim())){
					
					if(SecurityConstants.SIGNATURE_XML_KEY_INFO_X509.equals(signatureKeyInfo)){
						if(signatureKeyInfoAlias!=null && !"".equals(signatureKeyInfoAlias.trim())){
							xmlSignature.addX509KeyInfo(signatureKeyInfoAlias);
						}
						else {
							xmlSignature.addX509KeyInfo();
						}
					}
					else if(SecurityConstants.SIGNATURE_XML_KEY_INFO_RSA.equals(signatureKeyInfo)){
						if(signatureKeyInfoAlias!=null && !"".equals(signatureKeyInfoAlias.trim())){
							xmlSignature.addRSAKeyInfo(signatureKeyInfoAlias);
						}
						else {
							xmlSignature.addRSAKeyInfo();
						}
					}
					else{
						throw new Exception(SecurityConstants.SIGNATURE_XML_KEY_INFO+" not supported ["+signatureKeyInfo+"]");
					}
					
				}
				
				xmlSignature.sign(restXmlMessage.getContent(), signatureAlgorithm, digestMethodAlgorithm, signatureCanonicalizationMethod);
				
				
				
			} // fine signature
			
			
			
			
			
			else if(encrypt){
			
				// **************** Leggo parametri encryption store **************************
				
				XmlEncrypt xmlEncrypt = null;
				EncryptionBean bean = null;
				try {
					bean = KeystoreUtils.getSenderEncryptionBean(messageSecurityContext);
				}catch(Exception e) {
					throw e;
				}
				
				KeyStore encryptionKS = bean.getKeystore();
				KeyStore encryptionTrustStoreKS = bean.getTruststore();
				boolean encryptionSymmetric = bean.isEncryptionSimmetric();
				String aliasEncryptUser = bean.getUser();
				String aliasEncryptPassword = bean.getPassword();

				if(encryptionSymmetric) {
					if(encryptionKS==null) {
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require keystore");
					}
					if(aliasEncryptUser==null) {
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require alias secret key");
					}
					if(aliasEncryptPassword==null) {
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require password secret key");
					}
				}
				else {
					if(encryptionTrustStoreKS==null) {
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require truststore");
					}
					if(aliasEncryptUser==null) {
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require alias public key");
					}
				}
				
				String encryptionAlgorithm = null;
				String encryptionKeyAlgorithm = null;
				String wrappedKeyAlgorithm = null;
				if(encryptionSymmetric) {	
					
					encryptionAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_SYMMETRIC_ALGORITHM);
					if(encryptionAlgorithm==null || "".equals(encryptionAlgorithm.trim())){
						encryptionAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_ALGORITHM);
						if(encryptionAlgorithm==null || "".equals(encryptionAlgorithm.trim())){
							throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require '"+SecurityConstants.ENCRYPTION_SYMMETRIC_ALGORITHM+"'/'"+
									SecurityConstants.ENCRYPTION_ALGORITHM+"' property");
						}
					}
					
					xmlEncrypt = new XmlEncrypt(encryptionKS, true, aliasEncryptUser, aliasEncryptPassword);
					
				}
				else {
					
					encryptionAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_ALGORITHM);
					if(encryptionAlgorithm==null || "".equals(encryptionAlgorithm.trim())){
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require '"+SecurityConstants.ENCRYPTION_ALGORITHM+"' property");
					}
					
					encryptionKeyAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_KEY_ALGORITHM);
					if(encryptionKeyAlgorithm==null || "".equals(encryptionKeyAlgorithm.trim())){
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require '"+SecurityConstants.ENCRYPTION_KEY_ALGORITHM+"' property");
					}
					
					wrappedKeyAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_KEY_TRANSPORT_ALGORITHM);
					if(wrappedKeyAlgorithm==null || "".equals(wrappedKeyAlgorithm.trim())){
						throw new SecurityException(XMLCostanti.XML_ENGINE_ENCRYPT_DESCRIPTION+" require '"+SecurityConstants.ENCRYPTION_KEY_TRANSPORT_ALGORITHM+"' property");
					}
					
					xmlEncrypt = new XmlEncrypt(encryptionTrustStoreKS, aliasEncryptUser);
				}
				
				String encryptionDigestAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_DIGEST_ALGORITHM);
				if(encryptionDigestAlgorithm!=null && "".equals(encryptionDigestAlgorithm.trim())){
					encryptionDigestAlgorithm = null; // normalizzo
				}
				
				String encryptionCanonicalizationMethodAlgorithm = (String) messageSecurityContext.getOutgoingProperties().get(SecurityConstants.ENCRYPTION_C14N_ALGORITHM);
				if(encryptionCanonicalizationMethodAlgorithm!=null && "".equals(encryptionCanonicalizationMethodAlgorithm.trim())){
					encryptionCanonicalizationMethodAlgorithm = null; // normalizzo
				}
				
				
				
				// **************** Process **************************
				
				if(encryptionSymmetric) {	
					xmlEncrypt.encryptSymmetric(restXmlMessage.getContent(), encryptionAlgorithm, encryptionCanonicalizationMethodAlgorithm, encryptionDigestAlgorithm);
				}
				else {
					xmlEncrypt.encrypt(restXmlMessage.getContent(), encryptionAlgorithm, encryptionCanonicalizationMethodAlgorithm, encryptionDigestAlgorithm,
							encryptionKeyAlgorithm, wrappedKeyAlgorithm);
				}
				

			} // fine encrypt


		}
		catch(Exception e){
			
			String msg = Utilities.getInnerNotEmptyMessageException(e).getMessage();
			
			Throwable innerExc = Utilities.getLastInnerException(e);
			String innerMsg = null;
			if(innerExc!=null){
				innerMsg = innerExc.getMessage();
			}
			
			String messaggio = null;
			if(msg!=null){
				messaggio = new String(msg);
				if(innerMsg!=null && !innerMsg.equals(msg)){
					messaggio = messaggio + " ; " + innerMsg;
				}
			}
			else{
				if(innerMsg!=null){
					messaggio = innerMsg;
				}
			}
			
			SecurityException wssException = new SecurityException(e.getMessage(), e);
			wssException.setMsgErrore(messaggio);
			throw wssException;
		}
		
    }

 
}




