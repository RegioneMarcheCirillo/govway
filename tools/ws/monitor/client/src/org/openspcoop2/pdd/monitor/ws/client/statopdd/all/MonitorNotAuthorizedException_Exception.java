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

package org.openspcoop2.pdd.monitor.ws.client.statopdd.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-12-01T13:15:49.900+01:00
 * Generated source version: 2.7.4
 */

@WebFault(name = "monitor-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management")
public class MonitorNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.pdd.monitor.ws.client.statopdd.all.MonitorNotAuthorizedException monitorNotAuthorizedException;

    public MonitorNotAuthorizedException_Exception() {
        super();
    }
    
    public MonitorNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public MonitorNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public MonitorNotAuthorizedException_Exception(String message, org.openspcoop2.pdd.monitor.ws.client.statopdd.all.MonitorNotAuthorizedException monitorNotAuthorizedException) {
        super(message);
        this.monitorNotAuthorizedException = monitorNotAuthorizedException;
    }

    public MonitorNotAuthorizedException_Exception(String message, org.openspcoop2.pdd.monitor.ws.client.statopdd.all.MonitorNotAuthorizedException monitorNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.monitorNotAuthorizedException = monitorNotAuthorizedException;
    }

    public org.openspcoop2.pdd.monitor.ws.client.statopdd.all.MonitorNotAuthorizedException getFaultInfo() {
        return this.monitorNotAuthorizedException;
    }
}
