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


package org.openspcoop2.web.ctrlstat.servlet.pd;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.config.CorrelazioneApplicativa;
import org.openspcoop2.core.config.CorrelazioneApplicativaElemento;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.config.constants.CorrelazioneApplicativaGestioneIdentificazioneFallita;
import org.openspcoop2.core.config.constants.CorrelazioneApplicativaRichiestaIdentificazione;
import org.openspcoop2.core.config.constants.StatoFunzionalita;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCostanti;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * porteDelegateCorrAppAdd
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class PorteDelegateCorrelazioneApplicativaRequestAdd extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);


		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		// prelevo il flag che mi dice da quale pagina ho acceduto la sezione delle porte delegate
		Boolean useIdSogg= ServletUtils.getBooleanAttributeFromSession(PorteDelegateCostanti.ATTRIBUTO_PORTE_DELEGATE_USA_ID_SOGGETTO, session);


		try {
			PorteDelegateHelper porteDelegateHelper = new PorteDelegateHelper(request, pd, session);

			String id = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID);
			int idInt = Integer.parseInt(id);
			String idsogg = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO);
			int soggInt = Integer.parseInt(idsogg);
			String elemxml = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ELEMENTO_XML);
			String mode = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_MODE);
			if (mode == null) {
				mode = PorteDelegateCostanti.DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_CONTENT_BASED;
			}
			String pattern = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_PATTERN);
			String gif = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_GESTIONE_IDENTIFICAZIONE_FALLITA);
			String riusoIdentificativoMessaggio = request.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_RIUSO_ID_MESSAGGIO);

			PorteDelegateCore porteDelegateCore = new PorteDelegateCore( );
			SoggettiCore soggettiCore = new SoggettiCore(porteDelegateCore);

			// Preparo il menu
			porteDelegateHelper.makeMenu();

			// Prendo nome e tipo del soggetto
			String tmpTitle = null;
			if(porteDelegateCore.isRegistroServiziLocale()){
				org.openspcoop2.core.registry.Soggetto soggetto = soggettiCore.getSoggettoRegistro(soggInt);
				tmpTitle = soggetto.getTipo() + "/" + soggetto.getNome();
			}
			else{
				org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(soggInt);
				tmpTitle = soggetto.getTipo() + "/" + soggetto.getNome();
			}

			// Prendo il nome della porta delegata
			PortaDelegata pde = porteDelegateCore.getPortaDelegata(idInt);
			String nome = pde.getNome();

			// Se idhid = null, devo visualizzare la pagina per l'inserimento
			// dati
			if (ServletUtils.isEditModeInProgress(request)) {

				if(useIdSogg){
					// setto la barra del titolo
					ServletUtils.setPageDataTitle(pd, 
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_SOGGETTI, null),
							new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST),
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_PORTE_DELEGATE_DI + tmpTitle, 
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_LIST,
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg)
									),
							 new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_DI + nome,
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA,
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO, idsogg),
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID, id),
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME, nome)),
									new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI + nome, 
											PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST,
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID,id),
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg),
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME,nome)
											),
											new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_AGGIUNGI, null)
							);
				}else{
					// setto la barra del titolo
					ServletUtils.setPageDataTitle(pd, 
							new Parameter(PorteDelegateCostanti.LABEL_PORTE_DELEGATE, null),
							new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_LIST),
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_DI + nome,
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA,
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO, idsogg),
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID, id),
							new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME, nome)),
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI + nome, 
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST,
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID,id),
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg),
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME,nome)
									),
									new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_AGGIUNGI, null)
							);
				}

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = porteDelegateHelper.addHiddenFieldsToDati(TipoOperazione.ADD, id, idsogg, null, dati);

				dati = porteDelegateHelper. addPorteDelegateCorrelazioneApplicativaRequestToDati(TipoOperazione.ADD , pd,  
						elemxml, mode, pattern, gif, riusoIdentificativoMessaggio, dati, null);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST, ForwardParams.ADD());

			}

			// Controlli sui campi immessi
			boolean isOk = porteDelegateHelper.correlazioneApplicativaRichiestaCheckData(TipoOperazione.ADD,true);
			if (!isOk) {

				if(useIdSogg){
					// setto la barra del titolo
					ServletUtils.setPageDataTitle(pd, 
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_SOGGETTI, null),
							new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST),
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_PORTE_DELEGATE_DI + tmpTitle, 
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_LIST,
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg)
									),
							 new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_DI + nome,
										PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA,
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO, idsogg),
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID, id),
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME, nome)),
									new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI + nome, 
											PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST,
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID,id),
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg),
											new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME,nome)
											),
											new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_AGGIUNGI, null)
							);
				}else{
					// setto la barra del titolo
					ServletUtils.setPageDataTitle(pd, 
							new Parameter(PorteDelegateCostanti.LABEL_PORTE_DELEGATE, null),
							new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_LIST),
							 new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_DI + nome,
										PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA,
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO, idsogg),
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID, id),
								new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME, nome)),
							new Parameter(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI + nome, 
									PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST,
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID,id),
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO,idsogg),
									new Parameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_NOME,nome)
									),
									new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_AGGIUNGI, null)
							);
				}
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = porteDelegateHelper.addHiddenFieldsToDati(TipoOperazione.ADD, id, idsogg, null, dati);

				dati = porteDelegateHelper.	addPorteDelegateCorrelazioneApplicativaRequestToDati(TipoOperazione.ADD, pd, 
						elemxml, mode, pattern, gif, riusoIdentificativoMessaggio, dati, null);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST,
						ForwardParams.ADD());
			}

			// Inserisco la correlazione applicativa nel db
			CorrelazioneApplicativaElemento cae = new CorrelazioneApplicativaElemento();
			cae.setNome(elemxml);
			cae.setIdentificazione(CorrelazioneApplicativaRichiestaIdentificazione.toEnumConstant(mode));
			if (mode.equals(PorteDelegateCostanti.DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_URL_BASED) || 
					mode.equals(PorteDelegateCostanti.DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_CONTENT_BASED)) {
				cae.setPattern(pattern);
			}

			if(!PorteDelegateCostanti.DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_DISABILITATO.equals(mode)){
				cae.setIdentificazioneFallita(CorrelazioneApplicativaGestioneIdentificazioneFallita.toEnumConstant(gif));
				cae.setRiusoIdentificativo(StatoFunzionalita.toEnumConstant(riusoIdentificativoMessaggio));
			}

			CorrelazioneApplicativa ca = pde.getCorrelazioneApplicativa();
			if (ca == null) {
				ca = new CorrelazioneApplicativa();
			}
			ca.addElemento(cae);
			pde.setCorrelazioneApplicativa(ca);

			String userLogin = ServletUtils.getUserLoginFromSession(session);

			porteDelegateCore.performUpdateOperation(userLogin, porteDelegateHelper.smista(), pde);

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			List<CorrelazioneApplicativaElemento> lista = porteDelegateCore.porteDelegateCorrelazioneApplicativaList(idInt, ricerca);

			porteDelegateHelper.preparePorteDelegateCorrAppList(nome, ricerca, lista);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST,
					ForwardParams.ADD());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST, 
					ForwardParams.ADD());
		}  
	}


}
