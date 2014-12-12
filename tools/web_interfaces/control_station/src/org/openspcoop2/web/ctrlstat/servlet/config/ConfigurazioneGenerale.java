/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved. 
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


package org.openspcoop2.web.ctrlstat.servlet.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.config.Attachments;
import org.openspcoop2.core.config.Configurazione;
import org.openspcoop2.core.config.IndirizzoRisposta;
import org.openspcoop2.core.config.InoltroBusteNonRiscontrate;
import org.openspcoop2.core.config.IntegrationManager;
import org.openspcoop2.core.config.MessaggiDiagnostici;
import org.openspcoop2.core.config.Risposte;
import org.openspcoop2.core.config.Tracciamento;
import org.openspcoop2.core.config.ValidazioneBuste;
import org.openspcoop2.core.config.ValidazioneContenutiApplicativi;
import org.openspcoop2.core.config.constants.Severita;
import org.openspcoop2.core.config.constants.StatoFunzionalita;
import org.openspcoop2.core.config.constants.StatoFunzionalitaConWarning;
import org.openspcoop2.core.config.constants.TipoConnessioneRisposte;
import org.openspcoop2.core.config.constants.ValidazioneBusteTipoControllo;
import org.openspcoop2.core.config.constants.ValidazioneContenutiApplicativiTipo;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.users.dao.User;

