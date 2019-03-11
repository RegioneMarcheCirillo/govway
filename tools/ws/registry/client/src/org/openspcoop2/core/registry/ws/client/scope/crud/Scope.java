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
package org.openspcoop2.core.registry.ws.client.scope.crud;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.6
 * 2019-03-05T14:12:14.796+01:00
 * Generated source version: 3.2.6
 *
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/registry/management", name = "Scope")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.registry.ObjectFactory.class})
public interface Scope {

    @WebMethod(action = "deleteAllByFilter")
    @RequestWrapper(localName = "deleteAllByFilter", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteAllByFilter")
    @ResponseWrapper(localName = "deleteAllByFilterResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteAllByFilterResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public long deleteAllByFilter(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.scope.crud.SearchFilterScope filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "deleteById")
    @RequestWrapper(localName = "deleteById", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteById")
    @ResponseWrapper(localName = "deleteByIdResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteByIdResponse")
    public void deleteById(
        @WebParam(name = "idScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope idScope
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "deleteAll")
    @RequestWrapper(localName = "deleteAll", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteAll")
    @ResponseWrapper(localName = "deleteAllResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteAllResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public long deleteAll() throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "create")
    @RequestWrapper(localName = "create", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.Create")
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.CreateResponse")
    public void create(
        @WebParam(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.Scope scope
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "update")
    @RequestWrapper(localName = "update", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.Update")
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.UpdateResponse")
    public void update(
        @WebParam(name = "oldIdScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope oldIdScope,
        @WebParam(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.Scope scope
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "updateOrCreate")
    @RequestWrapper(localName = "updateOrCreate", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.UpdateOrCreate")
    @ResponseWrapper(localName = "updateOrCreateResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.UpdateOrCreateResponse")
    public void updateOrCreate(
        @WebParam(name = "oldIdScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope oldIdScope,
        @WebParam(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.Scope scope
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "delete")
    @RequestWrapper(localName = "delete", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.Delete")
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.crud.DeleteResponse")
    public void delete(
        @WebParam(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.Scope scope
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;
}
