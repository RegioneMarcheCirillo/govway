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

package org.openspcoop2.protocol.as4.pmode;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openspcoop2.protocol.as4.config.AS4Properties;
import org.openspcoop2.protocol.sdk.ProtocolException;
import org.openspcoop2.utils.resources.TemplateUtils;

import eu.domibus.configuration.Payload;
import eu.domibus.configuration.PayloadProfile;
import freemarker.template.Template;

/**
 * @author Bussu Giovanni (bussu@link.it)
 * @author  $Author: apoli $
 * @version $Rev: 13732 $, $Date: 2018-03-14 18:03:37 +0100 (Wed, 14 Mar 2018) $
 * 
 */
public class TranslatorPayloadProfilesDefault {

	private static TranslatorPayloadProfilesDefault translatorInstance;
	private static synchronized void initTranslator() throws ProtocolException {
		if(translatorInstance==null) {
			translatorInstance = new TranslatorPayloadProfilesDefault();
		}
	}
	public static TranslatorPayloadProfilesDefault getTranslator() throws ProtocolException {
		if(translatorInstance==null) {
			initTranslator();
		}
		return translatorInstance;
	}
	
	
	
	private byte[] payloadDefault = null;
	private Template templatePayloadDefault;
	
	private byte[] payloadProfileDefault = null;
	private Template templatePayloadProfileDefault;
	
	private TranslatorPayloadProfilesDefault() throws ProtocolException {
		try {
			AS4Properties props = AS4Properties.getInstance();
			
			this.payloadDefault = props.getPayloadProfilesDefaultPayloads();
			if(this.payloadDefault==null) {
				this.templatePayloadDefault = TemplateUtils.getTemplate("/org/openspcoop2/protocol/as4/pmode", "pmode-payloadDefault.ftl");	
			}
			
			this.payloadProfileDefault = props.getPayloadProfilesDefaultPayloadProfiles();
			if(this.payloadProfileDefault==null) {
				this.templatePayloadProfileDefault = TemplateUtils.getTemplate("/org/openspcoop2/protocol/as4/pmode", "pmode-payloadProfileDefault.ftl");
			}
			
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}

	// PAYLOAD DEFAULT
	
	public String getPayloadDefault() throws ProtocolException {
		try {
			if(this.payloadDefault==null) {
				Map<String, Object> map = new HashMap<String, Object>();
				StringWriter writer = new StringWriter();
				this.templatePayloadDefault.process(map, writer);
				return writer.toString();
			}
			else {
				return new String(this.payloadDefault);
			}
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}
	public List<Payload> getListPayloadDefault() throws ProtocolException {
		try {
			
			BufferedReader br = new BufferedReader(new StringReader(this.getPayloadDefault()));
			List<Payload> list = new ArrayList<Payload>();
			String line;
			StringBuffer bf = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if(bf.length()>0) {
					bf.append("\n");
				}
				bf.append(line);
				if(line.contains("/>") || line.contains("</payload>")) {
					String finalString = bf.toString();
					if(finalString!=null && !"".equals(finalString)) {
						String sWithNamespace = finalString.replace("<payload>", 
								"<payload xmlns=\""+eu.domibus.configuration.utils.ProjectInfo.getInstance().getProjectNamespace()+"\">");
						sWithNamespace = sWithNamespace.replace("<payload ", 
								"<payload xmlns=\""+eu.domibus.configuration.utils.ProjectInfo.getInstance().getProjectNamespace()+"\" ");
						eu.domibus.configuration.utils.serializer.JibxDeserializer deserializer = new eu.domibus.configuration.utils.serializer.JibxDeserializer();
						Payload p = deserializer.readPayload(sWithNamespace.getBytes());
						list.add(p);
					}
					bf = new StringBuffer();
				}
			}
			return list;
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}
	
	
	// PAYLOAD PROFILE DEFAULT
	
	public String getPayloadProfileDefault() throws ProtocolException {
		try {
			if(this.payloadProfileDefault==null) {
				Map<String, Object> map = new HashMap<String, Object>();
				StringWriter writer = new StringWriter();
				this.templatePayloadProfileDefault.process(map, writer);
				return writer.toString();
			}
			else {
				return new String(this.payloadProfileDefault);
			}
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}
	
	public List<PayloadProfile> getListPayloadProfileDefault() throws ProtocolException {
		try {
			
			BufferedReader br = new BufferedReader(new StringReader(this.getPayloadProfileDefault()));
			List<PayloadProfile> list = new ArrayList<PayloadProfile>();
			String line;
			StringBuffer bf = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if(bf.length()>0) {
					bf.append("\n");
				}
				bf.append(line);
				if(line.contains("</payloadProfile>")) {
					String finalString = bf.toString();
					if(finalString!=null && !"".equals(finalString)) {
						String sWithNamespace = 
								finalString.replace("<payloadProfile>", "<payloadProfile xmlns=\""+eu.domibus.configuration.utils.ProjectInfo.getInstance().getProjectNamespace()+"\">");
						sWithNamespace = sWithNamespace.replace("<payloadProfile ", 
								"<payloadProfile xmlns=\""+eu.domibus.configuration.utils.ProjectInfo.getInstance().getProjectNamespace()+"\" ");
						eu.domibus.configuration.utils.serializer.JibxDeserializer deserializer = new eu.domibus.configuration.utils.serializer.JibxDeserializer();
						PayloadProfile p = deserializer.readPayloadProfile(sWithNamespace.getBytes());
						list.add(p);
					}
					bf = new StringBuffer();
				}
			}
			return list;
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}

	
	// PAYLOAD PROFILES DEFAULT COMPLETE
	
	public String getAsStringPayloadProfilesDefaultAsCompleteXml() throws ProtocolException {
		try {
			StringBuffer bf = new StringBuffer();
			
			bf.append("\t<payloadProfiles>\n\n");
			
			bf.append(this.getPayloadDefault());
			
			bf.append("\n\n");
			
			bf.append(this.getPayloadProfileDefault());
			
			bf.append("\n\n");
			bf.append("\t</payloadProfiles>");
			
			return bf.toString();
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}
	
	public byte[] getPayloadProfilesDefaultAsCompleteXml() throws ProtocolException {
		try {
			return getAsStringPayloadProfilesDefaultAsCompleteXml().getBytes();
		}catch(Exception e) {
			throw new ProtocolException(e.getMessage(),e);
		}
	}
	
	

	
}
