/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved.
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
package org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-11-28T12:47:44.780+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/registry/management", name = "AccordoServizioParteSpecifica")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.registry.ObjectFactory.class})
public interface AccordoServizioParteSpecifica {

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "deleteAll", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteAll")
    @WebMethod(action = "deleteAll")
    @ResponseWrapper(localName = "deleteAllResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteAllResponse")
    public long deleteAll() throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.InUse")
    @WebMethod(action = "inUse")
    @ResponseWrapper(localName = "inUseResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.InUseResponse")
    public org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.UseInfo inUse(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryNotFoundException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Count")
    @WebMethod(action = "count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.CountResponse")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @RequestWrapper(localName = "deleteById", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteById")
    @WebMethod(action = "deleteById")
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.FindAll")
    @WebMethod(action = "findAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.FindAllResponse")
    public java.util.List<org.openspcoop2.core.registry.AccordoServizioParteSpecifica> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Exists")
    @WebMethod(action = "exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.ExistsResponse")
    public boolean exists(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception;

    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Get")
    @WebMethod(action = "get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.GetResponse")
    public org.openspcoop2.core.registry.AccordoServizioParteSpecifica get(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryNotFoundException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception;

    @RequestWrapper(localName = "delete", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Delete")
    @WebMethod(action = "delete")
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteResponse")
    public void delete(
        @WebParam(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica accordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @RequestWrapper(localName = "updateOrCreate", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.UpdateOrCreate")
    @WebMethod(action = "updateOrCreate")
    @ResponseWrapper(localName = "updateOrCreateResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.UpdateOrCreateResponse")
    public void updateOrCreate(
        @WebParam(name = "oldIdAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica oldIdAccordoServizioParteSpecifica,
        @WebParam(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica accordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "deleteAllByFilter", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteAllByFilter")
    @WebMethod(action = "deleteAllByFilter")
    @ResponseWrapper(localName = "deleteAllByFilterResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.DeleteAllByFilterResponse")
    public long deleteAllByFilter(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.FindAllIds")
    @WebMethod(action = "findAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.FindAllIdsResponse")
    public java.util.List<org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Find")
    @WebMethod(action = "find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.FindResponse")
    public org.openspcoop2.core.registry.AccordoServizioParteSpecifica find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryNotAuthorizedException_Exception, RegistryNotFoundException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception;

    @RequestWrapper(localName = "create", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Create")
    @WebMethod(action = "create")
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.CreateResponse")
    public void create(
        @WebParam(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica accordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;

    @RequestWrapper(localName = "update", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.Update")
    @WebMethod(action = "update")
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.all.UpdateResponse")
    public void update(
        @WebParam(name = "oldIdAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica oldIdAccordoServizioParteSpecifica,
        @WebParam(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica accordoServizioParteSpecifica
    ) throws RegistryNotAuthorizedException_Exception, RegistryNotFoundException_Exception, RegistryServiceException_Exception, RegistryNotImplementedException_Exception;
}
