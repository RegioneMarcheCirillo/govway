/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
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
package org.openspcoop2.web.ctrlstat.servlet.pa;

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
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.pdd.core.jmx.JMXUtils;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.config.ConfigurazioneCore;
import org.openspcoop2.web.ctrlstat.servlet.config.ConfigurazioneCostanti;
import org.openspcoop2.web.ctrlstat.servlet.connettori.ConnettoriCore;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.DataElementType;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/***
 * 
 * PorteApplicativeVerificaConnettore
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PorteApplicativeVerificaConnettore extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		
		// prelevo il flag che mi dice da quale pagina ho acceduto la sezione delle porte delegate
		Integer parentPA = ServletUtils.getIntegerAttributeFromSession(PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT, session);
		if(parentPA == null) parentPA = PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE;

		try {

			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String id = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			int idInt = Integer.parseInt(id);
			String idsogg = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO);
			String idAsps = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS);
			if(idAsps == null) 
				idAsps = "";

			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			ConfigurazioneCore confCore = new ConfigurazioneCore(porteApplicativeCore);
			ConnettoriCore connettoriCore = new ConnettoriCore(porteApplicativeCore);

			String tmpIdConnettore = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_VERIFICA_CONNETTORE_ID);
			long idConnettore = Long.valueOf(tmpIdConnettore);
			
			String tmpAccessoDaGruppi = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_VERIFICA_CONNETTORE_ACCESSO_DA_GRUPPI);
			boolean accessoDaGruppi = "true".equalsIgnoreCase(tmpAccessoDaGruppi);
			
			String tmpConnettoreRegistro = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_VERIFICA_CONNETTORE_REGISTRO);
			boolean connettoreRegistro = "true".equalsIgnoreCase(tmpConnettoreRegistro);

			String alias = porteApplicativeHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_SISTEMA_NODO_CLUSTER);
						
			// Prendo la lista di aliases
			List<String> aliases = confCore.getJmxPdD_aliases();
			if(aliases==null || aliases.size()<=0){
				throw new Exception("Pagina non prevista, la sezione configurazione non permette di accedere a questa pagina, se la configurazione non e' corretta");
			}
			
			
			org.openspcoop2.core.config.Connettore connettore = null;
			if(connettoreRegistro) {
				connettore = connettoriCore.getConnettoreRegistro(idConnettore).mappingIntoConnettoreConfigurazione();
			}
			else {
				connettore = connettoriCore.getConnettoreConfig(idConnettore);
			}
			String labelConnettore = porteApplicativeHelper.getLabelConnettore(connettore);
			
			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(idInt);
			String idporta = pa.getNome();

			
			List<Parameter> lstParam = porteApplicativeHelper.getTitoloPA(parentPA, idsogg, idAsps);
			
			String labelPerPorta = null;
			if(parentPA!=null && (parentPA.intValue() == PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_CONFIGURAZIONE)) {
				if(!accessoDaGruppi) {
					lstParam.remove(lstParam.size()-1);
				}
				labelPerPorta = porteApplicativeCore.getLabelRegolaMappingErogazionePortaApplicativa(
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_VERIFICA_CONNETTORE_CONFIGURAZIONE_CONFIG_DI,
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_VERIFICA_CONNETTORE_CONFIGURAZIONE,
						pa);
			}
			else {
				labelPerPorta = PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_VERIFICA_CONNETTORE_CONFIGURAZIONE_CONFIG_DI+idporta;
			}
			
			if(!accessoDaGruppi) {
				if(labelPerPorta.contains(CostantiControlStation.LABEL_DEL_GRUPPO)) {
					labelPerPorta = labelPerPorta.substring(0, labelPerPorta.indexOf(CostantiControlStation.LABEL_DEL_GRUPPO));
				}
			}
			
			lstParam.add(new Parameter(labelPerPorta,  null));

			
			
			ServletUtils.setPageDataTitle(pd, lstParam);
			
			// preparo i campi
			Vector<DataElement> dati = new Vector<DataElement>();
			dati.addElement(ServletUtils.getDataElementForEditModeFinished());

			DataElement deTestConnettivita = new DataElement();
			deTestConnettivita.setType(DataElementType.TITLE);
			deTestConnettivita.setLabel(PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_VERIFICA_CONNETTORE_TITLE);
			dati.add(deTestConnettivita);
			
			if(aliases.size()==1 || alias!=null) {

				porteApplicativeHelper.addDescrizioneVerificaConnettoreToDati(dati, labelConnettore, connettore);
				
				if (!porteApplicativeHelper.isEditModeInProgress()) {
				
					List<String> aliases_for_check = new ArrayList<>();
					if(aliases.size()==1) {
						aliases_for_check.add(aliases.get(0));
					}
					else if(CostantiControlStation.LABEL_VERIFICA_CONNETTORE_TUTTI_I_NODI.equals(alias)) {
						aliases_for_check.addAll(aliases);
					}
					else {
						aliases_for_check.add(alias);
					}
									
					boolean rilevatoErrore = false;
					String messagePerOperazioneEffettuata = "";
					int index = 0;
					for (String aliasForVerificaConnettore : aliases_for_check) {
						
						String risorsa = null;
						if(connettoreRegistro) {
							risorsa = confCore.getJmxPdD_configurazioneSistema_nomeRisorsaAccessoRegistroServizi(aliasForVerificaConnettore);
						}
						else {
							risorsa = confCore.getJmxPdD_configurazioneSistema_nomeRisorsaConfigurazionePdD(aliasForVerificaConnettore);
						}
						
						StringBuffer bfExternal = new StringBuffer();
						String descrizione = confCore.getJmxPdD_descrizione(aliasForVerificaConnettore);
						if(aliases.size()>1) {
							if(index>0) {
								bfExternal.append("<BR/>");
							}
							bfExternal.append(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_SISTEMA_NODO_CLUSTER).append(" ").append(descrizione).append("<BR/>");
						}						
						try{
							String stato = confCore.invokeJMXMethod(confCore.getGestoreRisorseJMX(aliasForVerificaConnettore), aliasForVerificaConnettore, confCore.getJmxPdD_configurazioneSistema_type(aliasForVerificaConnettore),
									risorsa, 
									confCore.getJmxPdD_configurazioneSistema_nomeMetodo_checkConnettoreById(aliasForVerificaConnettore), 
									idConnettore+"");
							if(JMXUtils.MSG_OPERAZIONE_EFFETTUATA_SUCCESSO.equals(stato)){
								bfExternal.append(CostantiControlStation.LABEL_CONFIGURAZIONE_VERIFICA_CONNETTORE_EFFETTUATO_CON_SUCCESSO);
							}
							else{
								rilevatoErrore = true;
								bfExternal.append(CostantiControlStation.LABEL_CONFIGURAZIONE_VERIFICA_CONNETTORE_FALLITA);
								if(stato.startsWith(JMXUtils.MSG_OPERAZIONE_NON_EFFETTUATA)) {
									bfExternal.append(stato.substring(JMXUtils.MSG_OPERAZIONE_NON_EFFETTUATA.length()));
								}
								else {
									bfExternal.append(stato);
								}
							}
						}catch(Exception e){
							ControlStationCore.logError("Errore durante la verifica del connettore (jmxResource '"+risorsa+"') (node:"+aliasForVerificaConnettore+"): "+e.getMessage(),e);
							rilevatoErrore = true;
							String stato = e.getMessage();
							bfExternal.append(CostantiControlStation.LABEL_CONFIGURAZIONE_VERIFICA_CONNETTORE_FALLITA);
							if(stato.startsWith(JMXUtils.MSG_OPERAZIONE_NON_EFFETTUATA)) {
								bfExternal.append(stato.substring(JMXUtils.MSG_OPERAZIONE_NON_EFFETTUATA.length()));
							}
							else {
								bfExternal.append(stato);
							}
						}
	
						if(messagePerOperazioneEffettuata.length()>0){
							messagePerOperazioneEffettuata+= "<BR/>";
						}
						messagePerOperazioneEffettuata+= bfExternal.toString();
						
						index++;
					}
					if(messagePerOperazioneEffettuata!=null){
						if(rilevatoErrore)
							pd.setMessage(messagePerOperazioneEffettuata);
						else 
							pd.setMessage(messagePerOperazioneEffettuata,Costanti.MESSAGE_TYPE_INFO);
					}
	
					pd.disableEditMode();
					
				}
				
			}
			else {
				porteApplicativeHelper.addVerificaConnettoreSceltaAlias(aliases, dati);			
			}
			
			pd.setLabelBottoneInvia(CostantiControlStation.LABEL_CONFIGURAZIONE_VERIFICA_CONNETTORE_BOTTONE);
			
			porteApplicativeHelper.addVerificaConnettoreHidden(dati,
					id, idsogg, idAsps, null,
					idConnettore, accessoDaGruppi, connettoreRegistro);
			
			dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.OTHER,id, idsogg, null, idAsps, dati);

			pd.setDati(dati);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeInProgress(mapping,	PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_VERIFICA_CONNETTORE,	ForwardParams.OTHER(""));
			
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_VERIFICA_CONNETTORE, ForwardParams.OTHER(""));
		}
	}

}
