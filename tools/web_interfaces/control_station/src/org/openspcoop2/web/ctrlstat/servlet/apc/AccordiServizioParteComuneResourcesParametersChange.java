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


package org.openspcoop2.web.ctrlstat.servlet.apc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.registry.AccordoServizioParteComune;
import org.openspcoop2.core.registry.Resource;
import org.openspcoop2.core.registry.ResourceParameter;
import org.openspcoop2.core.registry.ResourceRequest;
import org.openspcoop2.core.registry.ResourceResponse;
import org.openspcoop2.core.registry.constants.ParameterType;
import org.openspcoop2.core.registry.constants.StatiAccordo;
import org.openspcoop2.core.registry.driver.IDAccordoFactory;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * AccordiServizioParteComuneResourcesParametersChange
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author: pintori $
 * @version $Rev: 12608 $, $Date: 2017-01-18 16:42:09 +0100(mer, 18 gen 2017) $
 * 
 */
public final class AccordiServizioParteComuneResourcesParametersChange extends Action {

	private String editMode = null;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		// Inizializzo GeneralData
		GeneralHelper generalHelper = new GeneralHelper(session);

		GeneralData gd = generalHelper.initGeneralData(request);

		String userLogin = ServletUtils.getUserLoginFromSession(session);

		IDAccordoFactory idAccordoFactory = IDAccordoFactory.getInstance();
		// Parametri relativi al tipo operazione
		TipoOperazione tipoOp = TipoOperazione.CHANGE;
		try {
			AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore();

			AccordiServizioParteComuneHelper apcHelper = new AccordiServizioParteComuneHelper(request, pd, session);

			this.editMode = apcHelper.getParameter(Costanti.DATA_ELEMENT_EDIT_MODE_NAME);
			String id = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID);
			int idInt = Integer.parseInt(id);
			String nomeRisorsa = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME);
			if (nomeRisorsa == null) {
				nomeRisorsa = "";
			}
			String statusS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_RESPONSE_STATUS);
			Integer status = null;
			try {
				if(statusS!=null)
					status = Integer.parseInt(statusS);
			} catch(Exception e) {}
			String isReq = request.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCE_REQUEST);
			boolean isRequest = ServletUtils.isCheckBoxEnabled(isReq);
			
			String descr = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_DESCRIZIONE);
			if (descr == null) {
				descr = "";
			}
			
			String tipoAccordo = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO);
			if("".equals(tipoAccordo))
				tipoAccordo = null;
			String nome = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_NOME);
			String tipo = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_TIPO);
			String tipoParametroS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_TIPO_PARAMETRO);
			ParameterType tipoParametro =  StringUtils.isNotEmpty(tipoParametroS) ? ParameterType.toEnumConstant(tipoParametroS) : null;
			String requiredS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_REQUIRED);
			boolean required = ServletUtils.isCheckBoxEnabled(requiredS);
			
			String idPar = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_PARAMETER_ID);
			int idParInt = Integer.parseInt(idPar);
			
			// Preparo il menu
			apcHelper.makeMenu();

			// Prendo il nome
			AccordoServizioParteComune as = apcCore.getAccordoServizio(new Long(idInt));
			String uriAS = idAccordoFactory.getUriFromAccordo(as);

			Resource risorsa = null;
			for (int j = 0; j < as.sizeResourceList(); j++) {
				risorsa = as.getResource(j);
				if (nomeRisorsa.equals(risorsa.getNome())) {
					break;
				}
			}
			
			Long idResponse = null;
			Long idResource = null;
			ResourceRequest resourceRequest = null;
			ResourceResponse resourceResponse = null;
			ResourceParameter resourceParameterOLD = null;
			List<ResourceParameter> parameterList = null;
			if(isRequest) {
				resourceRequest = risorsa.getRequest();
				idResource = risorsa.getId();
				if(resourceRequest != null)
					parameterList = resourceRequest.getParameterList();
			} else {
				if(risorsa.getResponseList() != null) {
					for (int i = 0; i < risorsa.getResponseList().size(); i++) {
						resourceResponse = risorsa.getResponse(i);
						if (resourceResponse.getStatus() == status) {
							idResponse = resourceResponse.getId();
							break;
						}
					}
					if(resourceResponse != null)
						parameterList = resourceResponse.getParameterList();
				}
			}
			
			if(parameterList != null && parameterList.size() > 0) {
				for (ResourceParameter resourceParameter : parameterList) {
					if(resourceParameter.getId().intValue() == idParInt) {
//					if(resourceParameter.getNome().equals(nome) && resourceParameter.getParameterType().equals(tipoParametro)) {
						resourceParameterOLD = resourceParameter;
						break;
					}
				}
			}
			
			String oldNome = resourceParameterOLD.getNome();
			ParameterType oldTipoParametro = resourceParameterOLD.getParameterType();
			
			List<Parameter> listaLinkPageDataTitle = new ArrayList<Parameter>();
			
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneUtilities.getTerminologiaAccordoServizio(tipoAccordo),null));
			listaLinkPageDataTitle.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, 
					AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_LIST+"?"+
							AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getName()+"="+
							AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getValue()));
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_RISORSE + " di " + uriAS, 
								AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_LIST,
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID, id),
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO, tipoAccordo),
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_NOME, as.getNome())));
			String labelOwner = nomeRisorsa;
			if(!isRequest) {
				listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_RISPOSTE + " di " + nomeRisorsa, 
						AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_RISPOSTE_LIST+"?"+
								AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID+"="+id+"&"+
								AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME+"="+nomeRisorsa+"&"+
								AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getName()+"="+
								AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getValue()));
				labelOwner = statusS;
			} 
			
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_PARAMETERS + " di " + labelOwner, 
					AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_PARAMETERS_LIST,
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID, id),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO, tipoAccordo),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_NOME, uriAS),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCE_REQUEST, isRequest+""),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_RESPONSE_STATUS, statusS),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME, nomeRisorsa)));
			
			listaLinkPageDataTitle.add(new Parameter(nome, null));

			// Se idhid = null, devo visualizzare la pagina per la
			// modifica dati
		
			if(ServletUtils.isEditModeInProgress(this.editMode)){

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd,listaLinkPageDataTitle); 

				// Prendo i dati dell'accordo
				if(nome == null){
					nome = resourceParameterOLD.getNome();
					descr = resourceParameterOLD.getDescrizione();
					required = resourceParameterOLD.isRequired();
					tipoParametro = oldTipoParametro;
					tipo = resourceParameterOLD.getTipo();
				}

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = apcHelper.addAccordiResourceParameterToDati(tipoOp, dati, id, as.getStatoPackage(),tipoAccordo,
						 nomeRisorsa, isRequest, statusS, idParInt, nome, descr,  tipoParametro, tipo, required);

				pd.setDati(dati);

				if(apcCore.isShowGestioneWorkflowStatoDocumenti() && StatiAccordo.finale.toString().equals(as.getStatoPackage())){
					pd.disableEditMode();
				}

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_PARAMETERS, ForwardParams.CHANGE());

			}
			
			// Controlli sui campi immessi
			boolean isOk = apcHelper.accordiResourceParameterCheckData(tipoOp, id, nomeRisorsa, isRequest, statusS,  nome, descr, tipoParametro, tipo, required, idResource,idResponse,
					oldTipoParametro,oldNome);

			if (!isOk) {

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd,listaLinkPageDataTitle); 

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = apcHelper.addAccordiResourceParameterToDati(tipoOp, dati, id, as.getStatoPackage(),tipoAccordo,
						 nomeRisorsa, isRequest, statusS, idParInt, nome, descr,  tipoParametro, tipo, required);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_PARAMETERS, ForwardParams.CHANGE());
			}

			// Modifico i dati del parameter nel db
			ResourceParameter newParameter = new ResourceParameter();
			newParameter.setParameterType(tipoParametro);
			newParameter.setNome(nome);
			newParameter.setTipo(tipo);
			newParameter.setDescrizione(descr);
			newParameter.setRequired(required);
			
			int idx = -1;			
			if(parameterList != null && parameterList.size() > 0) {
				for (int i = 0; i < parameterList.size(); i++) {
					ResourceParameter resourceParameter = parameterList.get(i);
					if(resourceParameter.getId().intValue() == idParInt) {
//					if(resourceParameter.getNome().equals(nome) && resourceParameter.getParameterType().equals(tipoParametro)) {
						idx = i;
						break;
					}
				}
				if(idx > -1) {
					parameterList.remove(idx);
					parameterList.add(idx, newParameter);
				}
			}
			
			// effettuo le operazioni
			apcCore.performUpdateOperation(userLogin, apcHelper.smista(), as);

			// Devo rileggere l'accordo dal db, perche' altrimenti
			// manca l'id dei nuovi port-type
			as = apcCore.getAccordoServizio(new Long(idInt));

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			risorsa = null;
			for (int j = 0; j < as.sizeResourceList(); j++) {
				risorsa = as.getResource(j);
				if (nomeRisorsa.equals(risorsa.getNome())) {
					break;
				}
			}
			
			idResponse = null;
			idResource = null;
			resourceRequest = null;
			resourceResponse = null;
			if(isRequest) {
				resourceRequest = risorsa.getRequest();
				idResource = risorsa.getId();
			} else {
				if(risorsa.getResponseList() != null) {
					for (int i = 0; i < risorsa.getResponseList().size(); i++) {
						resourceResponse = risorsa.getResponse(i);
						if (resourceResponse.getStatus() == status) {
							idResponse = resourceResponse.getId();
							break;
						}
					}
				}
			}
			

			List<ResourceParameter> lista = apcCore.accordiResourceParametersList(idResource, isRequest, idResponse, ricerca);

			apcHelper.prepareAccordiResourcesParametersList(id, as, lista, ricerca, tipoAccordo, isRequest, nomeRisorsa, idResource, resourceRequest, resourceResponse);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeFinished(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_PARAMETERS, ForwardParams.CHANGE());

		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_PARAMETERS, ForwardParams.CHANGE());
		} 
	}
}