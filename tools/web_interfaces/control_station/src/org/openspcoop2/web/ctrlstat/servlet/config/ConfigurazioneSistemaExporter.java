/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
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


package org.openspcoop2.web.ctrlstat.servlet.config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.openspcoop2.pdd.core.jmx.InformazioniStatoPorta;
import org.openspcoop2.pdd.core.jmx.InformazioniStatoPortaCache;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.lib.mvc.PageData;

/**
 * Questa servlet si occupa di esportare le tracce in formato xml zippandole
 * 
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class ConfigurazioneSistemaExporter extends HttpServlet {

	private static final long serialVersionUID = -7341279067126334095L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}

	/**
	 * Processa la richiesta pervenuta e si occupa di fornire i dati richiesti
	 * in formato zip
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();
		
		ConfigurazioneHelper confHelper = null;
		try {
			confHelper = new ConfigurazioneHelper(request, pd, session);
		
			ControlStationCore.logDebug("Ricevuta Richiesta di esportazione configurazione di Sistema...");
			Enumeration<?> en = confHelper.getParameterNames();
			ControlStationCore.logDebug("Parametri (nome = valore):\n-----------------");
			while (en.hasMoreElements()) {
				String param = (String) en.nextElement();
				String value = confHelper.getParameter(param);
				ControlStationCore.logDebug(param + " = " + value);
			}
			ControlStationCore.logDebug("-----------------");
			
		}catch(Exception e){
			throw new ServletException(e.getMessage(),e);
		}
	
		try {
			
			String alias = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_SISTEMA_NODO_CLUSTER);
			
			ConfigurazioneCore confCore = new ConfigurazioneCore();
			
			// Setto Proprietà Export File
			HttpUtilities.setOutputFile(response, true, "ConfigurazioneSistema.txt");
				
			OutputStream out = response.getOutputStream();	
			out.write(getInformazioniStatoPorta(alias, confCore).getBytes());
			out.flush();
			out.close();
		
		} catch (Exception e) {
			ControlStationCore.logError("Errore durante l'export della configurazione di sistema: "+e.getMessage(), e);
			throw new ServletException(e);
		} 
	}

	
	private String getInformazioniStatoPorta(String alias, ConfigurazioneCore confCore) throws Exception{
		
		InformazioniStatoPorta infoStatoPorta = new InformazioniStatoPorta();
		
		Object gestoreRisorseJMX = confCore.getGestoreRisorseJMX(alias);
		
		String versionePdD = null;
		try{
			versionePdD = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versionePdD(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione della PdD (jmxResourcePdD): "+e.getMessage(),e);
			versionePdD = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String versioneBaseDati = null;
		try{
			versioneBaseDati = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versioneBaseDati(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione della base dati (jmxResourcePdD): "+e.getMessage(),e);
			versioneBaseDati = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String confDir = null;
		try{
			confDir = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_directoryConfigurazione(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della directory di configurazione (jmxResourcePdD): "+e.getMessage(),e);
			confDir = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String versioneJava = null;
		try{
			versioneJava = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versioneJava(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione di java (jmxResourcePdD): "+e.getMessage(),e);
			versioneJava = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String vendorJava = null;
		try{
			vendorJava = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_vendorJava(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sul vendor di java (jmxResourcePdD): "+e.getMessage(),e);
			vendorJava = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String messageFactory = null;
		try{
			messageFactory = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_messageFactory(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della message factory (jmxResourcePdD): "+e.getMessage(),e);
			messageFactory = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPD = null;
		try{
			statoServizioPD = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaDelegata(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Delegata (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPD = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPD_abilitazioni = null;
		try{
			statoServizioPD_abilitazioni = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaDelegataAbilitazioniPuntuali(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Delegata 'Abilitazioni Puntuali' (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPD_abilitazioni = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPD_disabilitazioni = null;
		try{
			statoServizioPD_disabilitazioni = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaDelegataDisabilitazioniPuntuali(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Delegata 'Disabilitazioni Puntuali' (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPD_disabilitazioni = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPA = null;
		try{
			statoServizioPA = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaApplicativa(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Applicativa (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPA = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPA_abilitazioni = null;
		try{
			statoServizioPA_abilitazioni = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaApplicativaAbilitazioniPuntuali(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Applicativa 'Abilitazioni Puntuali' (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPA_abilitazioni = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioPA_disabilitazioni = null;
		try{
			statoServizioPA_disabilitazioni = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaApplicativaDisabilitazioniPuntuali(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Porta Applicativa 'Disabilitazioni Puntuali' (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioPA_disabilitazioni = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoServizioIM = null;
		try{
			statoServizioIM = confCore.readJMXAttribute(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaStatoServiziPdD(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_statoServizioPortaApplicativa(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del servizio Integration Manager (jmxResourcePdD): "+e.getMessage(),e);
			statoServizioIM = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String livelloSeveritaDiagnostici = null;
		try{
			livelloSeveritaDiagnostici = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_severitaDiagnostici(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura del livello di severità dei diagnostici (jmxResourcePdD): "+e.getMessage(),e);
			livelloSeveritaDiagnostici = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String livelloSeveritaDiagnosticiLog4j = null;
		try{
			livelloSeveritaDiagnosticiLog4j = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_severitaDiagnosticiLog4j(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura del livello di severità log4j dei diagnostici (jmxResourcePdD): "+e.getMessage(),e);
			livelloSeveritaDiagnosticiLog4j = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String log4j_diagnostica = null;
		try{
			log4j_diagnostica = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_log4j_diagnostica(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del file di log Log4j govway_diagnostici.log (jmxResourcePdD): "+e.getMessage(),e);
			log4j_diagnostica = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String log4j_openspcoop = null;
		try{
			log4j_openspcoop = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_log4j_openspcoop(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del file di log Log4j govway.log (jmxResourcePdD): "+e.getMessage(),e);
			log4j_openspcoop = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String log4j_integrationManager = null;
		try{
			log4j_integrationManager = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_log4j_integrationManager(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del file di log Log4j govway_integrationManager.log (jmxResourcePdD): "+e.getMessage(),e);
			log4j_integrationManager = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String tracciamento = null;
		try{
			tracciamento = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_tracciamento(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del tracciamento buste (jmxResourcePdD): "+e.getMessage(),e);
			tracciamento = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String dumpPD = null;
		try{
			dumpPD = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_dumpPD(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del dump binario della Porta Delegata (jmxResourcePdD): "+e.getMessage(),e);
			dumpPD = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String dumpPA = null;
		try{
			dumpPA = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_dumpPA(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del dump binario della Porta Applicativa (jmxResourcePdD): "+e.getMessage(),e);
			dumpPA = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String log4j_tracciamento = null;
		try{
			log4j_tracciamento = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_log4j_tracciamento(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del file di log Log4j govway_tracciamento.log (jmxResourcePdD): "+e.getMessage(),e);
			log4j_tracciamento = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String log4j_dump = null;
		try{
			log4j_dump = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_log4j_dump(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del file di log Log4j govway_dump.log (jmxResourcePdD): "+e.getMessage(),e);
			log4j_dump = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String fileTraceGovWayState = null;
		try{
			fileTraceGovWayState = confCore.invokeJMXMethod(gestoreRisorseJMX, alias, confCore.getJmxPdD_configurazioneSistema_type(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_getFileTrace(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato di configurazione del file trace (jmxResourcePdD): "+e.getMessage(),e);
			log4j_dump = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		
		String errorSpecificTypeProcessRequest = null;
		try{
			errorSpecificTypeProcessRequest = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionSpecificErrorTypeInternalRequestError(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_TYPE+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SPECIFIC_ERROR_TYPE_PROCESS_REQUEST+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorSpecificTypeProcessRequest = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorSpecificTypeProcessResponse_1 = null;
		try{
			errorSpecificTypeProcessResponse_1 = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionSpecificErrorTypeBadResponse(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_TYPE+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SPECIFIC_ERROR_TYPE_PROCESS_RESPONSE+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorSpecificTypeProcessResponse_1 = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		String errorSpecificTypeProcessResponse_2 = null;
		try{
			errorSpecificTypeProcessResponse_2 = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionSpecificErrorTypeInternalResponseError(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_TYPE+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SPECIFIC_ERROR_TYPE_PROCESS_RESPONSE+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorSpecificTypeProcessResponse_2 = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		String errorSpecificTypeProcessResponse = null;
		if(ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE.contentEquals(errorSpecificTypeProcessResponse_1)) {
			errorSpecificTypeProcessResponse = errorSpecificTypeProcessResponse_1;
		}
		else if(ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE.contentEquals(errorSpecificTypeProcessResponse_2)) {
			errorSpecificTypeProcessResponse = errorSpecificTypeProcessResponse_2;
		}
		else {
			errorSpecificTypeProcessResponse = ("true".equals(errorSpecificTypeProcessResponse_1) && "true".equals(errorSpecificTypeProcessResponse_2)) + ""; 
		}
		
		String errorSpecificTypeInternalError = null;
		try{
			errorSpecificTypeInternalError = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionSpecificErrorTypeInternalError(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_TYPE+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SPECIFIC_ERROR_TYPE_INTERNAL_ERROR+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorSpecificTypeInternalError = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorStatus = null;
		try{
			errorStatus = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionErrorStatusCode(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_STATUS+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_STATUS_CODE+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorStatus = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorStatusInSoapFaultCode = null;
		try{
			errorStatusInSoapFaultCode = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionErrorUseStatusCodeAsFaultCode(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_STATUS+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SOAP_USE_STATUS_CODE_AS_SOAP_FAULT+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorStatusInSoapFaultCode = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorSpecificDetails = null;
		try{
			errorSpecificDetails = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionSpecificErrorDetails(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_DETAILS+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SPECIFIC_ERROR_DETAILS+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorSpecificDetails = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorInstanceId = null;
		try{
			errorInstanceId = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionErrorInstanceId(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_INSTANCE+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_INSTANCE_ID+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorInstanceId = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String errorGenerateHttpErrorCodeInSoap = null;
		try{
			errorGenerateHttpErrorCodeInSoap = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeAttributo_transactionErrorGenerateHttpHeaderGovWayCode(alias));
		}catch(Exception e){
			String tipo = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SOAP_GENERATE_HTTP+" ("+
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TRANSACTION_ERROR_SOAP_GENERATE_HTTP_CODE+")";
			ControlStationCore.logError("Errore durante la lettura dello stato (jmxResourcePdD) ["+tipo+"]: "+e.getMessage(),e);
			errorGenerateHttpErrorCodeInSoap = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}		

		String infoDatabase = null;
		try{
			infoDatabase = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniDatabase(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sul database (jmxResourcePdD): "+e.getMessage(),e);
			infoDatabase = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		HashMap<String, String> infoConnessioneAltriDB = null;
		HashMap<String, String> statoConnessioniAltriDB = null;
		try{
			int numeroDatasource = 0;
			try{
				String stato = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
						confCore.getJmxPdD_configurazioneSistema_nomeRisorsaDatasourceGW(alias),
						confCore.getJmxPdD_configurazioneSistema_nomeAttributo_numeroDatasourceGW(alias));
				if(stato!=null && !"".equals(stato)) {
					numeroDatasource = Integer.valueOf(stato);
				}
			}catch(Exception e){
				ControlStationCore.logDebug("Numero di datasource attivi non ottenibili: "+e.getMessage());
			}
			if(numeroDatasource>0) {
				String nomiDatasource = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
						confCore.getJmxPdD_configurazioneSistema_nomeRisorsaDatasourceGW(alias),
						confCore.getJmxPdD_configurazioneSistema_nomeMetodo_getDatasourcesGW(alias));
				if(nomiDatasource!=null && !"".equals(nomiDatasource)) {
					/* Esempio:
					 * 3 datasource allocati: 
	(2020-01-23_15:40:22.391) idDatasource:88c4db87-07a5-4fa6-95a5-e6caf4c21a7f jndiName:org.govway.datasource.tracciamento ConnessioniAttive:0
	(2020-01-23_15:40:22.396) idDatasource:bae6582a-659b-4b70-bc9c-aca3570b45af jndiName:org.govway.datasource.statistiche ConnessioniAttive:0
	(2020-01-23_15:40:22.627) idDatasource:4ff843af-94d6-4506-8ecf-aac52bcb3525 jndiName:org.govway.datasource.console ConnessioniAttive:0
					 **/
					String [] lines = nomiDatasource.split("\n");
					if(lines!=null && lines.length>0) {
						for (String line : lines) {
							if(line.startsWith("(")) {
								String [] tmp = line.split(" ");
								if(tmp!=null && tmp.length>3) {
									String nomeDS = tmp[2]+" "+tmp[1];
									try{
										String idDS = tmp[1].split(":")[1];
										
										String statoInfo = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
												confCore.getJmxPdD_configurazioneSistema_nomeRisorsaDatasourceGW(alias),
												confCore.getJmxPdD_configurazioneSistema_nomeMetodo_getInformazioniDatabaseDatasourcesGW(alias),
												idDS);
										if(infoConnessioneAltriDB==null) {
											infoConnessioneAltriDB = new HashMap<String, String>();
										}
										infoConnessioneAltriDB.put(nomeDS,statoInfo);
										
										String statoConnessioni = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
												confCore.getJmxPdD_configurazioneSistema_nomeRisorsaDatasourceGW(alias),
												confCore.getJmxPdD_configurazioneSistema_nomeMetodo_getUsedConnectionsDatasourcesGW(alias),
												idDS);
										if(statoConnessioniAltriDB==null) {
											statoConnessioniAltriDB = new HashMap<String, String>();
										}
										statoConnessioniAltriDB.put(nomeDS,statoConnessioni);
										
									}catch(Exception e){
										ControlStationCore.logError("Errore durante la lettura delle informazioni verso il database "+nomeDS+" (jmxResourcePdD): "+e.getMessage(),e);
										
										if(infoConnessioneAltriDB==null) {
											infoConnessioneAltriDB = new HashMap<String, String>();
										}
										infoConnessioneAltriDB.put(nomeDS,ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE);
										
										if(statoConnessioniAltriDB==null) {
											statoConnessioniAltriDB = new HashMap<String, String>();
										}
										statoConnessioniAltriDB.put(nomeDS,ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE);
									}		
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni verso altri database (jmxResourcePdD): "+e.getMessage(),e);
			
			if(infoConnessioneAltriDB==null) {
				infoConnessioneAltriDB = new HashMap<String, String>();
			}
			infoConnessioneAltriDB.put("GovWayDatasources",ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE);
			
			if(statoConnessioniAltriDB==null) {
				statoConnessioniAltriDB = new HashMap<String, String>();
			}
			statoConnessioniAltriDB.put("GovWayDatasources",ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE);
		}
		
		
		
		String infoSSL = null;
		try{
			infoSSL = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCompleteSSL(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni SSL (jmxResourcePdD): "+e.getMessage(),e);
			infoSSL = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoCryptographyKeyLength = null;
		try{
			infoCryptographyKeyLength = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCryptographyKeyLength(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sulla lunghezza delle chiavi di cifratura (jmxResourcePdD): "+e.getMessage(),e);
			infoCryptographyKeyLength = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoCharset = null;
		try{
			infoCharset = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCharset(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sul charset (jmxResourcePdD): "+e.getMessage(),e);
			infoCharset = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoInternazionalizzazione = null;
		try{
			infoInternazionalizzazione = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCompleteInternazionalizzazione(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sull'internazionalizzazione (jmxResourcePdD): "+e.getMessage(),e);
			infoInternazionalizzazione = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoTimeZone = null;
		try{
			infoTimeZone = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCompleteTimeZone(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sul TimeZone (jmxResourcePdD): "+e.getMessage(),e);
			infoTimeZone = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoProprietaJavaNetworking = null;
		try{
			infoProprietaJavaNetworking = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniCompleteProprietaJavaNetworking(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sulle proprietà java di networking (jmxResourcePdD): "+e.getMessage(),e);
			infoProprietaJavaNetworking = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoProprietaJavaAltro = null;
		try{
			infoProprietaJavaAltro = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniProprietaJavaAltro(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sulle proprietà java (escluse quelle di networking) (jmxResourcePdD): "+e.getMessage(),e);
			infoProprietaJavaAltro = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoProprietaSistema = null;
		try{
			infoProprietaSistema = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniProprietaSistema(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sulle proprietà di sistema (jmxResourcePdD): "+e.getMessage(),e);
			infoProprietaSistema = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoProtocolli = null;
		try{
			infoProtocolli = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_pluginProtocols(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sui protocolli (jmxResourcePdD): "+e.getMessage(),e);
			infoProtocolli = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		InformazioniStatoPortaCache [] cacheArray = null;
		
		List<String> caches = confCore.getJmxPdD_caches(alias);
		if(caches!=null && caches.size()>0){
			
			cacheArray = new InformazioniStatoPortaCache[caches.size()];
			int i = 0;
			for (String cacheName : caches) {
				
				boolean enabled = false;
				try{
					String stato = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
							cacheName,
							confCore.getJmxPdD_cache_nomeAttributo_cacheAbilitata(alias));
					if(stato.equalsIgnoreCase("true")){
						enabled = true;
					}
				}catch(Exception e){
					ControlStationCore.logError("Errore durante la lettura dello stato della cache ["+cacheName+"](jmxResourcePdD): "+e.getMessage(),e);
				}
				
				cacheArray[i] = new InformazioniStatoPortaCache(cacheName, enabled);
				
				if(enabled){
					String params = null;
					try{
						params = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_cache_type(alias), 
								cacheName,
								confCore.getJmxPdD_cache_nomeMetodo_statoCache(alias));
					}catch(Exception e){
						ControlStationCore.logError("Errore durante la lettura dello stato della cache ["+cacheName+"](jmxResourcePdD): "+e.getMessage(),e);
						params = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
					}
					cacheArray[i].setStatoCache(params);
				}
						
				i++;
			}
		}
		
		String statoConnessioniDB = null;
		try{
			statoConnessioniDB = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_connessioniDB(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato delle connessioni al database (jmxResourcePdD): "+e.getMessage(),e);
			statoConnessioniDB = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		// statoConnessioniAltriDB, letto prima durante l'acquisizione delle informazioni
				
		String statoConnessioniJMS = null;
		try{
			statoConnessioniJMS = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_connessioniJMS(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato delle connessioni al broker JMS (jmxResourcePdD): "+e.getMessage(),e);
			statoConnessioniJMS = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoTransazioniId = null;
		try{
			statoTransazioniId = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_idTransazioniAttive(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura degli identificativi delle transazioni attive (jmxResourcePdD): "+e.getMessage(),e);
			statoTransazioniId = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoTransazioniIdProtocollo = null;
		try{
			statoTransazioniIdProtocollo = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_idProtocolloTransazioniAttive(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura degli identificativi di protocollo delle transazioni attive (jmxResourcePdD): "+e.getMessage(),e);
			statoTransazioniIdProtocollo = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoConnessioniPD = null;
		try{
			statoConnessioniPD = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_connessioniPD(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato delle connessioni al servizio PD (jmxResourcePdD): "+e.getMessage(),e);
			statoConnessioniPD = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoConnessioniPA = null;
		try{
			statoConnessioniPA = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaMonitoraggio(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_connessioniPA(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato delle connessioni al servizio PA (jmxResourcePdD): "+e.getMessage(),e);
			statoConnessioniPA = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String statoPoolThread = null;
		try{
			statoPoolThread = confCore.invokeJMXMethod(gestoreRisorseJMX, alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConsegnaContenutiApplicativi(alias),
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_getThreadPoolStatus(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura dello stato del thread pool per la consegna agli applicativi (jmxResourcePdD): "+e.getMessage(),e);
			statoPoolThread = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoInstallazione = null;
		try{
			infoInstallazione = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniInstallazione(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sull'installazione (jmxResourcePdD): "+e.getMessage(),e);
			infoInstallazione = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		return infoStatoPorta.formatStatoPorta(versionePdD, versioneBaseDati, confDir, versioneJava, vendorJava, messageFactory,
				statoServizioPD,statoServizioPD_abilitazioni,statoServizioPD_disabilitazioni,
				statoServizioPA,statoServizioPA_abilitazioni,statoServizioPA_disabilitazioni,
				statoServizioIM,
				livelloSeveritaDiagnostici, livelloSeveritaDiagnosticiLog4j,
				"true".equals(log4j_diagnostica), "true".equals(log4j_openspcoop), "true".equals(log4j_integrationManager), 
				"true".equals(tracciamento), "true".equals(dumpPD), "true".equals(dumpPA),
				"true".equals(log4j_tracciamento), "true".equals(log4j_dump), 
				fileTraceGovWayState,
				"true".equals(errorSpecificTypeProcessRequest), "true".equals(errorSpecificTypeProcessResponse), "true".equals(errorSpecificTypeInternalError),
				"true".equals(errorStatus), "true".equals(errorStatusInSoapFaultCode),
				"true".equals(errorSpecificDetails), "true".equals(errorInstanceId), "true".equals(errorGenerateHttpErrorCodeInSoap),
				infoDatabase, infoConnessioneAltriDB , 
				infoSSL, infoCryptographyKeyLength, 
				infoCharset, infoInternazionalizzazione, infoTimeZone, 
				infoProprietaJavaNetworking, infoProprietaJavaAltro, infoProprietaSistema,
				infoProtocolli,
				statoConnessioniDB, statoConnessioniAltriDB, statoConnessioniJMS,
				statoTransazioniId, statoTransazioniIdProtocollo,
				statoConnessioniPD, statoConnessioniPA, 
				statoPoolThread,
				infoInstallazione,
				cacheArray);
	}
}
