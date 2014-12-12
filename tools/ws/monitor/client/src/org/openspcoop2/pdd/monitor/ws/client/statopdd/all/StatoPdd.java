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
package org.openspcoop2.pdd.monitor.ws.client.statopdd.all;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-12-01T13:15:49.936+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", name = "StatoPdd")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.pdd.monitor.ObjectFactory.class})
public interface StatoPdd {

    @WebResult(name = "statoPdd", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.statopdd.all.Find")
    @WebMethod(action = "find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management", className = "org.openspcoop2.pdd.monitor.ws.client.statopdd.all.FindResponse")
    public org.openspcoop2.pdd.monitor.StatoPdd find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
        org.openspcoop2.pdd.monitor.ws.client.statopdd.all.SearchFilterStatoPdd filter
    ) throws MonitorNotFoundException_Exception, MonitorMultipleResultException_Exception, MonitorServiceException_Exception, MonitorNotImplementedException_Exception, MonitorNotAuthorizedException_Exception;
}
