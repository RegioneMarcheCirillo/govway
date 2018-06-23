/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
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

package org.openspcoop2.security.message.constants;

/**     
 * SignatureAlgorithm
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public enum SignatureAlgorithm {

	DSA_SHA1("http://www.w3.org/2000/09/xmldsig#dsa-sha1"),
	DSA_SHA256("http://www.w3.org/2009/xmldsig11#dsa-sha256"),
	RSA_SHA1("http://www.w3.org/2000/09/xmldsig#rsa-sha1"),
	RSA_SHA224("http://www.w3.org/2001/04/xmldsig-more#rsa-sha224"),
	RSA_SHA256("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"),
	RSA_SHA384("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"),
	RSA_SH512("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512"),
	ECDSA_SHA1("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1"),
	ECDSA_SHA224("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224"),
	ECDSA_SHA256("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256"),
	ECDSA_SHA384("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384"),
	ECDSA_SHA1512("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512"),
	HMAC_SHA1("http://www.w3.org/2000/09/xmldsig#hmac-sha1"),
	HMAC_SHA224("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224"),
	HMAC_SHA256("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256"),
	HMAC_SHA384("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384"),
	HMAC_SHA512("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512");
	
	
	private String uri;
	SignatureAlgorithm(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return this.uri;
	}
}
