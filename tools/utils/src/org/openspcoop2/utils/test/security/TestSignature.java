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

package org.openspcoop2.utils.test.security;

import org.openspcoop2.utils.test.Costanti;
import org.openspcoop2.utils.test.TestLogger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * TestSignature
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TestSignature {

	private static final String ID_TEST = "Signature";
	
	@DataProvider(name="signatureProvider")
	public Object[][] provider(){
		return new Object[][]{
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JAVA_SIGNATURE},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.XML_SIGNATURE},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JKS},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JKS_HEADER_CUSTOM},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_HEADER_CUSTOM},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JKS_HEADER_CUSTOM_KID_ONLY},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_HEADER_CUSTOM_KID_ONLY},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JKS_KEYSTORE},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JKS_KEYSTORE_HEADER_CUSTOM},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_KEYS},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_KEYS_HEADER_CUSTOM},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_KEY},
				{org.openspcoop2.utils.security.TestSignature.TipoTest.JSON_SIGNATURE_PROPERTIES_JWK_KEY_HEADER_CUSTOM}
		};
	}
	
	@Test(groups={Costanti.GRUPPO_UTILS,Costanti.GRUPPO_UTILS+"."+ID_TEST},dataProvider="signatureProvider")
	public void testSignature(org.openspcoop2.utils.security.TestSignature.TipoTest tipo) throws Exception{
		
		TestLogger.info("Run test '"+ID_TEST+"' (tipo: "+tipo+") ...");
		org.openspcoop2.utils.security.TestSignature.main(new String [] { tipo.name() } );
		TestLogger.info("Run test '"+ID_TEST+"' (tipo: "+tipo+") ok");
		
	}
	
}