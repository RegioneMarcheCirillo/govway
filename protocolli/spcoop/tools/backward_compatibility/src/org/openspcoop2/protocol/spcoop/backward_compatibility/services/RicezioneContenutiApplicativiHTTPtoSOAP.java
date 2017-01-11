/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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



package org.openspcoop2.protocol.spcoop.backward_compatibility.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.pdd.services.RequestInfo;
import org.openspcoop2.pdd.services.connector.ConnectorUtils;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.engine.URLProtocolContext;
import org.openspcoop2.protocol.engine.constants.IDService;
import org.openspcoop2.protocol.spcoop.backward_compatibility.config.BackwardCompatibilityProperties;
import org.openspcoop2.protocol.spcoop.backward_compatibility.config.Costanti;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;

/**
 * Servlet che serve per creare un tunnel da un servizio che non conosce SOAP verso OpenSPCoop (che utilizza SOAP)
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class RicezioneContenutiApplicativiHTTPtoSOAP extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private org.openspcoop2.pdd.services.connector.RicezioneContenutiApplicativiHTTPtoSOAPConnector ricezioneContenutiApplicativiHTTPtoSOAP = null;
	private BackwardCompatibilityProperties backwardCompatibilityProperties = null;
	
	@Override
	public void init() throws ServletException {
		try{
			this.ricezioneContenutiApplicativiHTTPtoSOAP = new org.openspcoop2.pdd.services.connector.RicezioneContenutiApplicativiHTTPtoSOAPConnector();
			this.backwardCompatibilityProperties = new BackwardCompatibilityProperties(OpenSPCoop2Properties.getInstance().getRootDirectory());
						
		}catch(Exception e){
			throw new ServletException("Errore durante l'inizializzazione della servlet 'RicezioneContenutiApplicativiHTTPtoSOAP' compatibile con le interfacce di OpenSPCoop v1: "+e.getMessage(),e);
		}
	}
	
	
	private void dispatch(HttpServletRequest req, HttpServletResponse res, HttpRequestMethod method)
	throws ServletException, IOException {
		WrapperHttpServletRequest httpReqWrapper = null;
		RequestInfo requestInfo = null;
		try{
			Logger log = OpenSPCoop2Logger.getLoggerOpenSPCoopCore();
			if(log==null){
				log = LoggerWrapperFactory.getLogger(RicezioneContenutiApplicativiHTTPtoSOAP.class);
			}
			httpReqWrapper = new WrapperHttpServletRequest(req,this.backwardCompatibilityProperties);
			PdDContext pddContext = new PdDContext();
			pddContext.addObject(Costanti.OPENSPCOOP2_BACKWARD_COMPATIBILITY, Costanti.OPENSPCOOP2_BACKWARD_COMPATIBILITY);
			httpReqWrapper.setAttribute(CostantiPdD.OPENSPCOOP2_PDD_CONTEXT_HEADER_HTTP, pddContext);
			URLProtocolContext protocolContext = new URLProtocolContext(httpReqWrapper, log, true);
			requestInfo = ConnectorUtils.getRequestInfo(ProtocolFactoryManager.getInstance().getProtocolFactoryByName("spcoop"), protocolContext);
			//System.out.println("getRequestURI=["+httpReqWrapper.getRequestURI()+"]");
			//System.out.println("getRequestURL=["+httpReqWrapper.getRequestURL()+"]");
			//System.out.println("getServletPath=["+httpReqWrapper.getServletPath()+"]");
		}catch(Exception e){
			try{
				Logger log = OpenSPCoop2Logger.getLoggerOpenSPCoopCore();
				if(log==null){
					log = LoggerWrapperFactory.getLogger(RicezioneContenutiApplicativiHTTPtoSOAP.class);
				}
				log.error(e.getMessage(), e);
			}catch(Exception eLog){}
			// NOTA: L'unica eccezione generata è quella di protocollo non inizializzato
			boolean generateErrorMessage = false;
			try{
				OpenSPCoop2Properties prop = OpenSPCoop2Properties.getInstance();
				generateErrorMessage = prop.isGenerazioneErroreProtocolloNonSupportato();
			}catch(Exception eRead){}
			if(generateErrorMessage){
				ConnectorUtils.generateErrorMessage(IDService.PORTA_DELEGATA_XML_TO_SOAP, method, req, res, e.getMessage(), false, true);
			}
			else{
				res.sendError(404, ConnectorUtils.generateError404Message(ConnectorUtils.getFullCodeProtocolUnsupported(IDService.PORTA_DELEGATA_XML_TO_SOAP)));
			}
			return;
		}
		try{
			this.ricezioneContenutiApplicativiHTTPtoSOAP.doEngine(requestInfo, httpReqWrapper, res,method);
		}catch(Exception e){
			throw new ServletException(e.getMessage(),e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.POST);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.DELETE);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.GET);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.HEAD);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.OPTIONS);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.PUT);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dispatch(req, resp, HttpRequestMethod.TRACE);
	}

}
