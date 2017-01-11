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
package org.openspcoop2.core.config.ws.client.portaapplicativa.crud;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-12-12T11:24:15.381+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/config/management", name = "PortaApplicativa")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.config.ObjectFactory.class})
public interface PortaApplicativa {

    @RequestWrapper(localName = "deleteById", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteById")
    @WebMethod(action = "deleteById")
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "idPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa idPortaApplicativa
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "deleteAll", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteAll")
    @WebMethod(action = "deleteAll")
    @ResponseWrapper(localName = "deleteAllResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteAllResponse")
    public long deleteAll() throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "create", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.Create")
    @WebMethod(action = "create")
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.CreateResponse")
    public void create(
        @WebParam(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.PortaApplicativa portaApplicativa
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "updateOrCreate", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.UpdateOrCreate")
    @WebMethod(action = "updateOrCreate")
    @ResponseWrapper(localName = "updateOrCreateResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.UpdateOrCreateResponse")
    public void updateOrCreate(
        @WebParam(name = "oldIdPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa oldIdPortaApplicativa,
        @WebParam(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.PortaApplicativa portaApplicativa
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.Delete")
    @WebMethod(action = "delete")
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteResponse")
    public void delete(
        @WebParam(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.PortaApplicativa portaApplicativa
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "update", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.Update")
    @WebMethod(action = "update")
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.UpdateResponse")
    public void update(
        @WebParam(name = "oldIdPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa oldIdPortaApplicativa,
        @WebParam(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.PortaApplicativa portaApplicativa
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "deleteAllByFilter", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteAllByFilter")
    @WebMethod(action = "deleteAllByFilter")
    @ResponseWrapper(localName = "deleteAllByFilterResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.crud.DeleteAllByFilterResponse")
    public long deleteAllByFilter(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.portaapplicativa.crud.SearchFilterPortaApplicativa filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;
}
