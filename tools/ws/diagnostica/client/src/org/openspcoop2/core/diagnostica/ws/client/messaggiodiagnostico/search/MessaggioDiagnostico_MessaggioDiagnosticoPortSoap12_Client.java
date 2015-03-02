
package org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search;

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
 * 2015-02-20T15:28:30.973+01:00
 * Generated source version: 2.7.4
 * 
 */
public final class MessaggioDiagnostico_MessaggioDiagnosticoPortSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/diagnostica/management", "MessaggioDiagnosticoSoap12Service");

    private MessaggioDiagnostico_MessaggioDiagnosticoPortSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = MessaggioDiagnosticoSoap12Service.WSDL_LOCATION;
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
      
        MessaggioDiagnosticoSoap12Service ss = new MessaggioDiagnosticoSoap12Service(wsdlURL, MessaggioDiagnostico_MessaggioDiagnosticoPortSoap12_Client.SERVICE_NAME);
        MessaggioDiagnostico port = ss.getMessaggioDiagnosticoPortSoap12();
	
		new org.openspcoop2.core.diagnostica.ws.client.utils.RequestContextUtils("messaggioDiagnostico.soap12").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking findAll...");
        org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico _findAll_filter = new org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico();
        try {
            java.util.List<org.openspcoop2.core.diagnostica.MessaggioDiagnostico> _findAll__return = port.findAll(_findAll_filter);
            System.out.println("findAll.result=" + _findAll__return);

        } catch (DiagnosticaServiceException_Exception e) { 
            System.out.println("Expected exception: diagnostica-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (DiagnosticaNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: diagnostica-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (DiagnosticaNotImplementedException_Exception e) { 
            System.out.println("Expected exception: diagnostica-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking count...");
        org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico _count_filter = new org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico();
        try {
            long _count__return = port.count(_count_filter);
            System.out.println("count.result=" + _count__return);

        } catch (DiagnosticaServiceException_Exception e) { 
            System.out.println("Expected exception: diagnostica-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (DiagnosticaNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: diagnostica-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (DiagnosticaNotImplementedException_Exception e) { 
            System.out.println("Expected exception: diagnostica-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
