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
package org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.search;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-05-30T09:46:28.242+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "AccordoServizioParteComuneSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/AccordoServizioParteComuneSearch_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/registry/management") 
public class AccordoServizioParteComuneSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComuneSoap11Service");
    public final static QName AccordoServizioParteComunePortSoap11 = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComunePortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/AccordoServizioParteComuneSearch_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AccordoServizioParteComuneSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/AccordoServizioParteComuneSearch_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AccordoServizioParteComuneSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, AccordoServizioParteComuneSoap11Service.SERVICE);
    }

    public AccordoServizioParteComuneSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccordoServizioParteComuneSoap11Service() {
        super(AccordoServizioParteComuneSoap11Service.WSDL_LOCATION, AccordoServizioParteComuneSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns AccordoServizioParteComune
     */
    @WebEndpoint(name = "AccordoServizioParteComunePortSoap11")
    public AccordoServizioParteComune getAccordoServizioParteComunePortSoap11() {
        return super.getPort(AccordoServizioParteComuneSoap11Service.AccordoServizioParteComunePortSoap11, AccordoServizioParteComune.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccordoServizioParteComune
     */
    @WebEndpoint(name = "AccordoServizioParteComunePortSoap11")
    public AccordoServizioParteComune getAccordoServizioParteComunePortSoap11(WebServiceFeature... features) {
        return super.getPort(AccordoServizioParteComuneSoap11Service.AccordoServizioParteComunePortSoap11, AccordoServizioParteComune.class, features);
    }

}
