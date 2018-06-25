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
package org.openspcoop2.core.transazioni.ws.client.dumpmessaggio.search;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-23T14:34:52.493+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "DumpMessaggioSoap12Service", 
                  wsdlLocation = "file:deploy/wsdl/DumpMessaggioSearch_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/transazioni/management") 
public class DumpMessaggioSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/transazioni/management", "DumpMessaggioSoap12Service");
    public final static QName DumpMessaggioPortSoap12 = new QName("http://www.openspcoop2.org/core/transazioni/management", "DumpMessaggioPortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/DumpMessaggioSearch_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(DumpMessaggioSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/DumpMessaggioSearch_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public DumpMessaggioSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, DumpMessaggioSoap12Service.SERVICE);
    }

    public DumpMessaggioSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DumpMessaggioSoap12Service() {
        super(DumpMessaggioSoap12Service.WSDL_LOCATION, DumpMessaggioSoap12Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns DumpMessaggio
     */
    @WebEndpoint(name = "DumpMessaggioPortSoap12")
    public DumpMessaggio getDumpMessaggioPortSoap12() {
        return super.getPort(DumpMessaggioSoap12Service.DumpMessaggioPortSoap12, DumpMessaggio.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns DumpMessaggio
     */
    @WebEndpoint(name = "DumpMessaggioPortSoap12")
    public DumpMessaggio getDumpMessaggioPortSoap12(WebServiceFeature... features) {
        return super.getPort(DumpMessaggioSoap12Service.DumpMessaggioPortSoap12, DumpMessaggio.class, features);
    }

}
