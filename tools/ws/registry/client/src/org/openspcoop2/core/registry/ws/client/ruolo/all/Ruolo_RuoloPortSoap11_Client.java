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

package org.openspcoop2.core.registry.ws.client.ruolo.all;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-05-30T09:49:04.389+02:00
 * Generated source version: 3.1.7
 * 
 */
public final class Ruolo_RuoloPortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/registry/management", "RuoloSoap11Service");

    private Ruolo_RuoloPortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RuoloSoap11Service.WSDL_LOCATION;
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
      
        RuoloSoap11Service ss = new RuoloSoap11Service(wsdlURL, Ruolo_RuoloPortSoap11_Client.SERVICE_NAME);
        Ruolo port = ss.getRuoloPortSoap11();
	
		new org.openspcoop2.core.registry.ws.client.utils.RequestContextUtils("ruolo.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking find...");
        org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo _find_filter = new org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo();
        try {
            org.openspcoop2.core.registry.Ruolo _find__return = port.find(_find_filter);
            System.out.println("find.result=" + _find__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking get...");
        org.openspcoop2.core.registry.IdRuolo _get_idRuolo = new org.openspcoop2.core.registry.IdRuolo();
        try {
            org.openspcoop2.core.registry.Ruolo _get__return = port.get(_get_idRuolo);
            System.out.println("get.result=" + _get__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteById...");
        org.openspcoop2.core.registry.IdRuolo _deleteById_idRuolo = new org.openspcoop2.core.registry.IdRuolo();
        try {
            port.deleteById(_deleteById_idRuolo);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking inUse...");
        org.openspcoop2.core.registry.IdRuolo _inUse_idRuolo = new org.openspcoop2.core.registry.IdRuolo();
        try {
            org.openspcoop2.core.registry.ws.client.ruolo.all.UseInfo _inUse__return = port.inUse(_inUse_idRuolo);
            System.out.println("inUse.result=" + _inUse__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking create...");
        org.openspcoop2.core.registry.Ruolo _create_ruolo = new org.openspcoop2.core.registry.Ruolo();
        try {
            port.create(_create_ruolo);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking exists...");
        org.openspcoop2.core.registry.IdRuolo _exists_idRuolo = new org.openspcoop2.core.registry.IdRuolo();
        try {
            boolean _exists__return = port.exists(_exists_idRuolo);
            System.out.println("exists.result=" + _exists__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryMultipleResultException_Exception e) { 
            System.out.println("Expected exception: registry-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateOrCreate...");
        org.openspcoop2.core.registry.IdRuolo _updateOrCreate_oldIdRuolo = new org.openspcoop2.core.registry.IdRuolo();
        org.openspcoop2.core.registry.Ruolo _updateOrCreate_ruolo = new org.openspcoop2.core.registry.Ruolo();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdRuolo, _updateOrCreate_ruolo);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAllByFilter...");
        org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo _deleteAllByFilter_filter = new org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo();
        try {
            long _deleteAllByFilter__return = port.deleteAllByFilter(_deleteAllByFilter_filter);
            System.out.println("deleteAllByFilter.result=" + _deleteAllByFilter__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking findAllIds...");
        org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo _findAllIds_filter = new org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo();
        try {
            java.util.List<org.openspcoop2.core.registry.IdRuolo> _findAllIds__return = port.findAllIds(_findAllIds_filter);
            System.out.println("findAllIds.result=" + _findAllIds__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking count...");
        org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo _count_filter = new org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo();
        try {
            long _count__return = port.count(_count_filter);
            System.out.println("count.result=" + _count__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAll...");
        try {
            long _deleteAll__return = port.deleteAll();
            System.out.println("deleteAll.result=" + _deleteAll__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking update...");
        org.openspcoop2.core.registry.IdRuolo _update_oldIdRuolo = new org.openspcoop2.core.registry.IdRuolo();
        org.openspcoop2.core.registry.Ruolo _update_ruolo = new org.openspcoop2.core.registry.Ruolo();
        try {
            port.update(_update_oldIdRuolo, _update_ruolo);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking findAll...");
        org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo _findAll_filter = new org.openspcoop2.core.registry.ws.client.ruolo.all.SearchFilterRuolo();
        try {
            java.util.List<org.openspcoop2.core.registry.Ruolo> _findAll__return = port.findAll(_findAll_filter);
            System.out.println("findAll.result=" + _findAll__return);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking delete...");
        org.openspcoop2.core.registry.Ruolo _delete_ruolo = new org.openspcoop2.core.registry.Ruolo();
        try {
            port.delete(_delete_ruolo);

        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
