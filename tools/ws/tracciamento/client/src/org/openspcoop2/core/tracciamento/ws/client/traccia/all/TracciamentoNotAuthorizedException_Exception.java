
package org.openspcoop2.core.tracciamento.ws.client.traccia.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-02-16T16:08:57.995+01:00
 * Generated source version: 2.7.4
 */

@WebFault(name = "tracciamento-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
public class TracciamentoNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.tracciamento.ws.client.traccia.all.TracciamentoNotAuthorizedException tracciamentoNotAuthorizedException;

    public TracciamentoNotAuthorizedException_Exception() {
        super();
    }
    
    public TracciamentoNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public TracciamentoNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public TracciamentoNotAuthorizedException_Exception(String message, org.openspcoop2.core.tracciamento.ws.client.traccia.all.TracciamentoNotAuthorizedException tracciamentoNotAuthorizedException) {
        super(message);
        this.tracciamentoNotAuthorizedException = tracciamentoNotAuthorizedException;
    }

    public TracciamentoNotAuthorizedException_Exception(String message, org.openspcoop2.core.tracciamento.ws.client.traccia.all.TracciamentoNotAuthorizedException tracciamentoNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.tracciamentoNotAuthorizedException = tracciamentoNotAuthorizedException;
    }

    public org.openspcoop2.core.tracciamento.ws.client.traccia.all.TracciamentoNotAuthorizedException getFaultInfo() {
        return this.tracciamentoNotAuthorizedException;
    }
}
