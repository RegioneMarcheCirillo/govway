
package org.openspcoop2.core.registry.ws.client.scope.crud;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-05-30T09:51:58.067+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "registry-service-exception", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class RegistryServiceException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.registry.ws.client.scope.crud.RegistryServiceException registryServiceException;

    public RegistryServiceException_Exception() {
        super();
    }
    
    public RegistryServiceException_Exception(String message) {
        super(message);
    }
    
    public RegistryServiceException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryServiceException_Exception(String message, org.openspcoop2.core.registry.ws.client.scope.crud.RegistryServiceException registryServiceException) {
        super(message);
        this.registryServiceException = registryServiceException;
    }

    public RegistryServiceException_Exception(String message, org.openspcoop2.core.registry.ws.client.scope.crud.RegistryServiceException registryServiceException, Throwable cause) {
        super(message, cause);
        this.registryServiceException = registryServiceException;
    }

    public org.openspcoop2.core.registry.ws.client.scope.crud.RegistryServiceException getFaultInfo() {
        return this.registryServiceException;
    }
}