/**
 * configurazione
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class ConfigurazioneGenerale extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		String userLogin = ServletUtils.getUserLoginFromSession(session);	

		try {


			ConfigurazioneHelper confHelper = new ConfigurazioneHelper(request, pd, session);
			ConfigurazioneCore confCore = new ConfigurazioneCore();

			User user = ServletUtils.getUserFromSession(session);

			String inoltromin = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			String stato = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO);
			String controllo = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			String severita = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA);
			String severita_log4j = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA_LOG4J);
			String integman = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INTEGMAN);
			String nomeintegman = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
			String profcoll = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			String connessione = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			String utilizzo = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			String validman = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			String gestman = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_GESTMAN);
			String registrazioneTracce = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
			String dump = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DUMP);
			String xsd = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_XSD);
			String tipoValidazione = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE);
			String applicaMTOM = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_APPLICA_MTOM);

			Boolean confPersB = ServletUtils.getConfigurazioniPersonalizzateFromSession(session); 
			String confPers = confPersB ? "true" : "false";
			Configurazione configurazione = confCore.getConfigurazioneGenerale();

			// Preparo il menu
			confHelper.makeMenu();

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, null));

			ServletUtils.setPageDataTitle(pd, lstParam);

			// Se idhid != null, modifico i dati della porta di dominio nel
			// db
			if (!ServletUtils.isEditModeInProgress(request)) {
				// Controlli sui campi immessi
				boolean isOk = confHelper.configurazioneCheckData();
				if (!isOk) {
					// preparo i campi
					Vector<DataElement> dati = new Vector<DataElement>();

					dati.addElement(ServletUtils.getDataElementForEditModeFinished());

					dati =	confHelper.addConfigurazioneErrorModeToDati(user, 
							inoltromin, stato, controllo, severita, severita_log4j,
							integman, nomeintegman, profcoll, connessione,
							utilizzo, validman, gestman, registrazioneTracce, dump, xsd,
							tipoValidazione, confPers, configurazione, dati);

					pd.setDati(dati);

					ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

					return ServletUtils.getStrutsForwardEditModeCheckError(mapping, 
							ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_GENERALE,
							ConfigurazioneCostanti.TIPO_OPERAZIONE_CONFIGURAZIONE_GENERALE);
				}

				// Modifico i dati della porta di dominio nel db
				Configurazione newConfigurazione = confCore.getConfigurazioneGenerale();

				if (newConfigurazione.getInoltroBusteNonRiscontrate() != null) {
					newConfigurazione.getInoltroBusteNonRiscontrate().setCadenza(inoltromin);
				} else {
					InoltroBusteNonRiscontrate ibnr = new InoltroBusteNonRiscontrate();
					ibnr.setCadenza(inoltromin);
					newConfigurazione.setInoltroBusteNonRiscontrate(ibnr);
				}

				if (newConfigurazione.getValidazioneBuste() != null) {
					newConfigurazione.getValidazioneBuste().setStato(StatoFunzionalitaConWarning.toEnumConstant(stato));
					newConfigurazione.getValidazioneBuste().setControllo(ValidazioneBusteTipoControllo.toEnumConstant(controllo));
					newConfigurazione.getValidazioneBuste().setProfiloCollaborazione(StatoFunzionalita.toEnumConstant(profcoll));
					newConfigurazione.getValidazioneBuste().setManifestAttachments(StatoFunzionalita.toEnumConstant(validman));
				} else {
					ValidazioneBuste vbe = new ValidazioneBuste();
					vbe.setStato(StatoFunzionalitaConWarning.toEnumConstant(stato));
					vbe.setControllo(ValidazioneBusteTipoControllo.toEnumConstant(controllo));
					vbe.setProfiloCollaborazione(StatoFunzionalita.toEnumConstant(profcoll));
					vbe.setManifestAttachments(StatoFunzionalita.toEnumConstant(validman));
					newConfigurazione.setValidazioneBuste(vbe);
				}

				if (newConfigurazione.getMessaggiDiagnostici() != null) {
					newConfigurazione.getMessaggiDiagnostici().setSeverita(Severita.toEnumConstant(severita));
					newConfigurazione.getMessaggiDiagnostici().setSeveritaLog4j(Severita.toEnumConstant(severita_log4j));
				} else {
					MessaggiDiagnostici md = new MessaggiDiagnostici();
					md.setSeverita(Severita.toEnumConstant(severita));
					md.setSeveritaLog4j(Severita.toEnumConstant(severita_log4j));
					newConfigurazione.setMessaggiDiagnostici(md);
				}

				if (newConfigurazione.getIntegrationManager() != null) {
					if (integman == null || !integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM))
						newConfigurazione.getIntegrationManager().setAutenticazione(integman);
					else
						newConfigurazione.getIntegrationManager().setAutenticazione(nomeintegman);
				} else {
					IntegrationManager im = new IntegrationManager();
					if (integman == null || !integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM))
						im.setAutenticazione(integman);
					else
						im.setAutenticazione(nomeintegman);
					newConfigurazione.setIntegrationManager(im);
				}

				if (newConfigurazione.getRisposte() != null) {
					newConfigurazione.getRisposte().setConnessione(TipoConnessioneRisposte.toEnumConstant(connessione));
				} else {
					Risposte rs = new Risposte();
					rs.setConnessione(TipoConnessioneRisposte.toEnumConstant(connessione));
					newConfigurazione.setRisposte(rs);
				}

				if (newConfigurazione.getIndirizzoRisposta() != null) {
					newConfigurazione.getIndirizzoRisposta().setUtilizzo(StatoFunzionalita.toEnumConstant(utilizzo));
				} else {
					IndirizzoRisposta it = new IndirizzoRisposta();
					it.setUtilizzo(StatoFunzionalita.toEnumConstant(utilizzo));
					newConfigurazione.setIndirizzoRisposta(it);
				}

				if (newConfigurazione.getAttachments() != null) {
					newConfigurazione.getAttachments().setGestioneManifest(StatoFunzionalita.toEnumConstant(gestman));
				} else {
					Attachments a = new Attachments();
					a.setGestioneManifest(StatoFunzionalita.toEnumConstant(gestman));
					newConfigurazione.setAttachments(a);
				}

				if (newConfigurazione.getTracciamento() != null) {
					newConfigurazione.getTracciamento().setBuste(StatoFunzionalita.toEnumConstant(registrazioneTracce));
					newConfigurazione.getTracciamento().setDump(StatoFunzionalita.toEnumConstant(dump));
				} else {
					Tracciamento t = new Tracciamento();
					t.setBuste(StatoFunzionalita.toEnumConstant(registrazioneTracce));
					t.setDump(StatoFunzionalita.toEnumConstant(dump));
					newConfigurazione.setTracciamento(t);
				}

				if (newConfigurazione.getValidazioneContenutiApplicativi() != null) {
					newConfigurazione.getValidazioneContenutiApplicativi().setStato(StatoFunzionalitaConWarning.toEnumConstant(xsd));
					newConfigurazione.getValidazioneContenutiApplicativi().setTipo(ValidazioneContenutiApplicativiTipo.toEnumConstant(tipoValidazione));
					if(applicaMTOM != null){
						if(ServletUtils.isCheckBoxEnabled(applicaMTOM))
							newConfigurazione.getValidazioneContenutiApplicativi().setAcceptMtomMessage(StatoFunzionalita.ABILITATO);
						else 
							newConfigurazione.getValidazioneContenutiApplicativi().setAcceptMtomMessage(StatoFunzionalita.DISABILITATO);
					} else 
						newConfigurazione.getValidazioneContenutiApplicativi().setAcceptMtomMessage(null);
				} else {
					ValidazioneContenutiApplicativi vca = new ValidazioneContenutiApplicativi();
					vca.setStato(StatoFunzionalitaConWarning.toEnumConstant(xsd));
					vca.setTipo(ValidazioneContenutiApplicativiTipo.toEnumConstant(tipoValidazione));
					vca.setAcceptMtomMessage(null);
					newConfigurazione.setValidazioneContenutiApplicativi(vca);
				}
				
				confCore.performUpdateOperation(userLogin, confHelper.smista(), newConfigurazione);

				Vector<DataElement> dati = new Vector<DataElement>();

				dati = confHelper.addConfigurazioneToDati(  user,  inoltromin, stato, controllo, severita, severita_log4j, integman, nomeintegman, profcoll, 
						connessione, utilizzo, validman, gestman, registrazioneTracce, dump, xsd, tipoValidazione, confPers, configurazione, dati, applicaMTOM);


				pd.setDati(dati);

				pd.setMessage("Configurazione Generale modificata con successo");

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeFinished(mapping,
						ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_GENERALE,
						ConfigurazioneCostanti.TIPO_OPERAZIONE_CONFIGURAZIONE_GENERALE);
			}

			if (inoltromin == null) {
				inoltromin = configurazione.getInoltroBusteNonRiscontrate().getCadenza();
				if(configurazione.getValidazioneBuste().getStato()!=null)
					stato = configurazione.getValidazioneBuste().getStato().toString();
				if(configurazione.getValidazioneBuste().getControllo()!=null)
					controllo = configurazione.getValidazioneBuste().getControllo().toString();
				if(configurazione.getMessaggiDiagnostici().getSeverita()!=null)
					severita = configurazione.getMessaggiDiagnostici().getSeverita().toString();
				if(configurazione.getMessaggiDiagnostici().getSeveritaLog4j()!=null)
					severita_log4j = configurazione.getMessaggiDiagnostici().getSeveritaLog4j().toString();
				integman = configurazione.getIntegrationManager().getAutenticazione();
				if (integman != null &&
						!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_SSL) &&
						!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC) &&
						!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC_SSL)) {
					nomeintegman = integman;
					integman = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM  ;
				}
				if(configurazione.getValidazioneBuste().getProfiloCollaborazione()!=null)
					profcoll = configurazione.getValidazioneBuste().getProfiloCollaborazione().toString();
				if(configurazione.getRisposte().getConnessione()!=null)
					connessione = configurazione.getRisposte().getConnessione().toString();
				if(configurazione.getIndirizzoRisposta().getUtilizzo()!=null)
					utilizzo = configurazione.getIndirizzoRisposta().getUtilizzo().toString();
				if(configurazione.getValidazioneBuste().getManifestAttachments()!=null)
					validman = configurazione.getValidazioneBuste().getManifestAttachments().toString();
				if(configurazione.getAttachments().getGestioneManifest()!=null)
					gestman = configurazione.getAttachments().getGestioneManifest().toString();
				if(configurazione.getTracciamento().getBuste()!=null)
					registrazioneTracce = configurazione.getTracciamento().getBuste().toString();
				if(configurazione.getTracciamento().getDump()!=null)
					dump = configurazione.getTracciamento().getDump().toString();
				if (configurazione.getValidazioneContenutiApplicativi() != null) {
					if(configurazione.getValidazioneContenutiApplicativi().getStato()!=null)
						xsd = configurazione.getValidazioneContenutiApplicativi().getStato().toString();
					if(configurazione.getValidazioneContenutiApplicativi().getTipo()!=null)
						tipoValidazione = configurazione.getValidazioneContenutiApplicativi().getTipo().toString();
					if(configurazione.getValidazioneContenutiApplicativi().getAcceptMtomMessage()!=null){
						if(StatoFunzionalita.ABILITATO.equals(configurazione.getValidazioneContenutiApplicativi().getAcceptMtomMessage())){
							applicaMTOM = Costanti.CHECK_BOX_ENABLED_ABILITATO;
						}
						else{
							applicaMTOM = Costanti.CHECK_BOX_DISABLED;
						}
					}
				}
			}

			// preparo i campi
			Vector<DataElement> dati = new Vector<DataElement>();

			dati.add(ServletUtils.getDataElementForEditModeFinished());

			dati = confHelper.addConfigurazioneToDati(  user,  inoltromin, stato, controllo, severita, severita_log4j, integman, nomeintegman, profcoll, 
					connessione, utilizzo, validman, gestman, registrazioneTracce, dump, xsd, tipoValidazione, confPers, configurazione, dati, applicaMTOM);


			pd.setDati(dati);

			String msg = request.getParameter(Costanti.PARAMETER_NAME_MSG_ERROR_EXPORT);
			if(msg!=null && !"".equals(msg)){
				pd.setMessage("Errore durante esportazione: "+msg);
			}
			
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeInProgress(mapping,
					ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_GENERALE,
					ConfigurazioneCostanti.TIPO_OPERAZIONE_CONFIGURAZIONE_GENERALE);
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_GENERALE, ConfigurazioneCostanti.TIPO_OPERAZIONE_CONFIGURAZIONE_GENERALE);
		}
	}


}
