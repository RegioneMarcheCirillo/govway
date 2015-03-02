package org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-02-20T15:28:43.204+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", name = "InformazioniProtocolloTransazione")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.diagnostica.ObjectFactory.class})
public interface InformazioniProtocolloTransazione {

    @WebResult(name = "informazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.FindAll")
    @WebMethod(action = "findAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.FindAllResponse")
    public java.util.List<org.openspcoop2.core.diagnostica.InformazioniProtocolloTransazione> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.SearchFilterInformazioniProtocolloTransazione filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception;

    @WebResult(name = "informazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.Get")
    @WebMethod(action = "get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.GetResponse")
    public org.openspcoop2.core.diagnostica.InformazioniProtocolloTransazione get(
        @WebParam(name = "idInformazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.IdInformazioniProtocolloTransazione idInformazioniProtocolloTransazione
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotFoundException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception, DiagnosticaMultipleResultException_Exception;

    @WebResult(name = "informazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.Find")
    @WebMethod(action = "find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.FindResponse")
    public org.openspcoop2.core.diagnostica.InformazioniProtocolloTransazione find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.SearchFilterInformazioniProtocolloTransazione filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotFoundException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception, DiagnosticaMultipleResultException_Exception;

    @WebResult(name = "idInformazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.FindAllIds")
    @WebMethod(action = "findAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.FindAllIdsResponse")
    public java.util.List<org.openspcoop2.core.diagnostica.IdInformazioniProtocolloTransazione> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.SearchFilterInformazioniProtocolloTransazione filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception;

    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.Exists")
    @WebMethod(action = "exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.ExistsResponse")
    public boolean exists(
        @WebParam(name = "idInformazioniProtocolloTransazione", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.IdInformazioniProtocolloTransazione idInformazioniProtocolloTransazione
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception, DiagnosticaMultipleResultException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.Count")
    @WebMethod(action = "count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.CountResponse")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.informazioniprotocollotransazione.search.SearchFilterInformazioniProtocolloTransazione filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotAuthorizedException_Exception, DiagnosticaNotImplementedException_Exception;
}
