/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it).
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

package org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all;

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
 * 2015-01-26T16:23:29.161+01:00
 * Generated source version: 2.7.4
 * 
 */
public final class AccordoServizioParteComune_AccordoServizioParteComunePortSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComuneSoap12Service");

    private AccordoServizioParteComune_AccordoServizioParteComunePortSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = AccordoServizioParteComuneSoap12Service.WSDL_LOCATION;
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
      
        AccordoServizioParteComuneSoap12Service ss = new AccordoServizioParteComuneSoap12Service(wsdlURL, AccordoServizioParteComune_AccordoServizioParteComunePortSoap12_Client.SERVICE_NAME);
        AccordoServizioParteComune port = ss.getAccordoServizioParteComunePortSoap12();
	
		new org.openspcoop2.core.registry.ws.client.utils.RequestContextUtils("accordoServizioParteComune.soap12").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
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
        System.out.println("Invoking inUse...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _inUse_idAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        try {
            org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.UseInfo _inUse__return = port.inUse(_inUse_idAccordoServizioParteComune);
            System.out.println("inUse.result=" + _inUse__return);

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
        {
        System.out.println("Invoking count...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune _count_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune();
        try {
            long _count__return = port.count(_count_filter);
            System.out.println("count.result=" + _count__return);

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
        System.out.println("Invoking findAll...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune _findAll_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune();
        try {
            java.util.List<org.openspcoop2.core.registry.AccordoServizioParteComune> _findAll__return = port.findAll(_findAll_filter);
            System.out.println("findAll.result=" + _findAll__return);

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
        System.out.println("Invoking exists...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _exists_idAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        try {
            boolean _exists__return = port.exists(_exists_idAccordoServizioParteComune);
            System.out.println("exists.result=" + _exists__return);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking get...");
        org.openspcoop2.core.registry.IdAccordoServizioParteComune _get_idAccordoServizioParteComune = new org.openspcoop2.core.registry.IdAccordoServizioParteComune();
        try {
            org.openspcoop2.core.registry.AccordoServizioParteComune _get__return = port.get(_get_idAccordoServizioParteComune);
            System.out.println("get.result=" + _get__return);

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
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        }
            }
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
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune _deleteAllByFilter_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune();
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
        System.out.println("Invoking findAllIds...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune _findAllIds_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune();
        try {
            java.util.List<org.openspcoop2.core.registry.IdAccordoServizioParteComune> _findAllIds__return = port.findAllIds(_findAllIds_filter);
            System.out.println("findAllIds.result=" + _findAllIds__return);

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
        System.out.println("Invoking find...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune _find_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all.SearchFilterAccordoServizioParteComune();
        try {
            org.openspcoop2.core.registry.AccordoServizioParteComune _find__return = port.find(_find_filter);
            System.out.println("find.result=" + _find__return);

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
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
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
