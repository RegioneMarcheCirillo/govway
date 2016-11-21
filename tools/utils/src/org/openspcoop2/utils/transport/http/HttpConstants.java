/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it). 
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

package org.openspcoop2.utils.transport.http;

import org.openspcoop2.utils.mime.MimeTypeConstants;

/**
 * HttpConstants
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 12237 $, $Date: 2016-10-04 11:41:45 +0200 (Tue, 04 Oct 2016) $
 */
public class HttpConstants {
	
	public static byte[] CR_LF_BREAK_LINE = { 13, 10 };
	public static String HTTP_HEADER_SEPARATOR = ": ";
	
	/** HTTP HEADERS */
	public final static String CONTENT_TYPE = "Content-Type";
	public final static String CONTENT_LENGTH = "Content-Length";
	public final static String CONTENT_LOCATION = "Content-Location";
	public final static String CONTENT_ID = "Content-ID";
	public final static String RETURN_CODE = "ReturnCode";
	
    /** ContentType */
    public final static String CONTENT_TYPE_NON_VALORIZZATO = "Notv alued (null)";
    public final static String CONTENT_TYPE_NON_PRESENTE = "Undefined";
	
	/** Transfer Encoding */
	public final static String TRANSFER_ENCODING = "Transfer-Encoding";
	public final static String TRANSFER_ENCODING_VALUE_CHUNCKED = "chunked";
	
	/** Content Transfer Encoding */
	public final static String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
	public final static String CONTENT_TRANSFER_ENCODING_VALUE_BINARY = "binary";
	
	/** Redirect */
	public final static String REDIRECT_LOCATION = "Location";
	
	/** Authorization */
	public final static String AUTHORIZATION = "Authorization";
	public final static String AUTHORIZATION_PREFIX_BASIC = "Basic ";
	
	/** Content Type */
	public final static String CONTENT_TYPE_SOAP_1_1 = MimeTypeConstants.MEDIA_TYPE_SOAP_1_1;
	public final static String CONTENT_TYPE_SOAP_1_2 = MimeTypeConstants.MEDIA_TYPE_SOAP_1_2;
	public final static String CONTENT_TYPE_APPLICATION_OCTET_STREAM = MimeTypeConstants.MEDIA_TYPE_APPLICATION_OCTET_STREAM;
	public final static String CONTENT_TYPE_ZIP = MimeTypeConstants.MEDIA_TYPE_ZIP;
	public final static String CONTENT_TYPE_APPLICATION_XOP_XML = MimeTypeConstants.MEDIA_TYPE_APPLICATION_XOP_XML;
	public final static String CONTENT_TYPE_HTML = MimeTypeConstants.MEDIA_TYPE_HTML;
	public final static String CONTENT_TYPE_PLAIN = MimeTypeConstants.MEDIA_TYPE_PLAIN;
	public final static String CONTENT_TYPE_XML = MimeTypeConstants.MEDIA_TYPE_XML;
	public final static String CONTENT_TYPE_TEXT_XML = CONTENT_TYPE_SOAP_1_1;
	public final static String CONTENT_TYPE_JSON = MimeTypeConstants.MEDIA_TYPE_JSON;
	public final static String CONTENT_TYPE_OPENSPCOOP2_TUNNEL_SOAP = MimeTypeConstants.MEDIA_TYPE_OPENSPCOOP2_TUNNEL_SOAP;
	
	/** Content Type, Parameter */
	public final static String CONTENT_TYPE_PARAMETER_CHARSET = "charset";
	
	/** Multipart Content Type */
	public final static String CONTENT_TYPE_MULTIPART = "multipart/related";
	public final static String CONTENT_TYPE_MULTIPART_PARAMETER_BOUNDARY = "boundary";
	public final static String CONTENT_TYPE_MULTIPART_PARAMETER_TYPE = "type";
	public final static String CONTENT_TYPE_MULTIPART_PARAMETER_START = "start";
	public final static String CONTENT_TYPE_MULTIPART_PARAMETER_START_INFO = "start-info";
	
	/** Source */
    public final static String SEPARATOR_SOURCE = ":";
	
}
