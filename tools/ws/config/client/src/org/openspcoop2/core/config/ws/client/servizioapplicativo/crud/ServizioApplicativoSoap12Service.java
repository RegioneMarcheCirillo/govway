/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it).
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
package org.openspcoop2.core.config.ws.client.servizioapplicativo.crud;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2016-12-12T11:27:04.891+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "ServizioApplicativoSoap12Service", 
                  wsdlLocation = "file:deploy/wsdl/ServizioApplicativoCRUD_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/config/management") 
public class ServizioApplicativoSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/config/management", "ServizioApplicativoSoap12Service");
    public final static QName ServizioApplicativoPortSoap12 = new QName("http://www.openspcoop2.org/core/config/management", "ServizioApplicativoPortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/ServizioApplicativoCRUD_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ServizioApplicativoSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/ServizioApplicativoCRUD_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ServizioApplicativoSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, ServizioApplicativoSoap12Service.SERVICE);
    }

    public ServizioApplicativoSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServizioApplicativoSoap12Service() {
        super(ServizioApplicativoSoap12Service.WSDL_LOCATION, ServizioApplicativoSoap12Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns ServizioApplicativo
     */
    @WebEndpoint(name = "ServizioApplicativoPortSoap12")
    public ServizioApplicativo getServizioApplicativoPortSoap12() {
        return super.getPort(ServizioApplicativoSoap12Service.ServizioApplicativoPortSoap12, ServizioApplicativo.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServizioApplicativo
     */
    @WebEndpoint(name = "ServizioApplicativoPortSoap12")
    public ServizioApplicativo getServizioApplicativoPortSoap12(WebServiceFeature... features) {
        return super.getPort(ServizioApplicativoSoap12Service.ServizioApplicativoPortSoap12, ServizioApplicativo.class, features);
    }

}
