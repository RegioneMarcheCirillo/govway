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
package org.openspcoop2.core.registry.ws.client.portadominio.search;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.6
  * 2019-03-05T14:08:28.354+01:00
 * Generated source version: 3.2.6
 *
 */
@WebServiceClient(name = "PortaDominioSoap12Service",
                  wsdlLocation = "file:deploy/wsdl/PortaDominioSearch_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class PortaDominioSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/registry/management", "PortaDominioSoap12Service");
    public final static QName PortaDominioPortSoap12 = new QName("http://www.openspcoop2.org/core/registry/management", "PortaDominioPortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/PortaDominioSearch_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(PortaDominioSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/PortaDominioSearch_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public PortaDominioSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, PortaDominioSoap12Service.SERVICE);
    }

    public PortaDominioSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PortaDominioSoap12Service() {
        super(PortaDominioSoap12Service.WSDL_LOCATION, PortaDominioSoap12Service.SERVICE);
    }





    /**
     *
     * @return
     *     returns PortaDominio
     */
    @WebEndpoint(name = "PortaDominioPortSoap12")
    public PortaDominio getPortaDominioPortSoap12() {
        return super.getPort(PortaDominioSoap12Service.PortaDominioPortSoap12, PortaDominio.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PortaDominio
     */
    @WebEndpoint(name = "PortaDominioPortSoap12")
    public PortaDominio getPortaDominioPortSoap12(WebServiceFeature... features) {
        return super.getPort(PortaDominioSoap12Service.PortaDominioPortSoap12, PortaDominio.class, features);
    }

}
