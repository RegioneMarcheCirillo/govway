
package org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-02-16T12:07:47.205+01:00
 * Generated source version: 2.7.4
 */

@WebFault(name = "diagnostica-multiple-result-exception", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
public class DiagnosticaMultipleResultException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.all.DiagnosticaMultipleResultException diagnosticaMultipleResultException;

    public DiagnosticaMultipleResultException_Exception() {
        super();
    }
    
    public DiagnosticaMultipleResultException_Exception(String message) {
        super(message);
    }
    
    public DiagnosticaMultipleResultException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public DiagnosticaMultipleResultException_Exception(String message, org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.all.DiagnosticaMultipleResultException diagnosticaMultipleResultException) {
        super(message);
        this.diagnosticaMultipleResultException = diagnosticaMultipleResultException;
    }

    public DiagnosticaMultipleResultException_Exception(String message, org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.all.DiagnosticaMultipleResultException diagnosticaMultipleResultException, Throwable cause) {
        super(message, cause);
        this.diagnosticaMultipleResultException = diagnosticaMultipleResultException;
    }

    public org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.all.DiagnosticaMultipleResultException getFaultInfo() {
        return this.diagnosticaMultipleResultException;
    }
}
