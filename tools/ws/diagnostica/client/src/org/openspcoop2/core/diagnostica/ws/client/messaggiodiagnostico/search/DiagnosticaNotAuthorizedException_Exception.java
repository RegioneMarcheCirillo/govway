
package org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-02-20T15:28:31.059+01:00
 * Generated source version: 2.7.4
 */

@WebFault(name = "diagnostica-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
public class DiagnosticaNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.DiagnosticaNotAuthorizedException diagnosticaNotAuthorizedException;

    public DiagnosticaNotAuthorizedException_Exception() {
        super();
    }
    
    public DiagnosticaNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public DiagnosticaNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public DiagnosticaNotAuthorizedException_Exception(String message, org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.DiagnosticaNotAuthorizedException diagnosticaNotAuthorizedException) {
        super(message);
        this.diagnosticaNotAuthorizedException = diagnosticaNotAuthorizedException;
    }

    public DiagnosticaNotAuthorizedException_Exception(String message, org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.DiagnosticaNotAuthorizedException diagnosticaNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.diagnosticaNotAuthorizedException = diagnosticaNotAuthorizedException;
    }

    public org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.DiagnosticaNotAuthorizedException getFaultInfo() {
        return this.diagnosticaNotAuthorizedException;
    }
}
