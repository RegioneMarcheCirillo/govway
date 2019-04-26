/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.TrasformazioneRegola;
import org.openspcoop2.core.config.Trasformazioni;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.MessageType;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.ServletUtils;

/**
 * PorteApplicativeTrasformazioniList
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class PorteApplicativeTrasformazioniList extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		String userLogin = ServletUtils.getUserLoginFromSession(session);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);
 

		try {
			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String idPorta = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			String nomePorta = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_NOME);
			String cambiaPosizione = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_POSIZIONE);
			String idTrasformazioneS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE);
			
			// Preparo il menu
			porteApplicativeHelper.makeMenu();
			
			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			if(StringUtils.isNotEmpty(cambiaPosizione)) {
				long idTrasformazione = Long.parseLong(idTrasformazioneS);
				PortaApplicativa portaApplicativa = porteApplicativeCore.getPortaApplicativa(Long.parseLong(idPorta));
				
				Trasformazioni trasformazioni = portaApplicativa.getTrasformazioni();
				if(trasformazioni != null) {
					TrasformazioneRegola regolaToMove = null;
					for (TrasformazioneRegola reg : trasformazioni.getRegolaList()) {
						if(reg.getId().longValue() == idTrasformazione) {
							regolaToMove = reg;
							break;
						}
					}
					
					int posizioneAttuale = regolaToMove.getPosizione();
					int posizioneNuova = cambiaPosizione.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_POSIZIONE_SU) ? (posizioneAttuale - 1) : (posizioneAttuale + 1);
					
					TrasformazioneRegola regolaToSwitch = null;
					for (TrasformazioneRegola reg : trasformazioni.getRegolaList()) {
						if(reg.getPosizione() == posizioneNuova) {
							regolaToSwitch = reg;
							break;
						}
					}
					
					regolaToMove.setPosizione(posizioneNuova);
					regolaToSwitch.setPosizione(posizioneAttuale);
					
					porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), portaApplicativa);
					if(CostantiControlStation.VISUALIZZA_MESSAGGIO_CONFERMA_SPOSTAMENTO_REGOLA_TRASFORMAZIONE)
						pd.setMessage(CostantiControlStation.MESSAGGIO_CONFERMA_REGOLA_TRASFORMAZIONE_SPOSTATA_CORRETTAMENTE, MessageType.INFO);
				}
			}
	
			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
			
			int idLista = Liste.PORTE_APPLICATIVE_TRASFORMAZIONI; 
			
			ricerca = porteApplicativeHelper.checkSearchParameters(idLista, ricerca);
			
			
			List<TrasformazioneRegola> lista = porteApplicativeCore.porteAppTrasformazioniList(Long.parseLong(idPorta), ricerca);
			
			porteApplicativeHelper.preparePorteAppTrasformazioniRegolaList(nomePorta, ricerca, lista);
	
			ServletUtils.setSearchObjectIntoSession(session, ricerca);
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForward (mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI, ForwardParams.LIST());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI, ForwardParams.LIST());
		}  
	}
			
}