/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneSoggetti;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneSoggetto;
import org.openspcoop2.core.registry.constants.CredenzialeTipo;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.aps.AccordiServizioParteSpecificaCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * porteAppServizioApplicativoAdd
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author: apoli $
 * @version $Rev: 13574 $, $Date: 2018-01-26 12:24:34 +0100 (Fri, 26 Jan 2018) $
 * 
 */
public final class PorteApplicativeSoggettoAdd extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		String userLogin = ServletUtils.getUserLoginFromSession(session);	
		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		Integer parentPA = ServletUtils.getIntegerAttributeFromSession(PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT, session);
		if(parentPA == null) parentPA = PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE;

		try {
			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String idPorta = request.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			int idInt = Integer.parseInt(idPorta);
			String idsogg = request.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO);
			int soggInt = Integer.parseInt(idsogg);
			
			String idSoggettoToAdd = request.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SOGGETTO);
			String idAsps = request.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS);
			if(idAsps == null) 
				idAsps = "";

			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			SoggettiCore soggettiCore = new SoggettiCore(porteApplicativeCore);
			AccordiServizioParteSpecificaCore apsCore = new AccordiServizioParteSpecificaCore(porteApplicativeCore);

			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			// Prendo nome, tipo e pdd del soggetto
			String tmpTitle = null;
			String protocollo = null;
			if(porteApplicativeCore.isRegistroServiziLocale()){
				org.openspcoop2.core.registry.Soggetto soggetto = soggettiCore.getSoggettoRegistro(soggInt);
				protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(soggetto.getTipo());
				tmpTitle = porteApplicativeHelper.getLabelNomeSoggetto(protocollo, soggetto.getTipo() , soggetto.getNome());
			}
			else{
				org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(soggInt);
				protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(soggetto.getTipo());
				tmpTitle = porteApplicativeHelper.getLabelNomeSoggetto(protocollo, soggetto.getTipo() , soggetto.getNome());
			}

			//decodifica soggetto scelto
			String tipoSoggettoScelto = null;
			String nomeSoggettoScelto = null;
			if(idSoggettoToAdd != null) {
				int soggettoToAddInt = Integer.parseInt(idSoggettoToAdd);
				
				if(porteApplicativeCore.isRegistroServiziLocale()){
					org.openspcoop2.core.registry.Soggetto soggetto = soggettiCore.getSoggettoRegistro(soggettoToAddInt);
					tipoSoggettoScelto = soggetto.getTipo();
					nomeSoggettoScelto = soggetto.getNome();
				}
				else{
					org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(soggettoToAddInt);
					tipoSoggettoScelto = soggetto.getTipo();
					nomeSoggettoScelto = soggetto.getNome();
				}
			}
			
			// Prendo nome della porta applicativa
			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(idInt);
			String nomePorta = pa.getNome();
			CredenzialeTipo tipoAutenticazione = CredenzialeTipo.toEnumConstant(pa.getAutenticazione());
			
			List<String> tipiSoggettiGestitiProtocollo = soggettiCore.getTipiSoggettiGestitiProtocollo(protocollo);
			
			// lista soggetti disponibili
			String[] soggettiList = null;
			String[] soggettiListLabel = null;
			
			// calcolo soggetti compatibili con tipi protocollo supportati dalla pa e credenziali indicate
			List<org.openspcoop2.core.registry.Soggetto> list = null;
			if(apsCore.isVisioneOggettiGlobale(userLogin)){
				list = soggettiCore.getSoggettiFromTipoAutenticazione(tipiSoggettiGestitiProtocollo, null, tipoAutenticazione);
			}else{
				list = soggettiCore.getSoggettiFromTipoAutenticazione(tipiSoggettiGestitiProtocollo, userLogin, tipoAutenticazione);
			}

			PortaApplicativaAutorizzazioneSoggetti soggetti = pa.getSoggetti(); 
			List<PortaApplicativaAutorizzazioneSoggetto> soggettoList = soggetti != null ? soggetti.getSoggettoList() : new ArrayList<PortaApplicativaAutorizzazioneSoggetto>();
			if (list.size() > 0) {
				List<String> soggettiListTmp = new ArrayList<String>();
				List<String> soggettiListLabelTmp = new ArrayList<String>();
				for (org.openspcoop2.core.registry.Soggetto soggetto : list) {
					// scartare i soggetti gia associati
					boolean found = false;
					
					for (PortaApplicativaAutorizzazioneSoggetto soggettoAssociatoPa : soggettoList) { 
						if(soggettoAssociatoPa.getTipo().equals(soggetto.getTipo()) && soggettoAssociatoPa.getNome().equals(soggetto.getNome())) {
							found = true;
							break;
						}
					}
					
					if(!found){
						soggettiListTmp.add(soggetto.getId().toString());
						soggettiListLabelTmp.add(porteApplicativeHelper.getLabelNomeSoggetto(protocollo, soggetto.getTipo() , soggetto.getNome()));
					}
				}
				soggettiList = soggettiListTmp.toArray(new String[1]);
				soggettiListLabel = soggettiListLabelTmp.toArray(new String[1]);
			}
			
			List<Parameter> lstParam = porteApplicativeHelper.getTitoloPA(parentPA, idsogg, idAsps, tmpTitle);
			
			lstParam.add(new Parameter(PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTO_DI + nomePorta,
					PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_LIST,
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, idPorta),
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO, idsogg)
					));
			lstParam.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_AGGIUNGI, null));
			
			// Se servizioApplicativohid = null, devo visualizzare la pagina per
			// l'inserimento dati
			if (porteApplicativeHelper.isEditModeInProgress()) {
				
				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, lstParam);

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				dati = porteApplicativeHelper.addPorteSoggettoToDati(TipoOperazione.ADD, dati, soggettiListLabel, soggettiList, idSoggettoToAdd, soggettoList.size(), true);

				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.ADD, idPorta, idsogg, idPorta, idAsps,dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO,
						ForwardParams.ADD());
			}

			// Controlli sui campi immessi
			boolean isOk = porteApplicativeHelper.porteAppSoggettoCheckData(TipoOperazione.ADD);
			if (!isOk) {
				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, lstParam);

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = porteApplicativeHelper.addPorteSoggettoToDati(TipoOperazione.ADD, dati, soggettiListLabel, soggettiList, idSoggettoToAdd, soggettoList.size(), true);

				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.ADD, idPorta, idsogg, idPorta, idAsps, dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO, 
						ForwardParams.ADD());
			}

			// Inserisco il servizioApplicativo nel db
			PortaApplicativaAutorizzazioneSoggetto paSoggetto = new PortaApplicativaAutorizzazioneSoggetto();
			paSoggetto.setTipo(tipoSoggettoScelto);
			paSoggetto.setNome(nomeSoggettoScelto);
				
			if(soggetti != null)
				soggetti.addSoggetto(paSoggetto);
			else {
				soggetti = new PortaApplicativaAutorizzazioneSoggetti();
				soggetti.addSoggetto(paSoggetto);
				pa.setSoggetti(soggetti);
			}

			porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), pa);

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			int idLista = Liste.PORTE_APPLICATIVE_SOGGETTO;

			ricerca = porteApplicativeHelper.checkSearchParameters(idLista, ricerca);

			List<PortaApplicativaAutorizzazioneSoggetto> lista = porteApplicativeCore.porteAppSoggettoList(Integer.parseInt(idPorta), ricerca);

			porteApplicativeHelper.preparePorteAppSoggettoList(nomePorta, ricerca, lista);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO, 
					ForwardParams.ADD());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO,
					ForwardParams.ADD());
		} 
	}
}