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

package org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.crud;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-11-28T12:44:13.991+01:00
 * Generated source version: 2.7.4
 * 
 */
public final class AccordoServizioParteComune_AccordoServizioParteComunePortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComuneSoap11Service");

    private AccordoServizioParteComune_AccordoServizioParteComunePortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = AccordoServizioParteComuneSoap11Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        AccordoServizioParteComuneSoap11Service ss = new AccordoServizioParteComuneSoap11Service(wsdlURL, AccordoServizioParteComune_AccordoServizioParteComunePortSoap11_Client.SERVICE_NAME);
        AccordoServizioParteComune port = ss.getAccordoServizioParteComunePortSoap11();
	
		new org.openspcoop2.core.registry.ws.client.utils.RequestContextUtils("accordoServizioParteComune.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking delete...");
        org.openspcoop2.core.registry.AccordoServizioParteComune _delete_accordoServizioParteComune = new org.openspcoop2.core.registry.AccordoServizioParteComune();
        try {
            port.delete(_delete_accordoServizioParteComune);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteById...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _deleteById_idAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        try {
            port.deleteById(_deleteById_idAccordoServizioParteComune);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAll...");
        try {
            long _deleteAll__return = port.deleteAll();
            System.out.println("deleteAll.result=" + _deleteAll__return);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateOrCreate...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _updateOrCreate_oldIdAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        org.openspcoop2.core.registry.AccordoServizioParteComune _updateOrCreate_accordoServizioParteComune = new org.openspcoop2.core.registry.AccordoServizioParteComune();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdAccordoServizioParteComune, _updateOrCreate_accordoServizioParteComune);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAllByFilter...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.crud.SearchFilterAccordoServizioParteComune _deleteAllByFilter_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.crud.SearchFilterAccordoServizioParteComune();
        try {
            long _deleteAllByFilter__return = port.deleteAllByFilter(_deleteAllByFilter_filter);
            System.out.println("deleteAllByFilter.result=" + _deleteAllByFilter__return);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking create...");
        org.openspcoop2.core.registry.AccordoServizioParteComune _create_accordoServizioParteComune = new org.openspcoop2.core.registry.AccordoServizioParteComune();
        try {
            port.create(_create_accordoServizioParteComune);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking update...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _update_oldIdAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        org.openspcoop2.core.registry.AccordoServizioParteComune _update_accordoServizioParteComune = new org.openspcoop2.core.registry.AccordoServizioParteComune();
        try {
            port.update(_update_oldIdAccordoServizioParteComune, _update_accordoServizioParteComune);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
