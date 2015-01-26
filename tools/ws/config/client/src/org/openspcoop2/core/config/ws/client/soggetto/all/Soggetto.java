/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it).
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
package org.openspcoop2.core.config.ws.client.soggetto.all;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-01-26T16:30:57.537+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/config/management", name = "Soggetto")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.config.ObjectFactory.class})
public interface Soggetto {

    @RequestWrapper(localName = "deleteById", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteById")
    @WebMethod(action = "deleteById")
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "idSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto idSoggetto
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Find")
    @WebMethod(action = "find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.FindResponse")
    public org.openspcoop2.core.config.Soggetto find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto filter
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "updateOrCreate", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.UpdateOrCreate")
    @WebMethod(action = "updateOrCreate")
    @ResponseWrapper(localName = "updateOrCreateResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.UpdateOrCreateResponse")
    public void updateOrCreate(
        @WebParam(name = "oldIdSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto oldIdSoggetto,
        @WebParam(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.Soggetto soggetto
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Get")
    @WebMethod(action = "get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.GetResponse")
    public org.openspcoop2.core.config.Soggetto get(
        @WebParam(name = "idSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto idSoggetto
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Delete")
    @WebMethod(action = "delete")
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteResponse")
    public void delete(
        @WebParam(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.Soggetto soggetto
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "idSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.FindAllIds")
    @WebMethod(action = "findAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.FindAllIdsResponse")
    public java.util.List<org.openspcoop2.core.config.IdSoggetto> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "deleteAll", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteAll")
    @WebMethod(action = "deleteAll")
    @ResponseWrapper(localName = "deleteAllResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteAllResponse")
    public long deleteAll() throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "create", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Create")
    @WebMethod(action = "create")
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.CreateResponse")
    public void create(
        @WebParam(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.Soggetto soggetto
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.FindAll")
    @WebMethod(action = "findAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.FindAllResponse")
    public java.util.List<org.openspcoop2.core.config.Soggetto> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @RequestWrapper(localName = "update", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Update")
    @WebMethod(action = "update")
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.UpdateResponse")
    public void update(
        @WebParam(name = "oldIdSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto oldIdSoggetto,
        @WebParam(name = "soggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.Soggetto soggetto
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "inUse", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "inUse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.InUse")
    @WebMethod(action = "inUse")
    @ResponseWrapper(localName = "inUseResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.InUseResponse")
    public org.openspcoop2.core.config.ws.client.soggetto.all.UseInfo inUse(
        @WebParam(name = "idSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto idSoggetto
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Exists")
    @WebMethod(action = "exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.ExistsResponse")
    public boolean exists(
        @WebParam(name = "idSoggetto", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdSoggetto idSoggetto
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.Count")
    @WebMethod(action = "count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.CountResponse")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "deleteAllByFilter", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteAllByFilter")
    @WebMethod(action = "deleteAllByFilter")
    @ResponseWrapper(localName = "deleteAllByFilterResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.soggetto.all.DeleteAllByFilterResponse")
    public long deleteAllByFilter(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;
}
