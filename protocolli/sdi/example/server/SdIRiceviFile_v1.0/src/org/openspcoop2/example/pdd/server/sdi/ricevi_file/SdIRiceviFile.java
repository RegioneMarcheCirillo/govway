/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it).
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
package org.openspcoop2.example.pdd.server.sdi.ricevi_file;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-10-10T12:05:51.524+02:00
 * Generated source version: 2.7.4
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@WebService(targetNamespace = "http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0", name = "SdIRiceviFile")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SdIRiceviFile {

    @WebResult(name = "rispostaSdIRiceviFile", targetNamespace = "http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0/types", partName = "parametersOut")
    @WebMethod(operationName = "RiceviFile", action = "http://www.fatturapa.it/SdIRiceviFile/RiceviFile")
    public RispostaSdIRiceviFileType riceviFile(
        @WebParam(partName = "parametersIn", name = "fileSdIAccoglienza", targetNamespace = "http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0/types")
        FileSdIBaseType parametersIn
    );
}
