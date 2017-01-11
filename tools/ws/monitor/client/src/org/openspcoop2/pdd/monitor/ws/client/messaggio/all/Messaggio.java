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
package org.openspcoop2.pdd.monitor.ws.client.messaggio.all;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-12-01T13:16:06.096+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", name = "Messaggio")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.pdd.monitor.ObjectFactory.class})
public interface Messaggio {

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.Count")
    @WebMethod(action = "count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.CountResponse")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio filter
    ) throws MonitorServiceException_Exception, MonitorNotImplementedException_Exception, MonitorNotAuthorizedException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
    @RequestWrapper(localName = "deleteAllByFilter", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.DeleteAllByFilter")
    @WebMethod(action = "deleteAllByFilter")
    @ResponseWrapper(localName = "deleteAllByFilterResponse", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.DeleteAllByFilterResponse")
    public long deleteAllByFilter(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio filter
    ) throws MonitorServiceException_Exception, MonitorNotImplementedException_Exception, MonitorNotAuthorizedException_Exception;

    @WebResult(name = "messaggio", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.FindAll")
    @WebMethod(action = "findAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.messaggio.all.FindAllResponse")
    public java.util.List<org.openspcoop2.pdd.monitor.Messaggio> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio filter
    ) throws MonitorServiceException_Exception, MonitorNotImplementedException_Exception, MonitorNotAuthorizedException_Exception;
}
