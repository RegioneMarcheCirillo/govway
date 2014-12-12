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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openspcoop2.core.commons.ISearch;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.AccessoRegistro;
import org.openspcoop2.core.config.AccessoRegistroRegistro;
import org.openspcoop2.core.config.Configurazione;
import org.openspcoop2.core.config.MessaggiDiagnostici;
import org.openspcoop2.core.config.OpenspcoopAppender;
import org.openspcoop2.core.config.OpenspcoopSorgenteDati;
import org.openspcoop2.core.config.Property;
import org.openspcoop2.core.config.Route;
import org.openspcoop2.core.config.RoutingTable;
import org.openspcoop2.core.config.RoutingTableDestinazione;
import org.openspcoop2.core.config.SystemProperties;
import org.openspcoop2.core.config.Tracciamento;
import org.openspcoop2.core.config.constants.CostantiConfigurazione;
import org.openspcoop2.core.registry.constants.CostantiRegistroServizi;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziException;
import org.openspcoop2.pdd.logger.LogLevels;
import org.openspcoop2.utils.regexp.RegularExpressionEngine;
import org.openspcoop2.web.ctrlstat.servlet.ConsoleHelper;
import org.openspcoop2.web.ctrlstat.servlet.pd.PorteDelegateCostanti;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.DataElementType;
import org.openspcoop2.web.lib.mvc.GeneralLink;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;
import org.openspcoop2.web.lib.users.dao.InterfaceType;
import org.openspcoop2.web.lib.users.dao.User;

/**
 * ConfigurazioneHelper
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConfigurazioneHelper extends ConsoleHelper{

	//	private ConnettoriHelper connettoriHelper = null;
	public ConfigurazioneHelper(HttpServletRequest request, PageData pd, 
			HttpSession session) throws Exception {
		super(request, pd,  session);
		//		this.connettoriHelper = new ConnettoriHelper(request, pd, session);
	}

	public Vector<DataElement>   addIdProprietaToDati(TipoOperazione tipoOp, String idprop, Vector<DataElement> dati) {
		DataElement de = new DataElement();

		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA);
		de.setValue(idprop);
		de.setType(DataElementType.HIDDEN);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA);
		dati.addElement(de);

		return dati;
	}

	public Vector<DataElement> addTipoTracciamentoAppenderToDati(TipoOperazione tipoOp, String tipo,
			Vector<DataElement> dati,String idAppenderDati, int dimensioneAppenderDati) {
		DataElement de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setValue(tipo);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		if(tipoOp.equals(TipoOperazione.CHANGE)){
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);
			de = new DataElement();
			de.setType(DataElementType.LINK);
			de.setUrl(
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_PROPERTIES_LIST ,
					new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, idAppenderDati));
			if (contaListe)
				ServletUtils.setDataElementVisualizzaLabel(de, (long) dimensioneAppenderDati);
			else
				ServletUtils.setDataElementVisualizzaLabel(de);
			dati.addElement(de);
		}

		return dati;
	}

	public Vector<DataElement> addConfigurazioneRegistroToDati(String statocache,
			String dimensionecache, String algoritmocache, String idlecache,
			String lifecache, Vector<DataElement> dati) {
		DataElement de = new DataElement();


		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_REGISTRI_LIST);
		de.setValue(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_REGISTRI);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_CACHE);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoStatoCache = {
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_STATO_CACHE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO_CACHE);
		de.setValues(tipoStatoCache);
		de.setSelected(statocache);
		//			de.setOnChange("CambiaStatoCache()");
		de.setPostBack(true);
		dati.addElement(de);

		if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO)) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_DIMENSIONE_CACHE);
			de.setValue(dimensionecache);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DIMENSIONE_CACHE);
			de.setSize( getSize());
			dati.addElement(de);

			String[] tipoAlg = {
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_LRU,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_MRU
			};
			String[] labelsAlg = {
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_LRU,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_MRU
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE);
			de.setLabels(labelsAlg);
			de.setValues(tipoAlg);
			de.setSelected(algoritmocache);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_IDLE_CACHE);
			de.setValue(idlecache);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_IDLE_CACHE);
			de.setSize( getSize());
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LIFE_CACHE);
			de.setValue(lifecache);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIFE_CACHE);
			de.setSize( getSize());
			dati.addElement(de);
		}

		return dati;
	}


	public Vector<DataElement> addTipoDiagnosticaAppenderToDati(TipoOperazione tipoOp, String tipo,
			Vector<DataElement> dati,String idAppenderDati, int dimensioneAppenderDati) {
		DataElement de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setValue(tipo);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		if(tipoOp.equals(TipoOperazione.CHANGE)){
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);
			de = new DataElement();
			de.setType(DataElementType.LINK);
			de.setUrl(
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_PROPERTIES_LIST,
					new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, idAppenderDati));
			if (contaListe)
				ServletUtils.setDataElementVisualizzaLabel(de, (long) dimensioneAppenderDati);
			else
				ServletUtils.setDataElementVisualizzaLabel(de);
			dati.addElement(de);
		}

		return dati;
	}

	public Vector<DataElement>   addDiagnosticaDatasourceToDati(TipoOperazione tipoOp, String nome, String nomeJndi,
			String tipoDatabase, String[] tipoDbList, Vector<DataElement> dati, String idSorgenteDati, int dimensioneSorgenteDati) {
		DataElement de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME);
		de.setValue(nome);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
		de.setValue(nomeJndi);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);
		de.setValues(tipoDbList);
		if(tipoDatabase != null){
			de.setSelected(tipoDatabase);
		}
		de.setRequired(true);
		dati.addElement(de);

		if(tipoOp .equals(TipoOperazione.CHANGE)){
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);
			de = new DataElement();
			de.setType(DataElementType.LINK);
			de.setUrl(
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_PROPERTIES_LIST,
					new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, idSorgenteDati));
			if (contaListe)
				ServletUtils.setDataElementVisualizzaLabel(de, (long) dimensioneSorgenteDati);
			else
				ServletUtils.setDataElementVisualizzaLabel(de);
			dati.addElement(de);
		}

		return dati;
	}

	public Vector<DataElement>   addTracciamentoDatasourceToDati(TipoOperazione tipoOp, String nome, String nomeJndi,
			String tipoDatabase, String[] tipoDbList, Vector<DataElement> dati, String idSorgenteDati, int dimensioneSorgenteDati) {
		DataElement de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME);
		de.setValue(nome);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
		de.setValue(nomeJndi);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);
		de.setValues(tipoDbList);
		if(tipoDatabase != null){
			de.setSelected(tipoDatabase);
		}
		de.setRequired(true);
		dati.addElement(de);

		if(tipoOp .equals(TipoOperazione.CHANGE)){
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);
			de = new DataElement();
			de.setType(DataElementType.LINK);
			de.setUrl(
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_PROPERTIES_LIST,
					new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, idSorgenteDati));
			if (contaListe)
				ServletUtils.setDataElementVisualizzaLabel(de, (long) dimensioneSorgenteDati);
			else
				ServletUtils.setDataElementVisualizzaLabel(de);
			dati.addElement(de);
		}

		return dati;
	}

	// Prepara la lista di datasource del tracciamento
	public void prepareTracciamentoDatasourceList(List<OpenspcoopSorgenteDati> lista)
			throws Exception {
		try {

			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE);

			Boolean contaListe = ServletUtils.getConfigurazioniPersonalizzateFromSession(this.session);

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_SORGENTI_DATI_TRACCIAMENTO , null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = {
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_JNDI,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE,
					ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					OpenspcoopSorgenteDati od = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pid = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, od.getId() + "");

					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_CHANGE, pid);
					de.setValue(od.getNome());
					de.setIdToRemove(""+od.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(od.getNomeJndi());
					e.addElement(de);

					de = new DataElement();
					de.setValue(od.getTipoDatabase());
					e.addElement(de);

					de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_PROPERTIES_LIST, pid);
					if (contaListe)
						ServletUtils.setDataElementVisualizzaLabel(de, (long) od.sizePropertyList());
					else
						ServletUtils.setDataElementVisualizzaLabel(de);

					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati del sorgente dati del tracciamento
	public boolean tracciamentoDatasourceCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String nomeJndi = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
			String tipoDatabase = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (nomeJndi == null || "".equals(nomeJndi)) {
				this.pd.setMessage("Il campo Nome Jndi deve essere specificato.");
				return false;
			}
			if (tipoDatabase == null || "".equals(tipoDatabase)) {
				this.pd.setMessage("Il campo Tipo Database deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che il sorgente dati non sia gia' stato registrato
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovatoDatasource = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				Tracciamento t = newConfigurazione.getTracciamento();
				if (t != null) {
					List<OpenspcoopSorgenteDati> lista = t.getOpenspcoopSorgenteDatiList();
					OpenspcoopSorgenteDati od = null;
					for (int j = 0; j < t.sizeOpenspcoopSorgenteDatiList(); j++) {
						od = lista.get(j);
						if (nome.equals(od.getNome())) {
							trovatoDatasource = true;
							break;
						}
					}
				}
				if (trovatoDatasource) {
					this.pd.setMessage("Esiste gi&agrave; un sorgente dati con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	// Prepara la lista di property di appender del tracciamento
	public void prepareTracciamentoAppenderPropList(OpenspcoopAppender oa,
			List<Property> lista)
					throws Exception {
		try {
			Parameter pOaId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, ""+oa.getId());
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_PROPERTIES, 
					pOaId);			

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_APPENDER_TRACCIAMENTO , 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_LIST));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA+ " di " + oa.getTipo(), null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALORE
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					Property oap = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();
					Parameter pOapId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA, oap.getId()  + "");
					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_PROPERTIES_CHANGE, pOaId, pOapId
							);
					de.setValue(oap.getNome());
					de.setIdToRemove(""+oap.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(oap.getValore());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati delle property dell'appender del tracciamento
	public boolean tracciamentoAppenderPropCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String id = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID);
			int idInt = Integer.parseInt(id);
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String valore = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALORE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (valore == null || "".equals(valore)) {
				this.pd.setMessage("Il campo Valore deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che la property non sia gia' stata registrata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovataProp = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				Tracciamento t = newConfigurazione.getTracciamento();
				OpenspcoopAppender oa = null;
				for (int j = 0; j < t.sizeOpenspcoopAppenderList(); j++) {
					oa = t.getOpenspcoopAppender(j);
					if (idInt == oa.getId().intValue()) {
						break;
					}
				}
				Property oap = null;
				for (int i = 0; i < oa.sizePropertyList(); i++) {
					oap = oa.getProperty(i);
					if (nome.equals(oap.getNome())) {
						trovataProp = true;
						break;
					}
				}
				if (trovataProp) {
					this.pd.setMessage("Esiste gi&agrave; una Proprietà con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di property di datasource del tracciamento
	public void prepareTracciamentoDatasourcePropList(OpenspcoopSorgenteDati od,
			List<Property> lista)
					throws Exception {
		try {

			Parameter pOdId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, ""+od.getId());
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_PROPERTIES, 
					pOdId);	

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_SORGENTI_DATI_TRACCIAMENTO , 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_LIST));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA+ " di " + od.getNome(), null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALORE
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					Property odp = lista.get(i);

					Parameter pOapId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA, odp.getId()  + "");

					Vector<DataElement> e = new Vector<DataElement>();

					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_PROPERTIES_CHANGE, pOdId, pOapId
							);
					de.setValue(odp.getNome());
					de.setIdToRemove(""+odp.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(odp.getValore());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati delle property del datasource del tracciamento
	public boolean tracciamentoDatasourcePropCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String id = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID);
			int idInt = Integer.parseInt(id);
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String valore = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALORE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (valore == null || "".equals(valore)) {
				this.pd.setMessage("Il campo Valore deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che la property non sia gia' stata registrata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovataProp = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				Tracciamento t = newConfigurazione.getTracciamento();
				List<OpenspcoopSorgenteDati> lista = t.getOpenspcoopSorgenteDatiList();
				OpenspcoopSorgenteDati od = null;
				for (int j = 0; j < t.sizeOpenspcoopSorgenteDatiList(); j++) {
					od = lista.get(j);
					if (idInt == od.getId().intValue()) {
						break;
					}
				}
				List<Property> lista1 = od.getPropertyList();
				Property odp = null;
				for (int i = 0; i < od.sizePropertyList(); i++) {
					odp = lista1.get(i);
					if (nome.equals(odp.getNome())) {
						trovataProp = true;
						break;
					}
				}
				if (trovataProp) {
					this.pd.setMessage("Esiste gi&agrave; una Proprietà con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	// Prepara la lista di appender del tracciamento
	public void prepareTracciamentoAppenderList(List<OpenspcoopAppender> lista)
			throws Exception {
		try {
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER);
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_APPENDER_TRACCIAMENTO , 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_LIST));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_APPENDER_TRACCIAMENTO, null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO,
					ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					OpenspcoopAppender oa = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, oa.getId()  + "");
					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_CHANGE, pId);
					de.setValue(oa.getTipo());
					de.setIdToRemove(""+oa.getId());
					e.addElement(de);

					de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_PROPERTIES_LIST ,pId);

					if (contaListe)
						ServletUtils.setDataElementVisualizzaLabel(de, (long)oa.sizePropertyList());
					else
						ServletUtils.setDataElementVisualizzaLabel(de);
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati dell'appender del tracciamento
	public boolean tracciamentoAppenderCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String tipo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);

			if (tipo == null || "".equals(tipo)) {
				this.pd.setMessage("Il campo Tipo deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che l'appender non sia gia' stato registrato
			/*if (tipoOp.equals("add")) {
					boolean trovatoAppender = false;
					Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
					Tracciamento t = newConfigurazione.getTracciamento();
					if (t != null) {
						OpenspcoopAppender[] lista = t.getOpenspcoopAppenderList();
						OpenspcoopAppender oa = null;
						for (int j = 0; j < t.sizeOpenspcoopAppenderList(); j++) {
							oa = lista[j];
							if (tipo.equals(oa.getTipo())) {
								trovatoAppender = true;
								break;
							}
						}
					}
					if (trovatoAppender) {
						this.pd.setMessage("Esiste gi&agrave; un Appender con tipo " + tipo);
						return false;
					}
				}*/

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}





	// Controlla i dati del system properties
	public boolean systemPropertiesCheckData(TipoOperazione tipoOp) throws Exception {
		try {
			//String id = this.request.getParameter("id");
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String valore = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALORE);

			// Campi obbligatori
			if (nome.equals("") || valore.equals("")) {
				String tmpElenco = "";
				if (nome.equals("")) {
					tmpElenco = "Nome";
				}
				if (valore.equals("")) {
					if (tmpElenco.equals("")) {
						tmpElenco = "Valore";
					} else {
						tmpElenco = tmpElenco + ", Valore";
					}
				}
				this.pd.setMessage("Dati incompleti. E' necessario indicare: " + tmpElenco);
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			//if ((nome.indexOf(" ") != -1) || (valore.indexOf(" ") != -1)) {
			if ((nome.indexOf(" ") != -1) ) {
				this.pd.setMessage("Non inserire spazi nei nomi");
				return false;
			}
			if(valore.startsWith(" ") || valore.endsWith(" ")){
				this.pd.setMessage("Non inserire spazi all'inizio o alla fine dei valori");
				return false;
			}

			// Se tipoOp = add, controllo che non sia gia' stato
			// registrato per la porta delegata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean giaRegistrato = false;
				SystemProperties sps = this.confCore.getSystemPropertiesPdD();
				if(sps!=null){
					for (int i = 0; i < sps.sizeSystemPropertyList(); i++) {
						Property tmpSP = sps.getSystemProperty(i);
						if (nome.equals(tmpSP.getNome())) {
							giaRegistrato = true;
							break;
						}
					}
				}

				if (giaRegistrato) {
					this.pd.setMessage("La proprietà di sistema " + nome + " &egrave; gi&agrave; stata registrata");
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	public void prepareSystemPropertiesList(ISearch ricerca, List<Property> lista)
			throws Exception {
		try {

			String id = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID);
			Parameter pId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, id);
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_SYSTEM_PROPERTIES, pId);

			int idLista = Liste.SYSTEM_PROPERTIES;
			int limit = ricerca.getPageSize(idLista);
			int offset = ricerca.getIndexIniziale(idLista);
			String search = ServletUtils.getSearchFromSession(ricerca, idLista);

			this.pd.setIndex(offset);
			this.pd.setPageSize(limit);
			this.pd.setNumEntries(ricerca.getNumEntries(idLista));

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			if (search.equals("")) {
				this.pd.setSearchDescription("");
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA_SISTEMA,	null));

			} else{
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA_SISTEMA, 
						ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_SYSTEM_PROPERTIES_LIST));
				lstParam.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_RISULTATI_RICERCA, null));
			}
			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// controllo eventuali risultati ricerca
			if (!search.equals("")) {
				this.pd.setSearch("on");
				this.pd.setSearchDescription("Proprietà di Sistema contenenti la stringa '" + search + "'");
			}

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALORE
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				Iterator<Property> it = lista.iterator();
				while (it.hasNext()) {
					Property sp = it.next();

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pIdProp = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, ""+sp.getId());

					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_SYSTEM_PROPERTIES_CHANGE, pIdProp);
					de.setValue(sp.getNome());
					de.setIdToRemove(sp.getNome());
					e.addElement(de);

					de = new DataElement();
					de.setValue(sp.getValore());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati del pannello Configurazione -> Tabella di routing
	public boolean routingCheckData(String[] registriList) throws Exception {

		try{

			String rottaenabled = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ROTTA_ENABLED);
			String tiporotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA);
			if (tiporotta == null) {
				tiporotta = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY;
			}
			String tiposoggrotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
			if (tiposoggrotta == null) {
				tiposoggrotta = "";
			}
			String nomesoggrotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
			if (nomesoggrotta == null) {
				nomesoggrotta = "";
			}
			String registrorotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
			if (registrorotta == null) {
				registrorotta = "";
			}

			// Controllo che i campi "select" abbiano uno dei valori ammessi
			if (!tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && 
					!tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
				this.pd.setMessage("Tipo Rotta dev'essere gateway o registro");
				return false;
			}

			// Campi obbligatori
			if (rottaenabled.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) &&
					tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && ( (tiposoggrotta.equals("") || tiposoggrotta.equals("-")) || nomesoggrotta.equals(""))) {
				this.pd.setMessage("Dati incompleti. E' necessario indicare Tipo e Nome Soggetto");
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && ((tiposoggrotta.indexOf(" ") != -1) || (nomesoggrotta.indexOf(" ") != -1))) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}

			// Controllo che il registrorotta appartenga alla lista di registri
			// disponibili
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
				boolean trovatoReg = false;
				for (int i = 0; i < registriList.length; i++) {
					String tmpReg = registriList[i];
					if (tmpReg.equals(registrorotta)) {
						trovatoReg = true;
					}
				}
				if (!trovatoReg) {
					this.pd.setMessage("Il registro dev'essere 'all' oppure dev'essere scelto tra quelli definiti nel pannello Registri");
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati delle rotte statiche
	public boolean routingListCheckData(TipoOperazione tipoOp, String[] registriList)
			throws Exception {

		try{

			// String id = this.request.getParameter("id");
			// int idInt = 0;
			// if (tipoOp.equals("change"))
			// idInt = Integer.parseInt(id);
			String tipo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String tiporotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA);
			String tiposoggrotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
			String nomesoggrotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
			String registrorotta = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);

			// Controllo che i campi "select" abbiano uno dei valori ammessi
			if (!tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && !tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
				this.pd.setMessage("Tipo Rotta dev'essere gateway o registro");
				return false;
			}

			// Campi obbligatori
			if (tipo.equals("") || nome.equals("")) {
				this.pd.setMessage("Dati incompleti. E' necessario indicare Tipo e Nome");
				return false;
			}
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && (tiposoggrotta.equals("") || nomesoggrotta.equals(""))) {
				this.pd.setMessage("Dati incompleti. E' necessario indicare Tipo e Nome Soggetto");
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			if ((tipo.indexOf(" ") != -1) || (nome.indexOf(" ") != -1)) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY) && ((tiposoggrotta.indexOf(" ") != -1) || (nomesoggrotta.indexOf(" ") != -1))) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}

			// Controllo che il registrorotta appartenga alla lista di registri
			// disponibili
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
				boolean trovatoReg = false;
				for (int i = 0; i < registriList.length; i++) {
					String tmpReg = registriList[i];
					if (tmpReg.equals(registrorotta)) {
						trovatoReg = true;
					}
				}
				if (!trovatoReg) {
					this.pd.setMessage("Il registro dev'essere 'all' oppure dev'essere scelto tra quelli definiti nel pannello Registri");
					return false;
				}
			}

			// Se tipoOp = add, controllo che il routing non sia gia' stato
			// registrato
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean giaRegistrato = false;

				RoutingTable rt = this.confCore.getRoutingTable();
				for (int i = 0; i < rt.sizeDestinazioneList(); i++) {
					RoutingTableDestinazione rtd = rt.getDestinazione(i);
					if (nome.equals(rtd.getNome()) && tipo.equals(rtd.getTipo())) {
						giaRegistrato = true;
						break;
					}
				}

				if (giaRegistrato) {
					this.pd.setMessage("La destinazione " + tipo + "/" + nome + " &egrave; gi&agrave; stata registrata");
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di rotte statiche
	public void prepareRoutingList(ISearch ricerca, List<RoutingTableDestinazione> lista)
			throws Exception {
		try {
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_ROTTE_ROUTING);

			int idLista = Liste.ROUTING;
			int limit = ricerca.getPageSize(idLista);
			int offset = ricerca.getIndexIniziale(idLista);
			String search = ServletUtils.getSearchFromSession(ricerca, idLista);

			this.pd.setIndex(offset);
			this.pd.setPageSize(limit);
			this.pd.setNumEntries(ricerca.getNumEntries(idLista));

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_TABELLA_DI_ROUTING, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROUTING));
			if(search.equals("")){ 
				this.pd.setSearchDescription("");
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_DESTINAZIONI, 
						null));
			}else{
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_DESTINAZIONI, 
						ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROTTE_ROUTING_LIST));
				lstParam.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_RISULTATI_RICERCA, null));
			}

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// controllo eventuali risultati ricerca
			if (!search.equals("")) {
				this.pd.setSearch("on");
				this.pd.setSearchDescription("Destinazioni contenenti la stringa '" + search + "'");
			}

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_DESTINATARIO,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA 
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				Iterator<RoutingTableDestinazione> it = lista.iterator();
				while (it.hasNext()) {
					RoutingTableDestinazione rtd = it.next();

					Vector<DataElement> e = new Vector<DataElement>();

					DataElement de = new DataElement();
					Parameter pId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, rtd.getId() + "");
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROTTE_ROUTING_CHANGE, pId);
					de.setValue(rtd.getTipo()+"/"+rtd.getNome());
					e.addElement(de);

					Route r = rtd.getRoute(0);
					de = new DataElement();
					if (r.getGateway() != null) {
						de.setValue(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY);
					} else {
						de.setValue(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO);
					}
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	// Controlla i dati del pannello Configurazione -> Registro
	public boolean registroCheckData() throws Exception {

		try{

			String statocache = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO_CACHE);
			String dimensionecache = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DIMENSIONE_CACHE);
			String algoritmocache = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE);
			String idlecache = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_IDLE_CACHE);
			String lifecache = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIFE_CACHE);

			// Campi obbligatori
			if (statocache.equals("")) {
				this.pd.setMessage("Dati incompleti. E' necessario indicare lo Stato");
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && ((dimensionecache.indexOf(" ") != -1) || (idlecache.indexOf(" ") != -1) || (lifecache.indexOf(" ") != -1))) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}

			// Controllo che i campi "select" abbiano uno dei valori ammessi
			if (!statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && !statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Stato Cache dev'essere abilitato o disabilitato");
				return false;
			}
			if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && 
					!algoritmocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_LRU) && 
					!algoritmocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_ALGORITMO_CACHE_MRU)) {
				this.pd.setMessage("Algoritmo dev'essere LRU o MRU");
				return false;
			}

			// dimensionecache, idlecache e lifecache devono essere numerici
			if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && !dimensionecache.equals("") && !RegularExpressionEngine.isMatch(dimensionecache,"^[0-9]+$")) {
				this.pd.setMessage("Dimensione dev'essere numerico");
				return false;
			}
			if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && !idlecache.equals("") && !RegularExpressionEngine.isMatch(idlecache,"^[0-9]+$")) {
				this.pd.setMessage("Idle time dev'essere numerico");
				return false;
			}
			if (statocache.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && !lifecache.equals("") && !RegularExpressionEngine.isMatch(lifecache,"^[0-9]+$")) {
				this.pd.setMessage("Life second dev'essere numerico");
				return false;
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati del registro
	public boolean registriCheckData(TipoOperazione tipoOp) throws Exception {

		try{

			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String location = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LOCATION);
			String tipo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
			String utente = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTENTE);
			String password = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PASSWORD);
			String confpw = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONFERMA_PASSWORD);

			// Campi obbligatori
			if (nome.equals("") || location.equals("") || tipo.equals("")) {
				String tmpElenco = "";
				if (nome.equals("")) {
					tmpElenco = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME;
				}
				if (location.equals("")) {
					if (tmpElenco.equals("")) {
						tmpElenco =  ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LOCATION;
					} else {
						tmpElenco = tmpElenco + ", "+ ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LOCATION;
					}
				}
				if (tipo.equals("")) {
					if (tmpElenco.equals("")) {
						tmpElenco = ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO;
					} else {
						tmpElenco = tmpElenco + ", "+ ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO;
					}
				}
				this.pd.setMessage("Dati incompleti. E' necessario indicare: " + tmpElenco);
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			if ((nome.indexOf(" ") != -1) || (location.indexOf(" ") != -1)) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}
			if (tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_UDDI) &&
					((utente.indexOf(" ") != -1) || (password.indexOf(" ") != -1) || (confpw.indexOf(" ") != -1))) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}

			// Controllo che i campi "select" abbiano uno dei valori ammessi
			if (!tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_XML) &&
					!tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_DB) &&
					!tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_UDDI) &&
					!tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_WEB) &&
					!tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_WS)) {
				this.pd.setMessage("Tipo dev'essere xml, db, uddi, web o ws");
				return false;
			}

			// Controllo che le password corrispondano
			if (tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_UDDI) && !password.equals(confpw)) {
				this.pd.setMessage("Le password non corrispondono");
				return false;
			}

			// Se tipoOp = add, controllo che il registro non sia gia' stato
			// registrata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean giaRegistrato = false;

				AccessoRegistro ar = this.confCore.getAccessoRegistro();
				for (int i = 0; i < ar.sizeRegistroList(); i++) {
					AccessoRegistroRegistro arr = ar.getRegistro(i);
					if (nome.equals(arr.getNome())) {
						giaRegistrato = true;
						break;
					}
				}

				if (giaRegistrato) {
					this.pd.setMessage("Il registro " + nome + " &egrave; gi&agrave; stato registrato");
					return false;
				}
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di registri
	public void prepareRegistriList(ISearch ricerca, List<AccessoRegistroRegistro> lista)
			throws Exception {
		try {
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_REGISTRI);

			int idLista = Liste.REGISTRI;
			int limit = ricerca.getPageSize(idLista);
			int offset = ricerca.getIndexIniziale(idLista);
			String search = ServletUtils.getSearchFromSession(ricerca, idLista);

			this.pd.setIndex(offset);
			this.pd.setPageSize(limit);
			this.pd.setNumEntries(ricerca.getNumEntries(idLista));

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_REGISTRO, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ACCESSO_REGISTRO_SERVIZI));
			if(search.equals("")){
				this.pd.setSearchDescription("");
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_REGISTRI, 
						null));
			}else{
				lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_REGISTRI, 
						ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_REGISTRI_LIST));
				lstParam.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_RISULTATI_RICERCA, null));
			}

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// controllo eventuali risultati ricerca
			if (!search.equals("")) {
				this.pd.setSearch("on");
				this.pd.setSearchDescription("Registri contenenti la stringa '" + search + "'");
			}

			// setto le label delle colonne
			String[] labels = {
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				Iterator<AccessoRegistroRegistro> it = lista.iterator();
				while (it.hasNext()) {
					AccessoRegistroRegistro arr = it.next();

					Vector<DataElement> e = new Vector<DataElement>();

					DataElement de = new DataElement();
					Parameter pNome = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME, arr.getNome());
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_REGISTRI_CHANGE , pNome);
					de.setValue(arr.getNome());
					de.setIdToRemove(arr.getNome());
					e.addElement(de);

					de = new DataElement();
					de.setValue(arr.getTipo().toString());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}


	// Controlla i dati del pannello Configurazione -> Generale
	public boolean configurazioneCheckData() throws Exception {

		try{

			String inoltromin = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			String stato = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO);
			String controllo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			//String severita = this.request.getParameter("severita");
			//String severita_log4j = this.request.getParameter("severita_log4j");
			String integman = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INTEGMAN);
			String nomeintegman = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
			String profcoll = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			String connessione = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			String utilizzo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			String validman = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			String gestman = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_GESTMAN);
			String registrazioneTracce = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
			String dump = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DUMP);

			// Campi obbligatori
			if (inoltromin.equals("")) {
				this.pd.setMessage("Dati incompleti. E' necessario indicare una cadenza");
				return false;
			}

			// Controllo che non ci siano spazi nei campi di testo
			if (inoltromin.indexOf(" ") != -1) {
				this.pd.setMessage("Non inserire spazi nei campi di testo");
				return false;
			}

			// Controllo che i campi "select" abbiano uno dei valori ammessi
			if (stato != null && !stato.equals("") && !stato.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && 
					!stato.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO) && !stato.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY)) {
				this.pd.setMessage("Stato dev'essere abilitato, disabilitato o warningOnly");
				return false;
			}
			if (controllo != null && !controllo.equals("") && 
					!controllo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_RIGIDO) &&
					!controllo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_NORMALE)) {
				this.pd.setMessage("Controllo dev'essere rigido o normale");
				return false;
			}
			/*if (!spcoop.equals("off") && !spcoop.equals("fatalOpenspcoop") && !spcoop.equals("errorSpcoop") && !spcoop.equals("errorOpenspcoop") && !spcoop.equals("infoSpcoop") && !spcoop.equals("infoOpenspcoop") && !spcoop.equals("debugLow") && !spcoop.equals("debugMedium") && !spcoop.equals("debugHigh") && !spcoop.equals("all")) {
					this.pd.setMessage("Livello SPCoop dev'essere off, fatalOpenspcoop, errorSpcoop, errorOpenspcoop, infoSpcoop, infoOpenspcoop, debugLow, debugMedium, debugHigh o all");
					return false;
				}
				if (!openspcoop.equals("off") && !openspcoop.equals("fatalOpenspcoop") && !openspcoop.equals("errorSpcoop") && !openspcoop.equals("errorOpenspcoop") && !openspcoop.equals("infoSpcoop") && !openspcoop.equals("infoOpenspcoop") && !openspcoop.equals("debugLow") && !openspcoop.equals("debugMedium") && !openspcoop.equals("debugHigh") && !openspcoop.equals("all")) {
					this.pd.setMessage("Livello OpenSPCoop dev'essere off, fatalOpenspcoop, errorSpcoop, errorOpenspcoop, infoSpcoop, infoOpenspcoop, debugLow, debugMedium, debugHigh o all");
					return false;
				}*/
			if (!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC) &&
					!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_SSL) && 
					!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC_SSL) &&
					!integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM)) {
				this.pd.setMessage("Tipo autenticazione dev'essere basic, ssl, basic,ssl o custom");
				return false;
			}
			if (profcoll != null && !profcoll.equals("") &&
					!profcoll.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && 
					!profcoll.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Profilo di collaborazione dev'essere abilitato o disabilitato");
				return false;
			}
			if (connessione != null && !connessione.equals("") &&
					!connessione.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_NEW) &&
					!connessione.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_REPLY)) {
				this.pd.setMessage("Connessione dev'essere abilitato o disabilitato");
				return false;
			}
			if (utilizzo != null && !utilizzo.equals("") && !utilizzo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && 
					!utilizzo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Utilizzo dev'essere abilitato o disabilitato");
				return false;
			}
			if (validman != null && !validman.equals("") && !validman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) &&
					!validman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Manifest attachments dev'essere abilitato o disabilitato");
				return false;
			}
			if (!gestman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) && 
					!gestman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Gestione dev'essere abilitato o disabilitato");
				return false;
			}
			if (!registrazioneTracce.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) &&
					!registrazioneTracce.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Buste dev'essere abilitato o disabilitato");
				return false;
			}
			if (!dump.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO) &&
					!dump.equals(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO)) {
				this.pd.setMessage("Dump dev'essere abilitato o disabilitato");
				return false;
			}

			// inoltromin dev'essere numerico
			if (!RegularExpressionEngine.isMatch(inoltromin,"^[0-9]+$")) {
				this.pd.setMessage("Inoltro buste dev'essere numerico");
				return false;
			}

			// Se integman = custom, nomeintegman dev'essere specificato
			if (integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM) && 
					(nomeintegman == null || nomeintegman.equals(""))) {
				this.pd.setMessage("Indicare un nome per il tipo autenticazione");
				return false;
			}

			return true;

		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}








	// Prepara la lista di appender dei messaggi diagnostici
	public void prepareDiagnosticaAppenderList(List<OpenspcoopAppender> lista)
			throws Exception {
		try {
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER);
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_APPENDER_MESSAGGI_DIAGNOSTICI ,   null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = {
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO,
					ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					OpenspcoopAppender oa = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pOaId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, oa.getId() + ""); 

					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_CHANGE, pOaId);
					de.setValue(oa.getTipo());
					de.setIdToRemove(""+oa.getId());
					e.addElement(de);

					de = new DataElement();
					de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_PROPERTIES_LIST, pOaId);
					if (contaListe)
						ServletUtils.setDataElementVisualizzaLabel(de, (long)oa.sizePropertyList());
					else
						ServletUtils.setDataElementVisualizzaLabel(de);
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati dell'appender dei messaggi diagnostici
	public boolean diagnosticaAppenderCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String tipo = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);

			if (tipo == null || "".equals(tipo)) {
				this.pd.setMessage("Il campo Tipo deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che l'appender non sia gia' stato registrato
			/*if (tipoOp.equals("add")) {
					boolean trovatoAppender = false;
					Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
					MessaggiDiagnostici md = newConfigurazione.getMessaggiDiagnostici();
					if (md != null) {
						OpenspcoopAppender[] lista = md.getOpenspcoopAppenderList();
						OpenspcoopAppender oa = null;
						for (int j = 0; j < md.sizeOpenspcoopAppenderList(); j++) {
							oa = lista[j];
							if (tipo.equals(oa.getTipo())) {
								trovatoAppender = true;
								break;
							}
						}
					}
					if (trovatoAppender) {
						this.pd.setMessage("Esiste gi&agrave; un Appender con tipo " + tipo);
						return false;
					}
				}*/

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di property di appender dei messaggi diagnostici
	public void prepareDiagnosticaAppenderPropList(OpenspcoopAppender oa,
			List<Property> lista)
					throws Exception {
		try {
			Parameter pOaId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, ""+oa.getId());
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_PROPERTIES, pOaId);


			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_APPENDER_MESSAGGI_DIAGNOSTICI, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_LIST));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA + " di " + oa.getTipo(),
					null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALORE
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					Property oap = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pIdProp = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA, oap.getId()  + "");
					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_PROPERTIES_CHANGE, pOaId, pIdProp);
					de.setValue(oap.getNome());
					de.setIdToRemove(""+oap.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(oap.getValore());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati delle property dell'appender dei messaggi diagnostici
	public boolean diagnosticaAppenderPropCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String id = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID);
			int idInt = Integer.parseInt(id);
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String valore = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALORE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (valore == null || "".equals(valore)) {
				this.pd.setMessage("Il campo Valore deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che la property non sia gia' stata registrata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovataProp = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				MessaggiDiagnostici md = newConfigurazione.getMessaggiDiagnostici();
				OpenspcoopAppender oa = null;
				for (int j = 0; j < md.sizeOpenspcoopAppenderList(); j++) {
					oa = md.getOpenspcoopAppender(j);
					if (idInt == oa.getId().intValue()) {
						break;
					}
				}
				Property oap = null;
				for (int i = 0; i < oa.sizePropertyList(); i++) {
					oap = oa.getProperty(i);
					if (nome.equals(oap.getNome())) {
						trovataProp = true;
						break;
					}
				}
				if (trovataProp) {
					this.pd.setMessage("Esiste gi&agrave; una Proprietà con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di datasource dei messaggi diagnostici
	public void prepareDiagnosticaDatasourceList(List<OpenspcoopSorgenteDati> lista)
			throws Exception {
		try {
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE);
			Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_SORGENTI_DATI_MESSAGGI_DIAGNOSTICI , null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_JNDI,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE,
					ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					OpenspcoopSorgenteDati od = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();

					Parameter pId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, od.getId() + "");
					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_CHANGE, pId);
					de.setValue(od.getNome());
					de.setIdToRemove(""+od.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(od.getNomeJndi());
					e.addElement(de);

					de = new DataElement();
					de.setValue(od.getTipoDatabase());
					e.addElement(de);

					de = new DataElement();
					de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_PROPERTIES_LIST, pId);
					if (contaListe)
						ServletUtils.setDataElementVisualizzaLabel(de, (long)  od.sizePropertyList());
					else
						ServletUtils.setDataElementVisualizzaLabel(de);
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati del sorgente dati dei messaggi diagnostici
	public boolean diagnosticaDatasourceCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String nomeJndi = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_JNDI);
			String tipoDatabase = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_DATABASE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (nomeJndi == null || "".equals(nomeJndi)) {
				this.pd.setMessage("Il campo Nome Jndi deve essere specificato.");
				return false;
			}
			if (tipoDatabase == null || "".equals(tipoDatabase)) {
				this.pd.setMessage("Il campo Tipo Database deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che il sorgente dati non sia gia' stato registrato
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovatoDatasource = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				MessaggiDiagnostici md = newConfigurazione.getMessaggiDiagnostici();
				if (md != null) {
					List<OpenspcoopSorgenteDati> lista = md.getOpenspcoopSorgenteDatiList();
					OpenspcoopSorgenteDati od = null;
					for (int j = 0; j < md.sizeOpenspcoopSorgenteDatiList(); j++) {
						od = lista.get(j);
						if (nome.equals(od.getNome())) {
							trovatoDatasource = true;
							break;
						}
					}
				}
				if (trovatoDatasource) {
					this.pd.setMessage("Esiste gi&agrave; un sorgente dati con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Prepara la lista di property di datasource dei messaggi diagnostici
	public void prepareDiagnosticaDatasourcePropList(OpenspcoopSorgenteDati od,
			List<Property> lista)
					throws Exception {
		try {
			Parameter pId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID, od.getId() + "");
			ServletUtils.addListElementIntoSession(this.session, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_PROPERTIES, pId);

			//this.pd.setIndex(offset);
			//this.pd.setPageSize(limit);
			if (lista == null)
				this.pd.setNumEntries(0);
			else
				this.pd.setNumEntries(lista.size());

			this.pd.setSearchDescription("");
			this.pd.setSearch("off");

			// setto la barra del titolo
			Vector<GeneralLink> titlelist = new Vector<GeneralLink>();
			GeneralLink tl1 = new GeneralLink();
			tl1.setLabel("Configurazione");
			titlelist.addElement(tl1);
			GeneralLink tl2 = new GeneralLink();
			tl2.setLabel("Generale");
			tl2.setUrl("configurazione.do");
			titlelist.addElement(tl2);
			GeneralLink tl3 = new GeneralLink();
			tl3.setLabel("Elenco sorgenti dati messaggi diagnostici");
			tl3.setUrl("diagnosticaDatasourceList.do");
			titlelist.addElement(tl3);
			GeneralLink tl4 = new GeneralLink();
			tl4.setLabel("Proprietà di "+od.getNome());
			titlelist.addElement(tl4);
			this.pd.setTitleList(titlelist);
			
			// setto la barra del titolo
			List<Parameter> lstParam = new ArrayList<Parameter>();

			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE, null));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ELENCO_SORGENTI_DATI_MESSAGGI_DIAGNOSTICI, 
					ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_LIST));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA + " di " + od.getNome(),  null));

			ServletUtils.setPageDataTitle(this.pd, lstParam);

			// setto le label delle colonne
			String[] labels = { 
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALORE
			};
			this.pd.setLabels(labels);

			// preparo i dati
			Vector<Vector<DataElement>> dati = new Vector<Vector<DataElement>>();

			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					Property odp = lista.get(i);

					Vector<DataElement> e = new Vector<DataElement>();
					Parameter pOdpId = new Parameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID_PROPRIETA, odp.getId() + "");
					DataElement de = new DataElement();
					de.setUrl(
							ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_PROPERTIES_CHANGE, pId, pOdpId);
					de.setValue(odp.getNome());
					de.setIdToRemove(""+odp.getId());
					e.addElement(de);

					de = new DataElement();
					de.setValue(odp.getValore());
					e.addElement(de);

					dati.addElement(e);
				}
			}

			this.pd.setDati(dati);
			this.pd.setAddButton(true);
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}

	// Controlla i dati delle property del datasource dei messaggi diagnostici
	public boolean diagnosticaDatasourcePropCheckData(TipoOperazione tipoOp)
			throws Exception {
		try {
			String id = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ID);
			int idInt = Integer.parseInt(id);
			String nome = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
			String valore = this.request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALORE);

			if (nome == null || "".equals(nome)) {
				this.pd.setMessage("Il campo Nome deve essere specificato.");
				return false;
			}
			if (valore == null || "".equals(valore)) {
				this.pd.setMessage("Il campo Valore deve essere specificato.");
				return false;
			}

			// Se tipoOp = add, controllo che la property non sia gia' stata registrata
			if (tipoOp.equals(TipoOperazione.ADD)) {
				boolean trovataProp = false;
				Configurazione newConfigurazione = this.core.getConfigurazioneGenerale();
				MessaggiDiagnostici md = newConfigurazione.getMessaggiDiagnostici();
				List<OpenspcoopSorgenteDati> lista = md.getOpenspcoopSorgenteDatiList();
				OpenspcoopSorgenteDati od = null;
				for (int j = 0; j < md.sizeOpenspcoopSorgenteDatiList(); j++) {
					od = lista.get(j);
					if (idInt == od.getId().intValue()) {
						break;
					}
				}
				List<Property> lista1 = od.getPropertyList();
				Property odp = null;
				for (int i = 0; i < od.sizePropertyList(); i++) {
					odp = lista1.get(i);
					if (nome.equals(odp.getNome())) {
						trovataProp = true;
						break;
					}
				}
				if (trovataProp) {
					this.pd.setMessage("Esiste gi&agrave; una Proprietà con nome " + nome);
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			this.log.error("Exception: " + e.getMessage(), e);
			throw new Exception(e);
		}
	}



	public Vector<DataElement>  addValoriRottaToDati(TipoOperazione tipoOp, String nome,
			String tipo, String tiporotta, String registrorotta,
			String[] registriList,
			String[] registriListLabel, Vector<DataElement> dati, String tiposoggrotta, String nomesoggrotta, 
			String[] tipiSoggettiLabel, String[] tipiSoggettiLabelPerProtocollo )
					throws DriverRegistroServiziException {
		DataElement de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_DESTINATARIO);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

//		if (InterfaceType.STANDARD.equals(ServletUtils.getUserFromSession(this.session).getInterfaceType())) {
//			de = new DataElement();
//			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
//			de.setValue(this.soggettiCore.getTipoSoggettoDefault());
//			de.setType(DataElementType.HIDDEN);
//			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
//			de.setSize(getSize());
//			dati.addElement(de);
//		} else {
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
//		if (tipo == null) {
//			de.setValue("");
//		} else {
//			de.setValue(tipo);
//		}
		de.setSelected(tipo);
		de.setValues(tipiSoggettiLabel);
		//de.setType(DataElementType.TEXT_EDIT);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setPostBack(true);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);
//		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME);
		de.setValue(nome);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
		de.setSize(getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ROTTA);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoR = { ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY,
				ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO 
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA);
		de.setValues(tipoR);
		de.setSelected(tiporotta);
		//				de.setOnChange("CambiaRotta('add')");
		de.setPostBack(true);
		dati.addElement(de);

//		if (InterfaceType.STANDARD.equals(ServletUtils.getUserFromSession(this.session).getInterfaceType())) {
//			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY)) {
//				de = new DataElement();
//				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
//				de.setValue(this.soggettiCore.getTipoSoggettoDefault());
//				de.setType(DataElementType.HIDDEN);
//				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
//				de.setSize(getSize());
//				dati.addElement(de);
//
//				de = new DataElement();
//				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
//				de.setValue(nomesoggrotta);
//				de.setType(DataElementType.TEXT_EDIT);
//				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
//				de.setSize(getSize());
//				de.setRequired(true);
//				dati.addElement(de);
//			}
//		} else {
		if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY)) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
			//de.setValue(tiposoggrotta);
			de.setSelected(tiposoggrotta);
			de.setValues(tipiSoggettiLabelPerProtocollo);
			//de.setType(DataElementType.TEXT_EDIT);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
			de.setSize(getSize());
			de.setRequired(true);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
			de.setValue(nomesoggrotta);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
			de.setSize(getSize());
			de.setRequired(true);
			dati.addElement(de);
		}
//		}

		if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
			de.setValues(registriList);
			de.setLabels(registriListLabel);
			de.setSelected(registrorotta);
			dati.addElement(de);
		}

		return dati;
	}




	public Vector<DataElement> addRoutingToDati(TipoOperazione tipoOp, String tiporotta,
			String tiposoggrotta, String nomesoggrotta, String registrorotta,
			String rottaenabled,  
			String[] registriList, String[] registriListLabel, String[] tipiSoggettiLabel,
			Vector<DataElement> dati) throws DriverRegistroServiziException {
		DataElement de = new DataElement();

		String[] tipoRouting = {
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ROUTING_DELLE_BUSTE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ROTTA_ENABLED);
		de.setValues(tipoRouting);
		de.setSelected(rottaenabled);
		//					de.setOnChange("CambiaRouting()");
		de.setPostBack(true);
		dati.addElement(de);

		if (rottaenabled.equals(ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO)) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ROTTA_DI_DEFAULT);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoR = {
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY,
					ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA);
			de.setValues(tipoR);
			de.setSelected(tiporotta);
			//						de.setOnChange("CambiaRotta('routing')");
			de.setPostBack(true);
			dati.addElement(de);

//			if (InterfaceType.STANDARD.equals(ServletUtils.getUserFromSession(this.session).getInterfaceType())) {
//				if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY)) {
//					de = new DataElement();
//					de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
//					de.setValue(this.soggettiCore.getTipoSoggettoDefault());
//					de.setType(DataElementType.HIDDEN);
//					de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
//					de.setSize(getSize());
//					dati.addElement(de);
//
//					de = new DataElement();
//					de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
//					de.setValue(nomesoggrotta);
//					de.setType(DataElementType.TEXT_EDIT);
//					de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
//					de.setRequired(true);
//					de.setSize(getSize());
//					dati.addElement(de);
//				}
//			} else {
			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_GATEWAY)) {
				de = new DataElement();
				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
				//de.setValue(tiposoggrotta);
				//de.setType(DataElementType.TEXT_EDIT);
				de.setType(DataElementType.SELECT);
				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_SOGGETTO_ROTTA);
				de.setValues(tipiSoggettiLabel);
				de.setSelected(tiposoggrotta);
				de.setSize(getSize());
				de.setRequired(true);
				dati.addElement(de);

				de = new DataElement();
				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
				de.setValue(nomesoggrotta);
				de.setType(DataElementType.TEXT_EDIT);
				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_SOGGETTO_ROTTA);
				de.setSize(getSize());
				de.setRequired(true);
				dati.addElement(de);
			}
//			}

			if (tiporotta.equals(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_ROTTA_REGISTRO)) {
				de = new DataElement();
				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
				de.setType(DataElementType.SELECT);
				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRO_ROTTA);
				de.setValues(registriList);
				de.setLabels(registriListLabel);
				de.setSelected(registrorotta);
				dati.addElement(de);
			}

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ROTTE_STATICHE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			de = new DataElement();
			de.setType(DataElementType.LINK);
			de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROTTE_ROUTING_LIST);
			de.setValue(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_DESTINAZIONI);
			dati.addElement(de);
		}

		return dati;
	}

	public Vector<DataElement> addRegistroToDati(TipoOperazione tipoOP, String nome, String location, String tipo,
			String utente, String password, String confpw,
			Vector<DataElement> dati) {
		DataElement de = new DataElement();

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME);
		de.setValue(nome);
		if(TipoOperazione.ADD.equals(tipoOP)){
			de.setType(DataElementType.TEXT_EDIT);
		}
		else{
			de.setType(DataElementType.TEXT);
		}
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME);
		de.setSize( getSize());
		de.setRequired(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LOCATION);
		de.setValue(location);
		de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LOCATION);
		de.setSize( getSize());
		de.setRequired(true);
		dati.addElement(de);

		//String[] tipoReg = { "xml", "db", "uddi", "web", "ws" };
		String[] tipoReg = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_XML,
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_DB,
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_WEB,
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_WS
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO);
		de.setValues(tipoReg);
		de.setSelected(tipo);
		//				de.setOnChange("CambiaTipo('add')");
		de.setPostBack(true);
		dati.addElement(de);

		if (tipo.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_UDDI)) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_UTENTE);
			de.setValue(utente);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTENTE);
			de.setSize( getSize());
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PASSWORD);
			de.setValue(password);
			de.setType(DataElementType.CRYPT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PASSWORD);
			de.setSize( getSize());
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONFERMA_PASSWORD);
			de.setValue(confpw);
			de.setType(DataElementType.CRYPT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONFERMA_PASSWORD);
			de.setSize( getSize());
			dati.addElement(de);
		}


		return dati;
	}


	public Vector<DataElement> addConfigurazioneToDati(  User user,
			String inoltromin, String stato,
			String controllo, String severita, String severita_log4j,
			String integman, String nomeintegman, String profcoll,
			String connessione, String utilizzo, String validman,
			String gestman, String registrazioneTracce, String dump, String xsd,
			String tipoValidazione, String confPers, Configurazione configurazione,
			Vector<DataElement> dati, String applicaMTOM) {
		DataElement de = new DataElement();
		Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);

		if (InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setValue(inoltromin);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setSize( getSize());
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_STATO);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO);
			de.setValue(stato);

			dati.addElement(de);
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			de.setValue(controllo);

			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			de.setValue(profcoll);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			de.setValue(validman);
			dati.addElement(de);
		} else {

			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INOLTRO_BUSTE_NON_RISCONTRATE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setValue(inoltromin);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setRequired(true);
			de.setSize(getSize());
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_VALIDAZIONE_BUSTE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoStato = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_STATO);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO);
			de.setValues(tipoStato);
			de.setSelected(stato);
			dati.addElement(de);

			String[] tipoControllo = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_RIGIDO,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_NORMALE
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONTROLLO);
			de.setValues(tipoControllo);
			de.setSelected(controllo);
			dati.addElement(de);

			String[] tipoPF = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
			de.setValues(tipoPF);
			de.setSelected(profcoll);
			dati.addElement(de);

			String[] tipoVM = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALIDMAN);
			de.setValues(tipoVM);
			de.setSelected(validman);
			dati.addElement(de);
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_MESSAGGI_DIAGNOSTICI);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		//					String[] tipoMsg = { "off", "fatalOpenspcoop", "errorSpcoop", "errorOpenspcoop", "infoSpcoop", "infoOpenspcoop",
		//							"debugLow", "debugMedium", "debugHigh", "all" };
		String[] tipoMsg = { LogLevels.LIVELLO_OFF, LogLevels.LIVELLO_FATAL, LogLevels.LIVELLO_ERROR_PROTOCOL, LogLevels.LIVELLO_ERROR_INTEGRATION, 
				LogLevels.LIVELLO_INFO_PROTOCOL, LogLevels.LIVELLO_INFO_INTEGRATION,
				LogLevels.LIVELLO_DEBUG_LOW, LogLevels.LIVELLO_DEBUG_MEDIUM, LogLevels.LIVELLO_DEBUG_HIGH,
				LogLevels.LIVELLO_ALL};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA);
		//		de.setLabel("Livello Severita");
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA);
		de.setValues(tipoMsg);
		de.setSelected(severita);
		dati.addElement(de);

		de = new DataElement();
		//		de.setLabel("Livello Severita Log4J");
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA_LOG4J);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA_LOG4J);
		de.setValues(tipoMsg);
		de.setSelected(severita_log4j);
		dati.addElement(de);

		if (!InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			if (this.confCore.isMsgDiagnostici_showConfigurazioneCustomAppender()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_LIST);
				if (contaListe) {
					int totAppender = 0;
					if (configurazione.getMessaggiDiagnostici() != null)
						totAppender =
						configurazione.getMessaggiDiagnostici().sizeOpenspcoopAppenderList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, (long)totAppender);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, null);
				dati.addElement(de);
			}
			if (this.confCore.isMsgDiagnostici_showSorgentiDatiDatabase()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_LIST);
				if (contaListe) {
					int totDs = 0;
					if (configurazione.getMessaggiDiagnostici() != null)
						totDs =
						configurazione.getMessaggiDiagnostici().sizeOpenspcoopSorgenteDatiList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI, (long)totDs);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI);
				dati.addElement(de);
			}
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_TRACCIAMENTO);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoBuste = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
		de.setValues(tipoBuste);
		de.setSelected(registrazioneTracce);
		dati.addElement(de);

		String[] tipoDump = {
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_DUMP);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DUMP);
		de.setValues(tipoDump);
		de.setSelected(dump);
		dati.addElement(de);

		if (!InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			if (this.confCore.isTracce_showConfigurazioneCustomAppender()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_LIST);
				if (contaListe) {
					int totAppender = 0;
					if (configurazione.getTracciamento() != null)
						totAppender =
						configurazione.getTracciamento().sizeOpenspcoopAppenderList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, (long)totAppender);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, null);
				dati.addElement(de);
			}
			if (this.confCore.isTracce_showSorgentiDatiDatabase()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_LIST);
				if (contaListe) {
					int totDs = 0;
					if (configurazione.getTracciamento() != null)
						totDs =
						configurazione.getTracciamento().sizeOpenspcoopSorgenteDatiList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI, (long)totDs);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI);
				dati.addElement(de);
			}
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INTEGRATION_MANAGER);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		int totEl = 3;
		if (confPers.equals(Costanti.CHECK_BOX_ENABLED_TRUE))
			totEl++;
		String[] tipoIM = new String[totEl];
		tipoIM[0] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_SSL;
		tipoIM[1] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC;
		tipoIM[2] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC_SSL;
		if (confPers.equals(Costanti.CHECK_BOX_ENABLED_TRUE))
			tipoIM[3] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM;
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INTEGMAN);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INTEGMAN);
		de.setValues(tipoIM);
		de.setSelected(integman);
		//					de.setOnChange("CambiaIntegMan('" +
		//							InterfaceType.STANDARD.equals(user.getInterfaceType()) +
		//							"')");
		de.setPostBack(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
		if (integman == null || !integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM))
			de.setType(DataElementType.HIDDEN);
		else
			de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
		de.setValue(nomeintegman);
		dati.addElement(de);

		if (InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setValue(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_REPLY);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setValue(CostantiConfigurazione.DISABILITATO.toString());
			de.setSelected(utilizzo);
			dati.addElement(de);
		} else {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_VALIDAZIONE_CONTENUTI_APPLICATIVI);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoXsd = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY 
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_XSD);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_XSD);
			de.setValues(tipoXsd);
			//						de.setOnChange("CambiaValidazione('" +
			//							InterfaceType.STANDARD.equals(user.getInterfaceType()) +
			//							"')");
			de.setPostBack(true);
			if (xsd == null) {
				de.setSelected(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO);
			} else {
				de.setSelected(xsd);
			}
			dati.addElement(de);

			if (ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO.equals(xsd) || 
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY .equals(xsd)) {
				//String[] tipi_validazione = { "xsd", "wsdl", "openspcoop" };
				String[] tipi_validazione = { 
						ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_XSD,
						ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_WSDL
				};
				de = new DataElement();
				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE);
				de.setType(DataElementType.SELECT);
				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE);
				de.setValues(tipi_validazione);
				if (tipoValidazione == null) {
					de.setSelected(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_XSD);
				} else {
					de.setSelected(tipoValidazione);
				}
				dati.addElement(de);
				
				
				// Applica MTOM 
				de = new DataElement();
				de.setLabel(PorteDelegateCostanti.LABEL_PARAMETRO_PORTE_DELEGATE_ACCETTA_MTOM);

				
				if(InterfaceType.AVANZATA.equals(ServletUtils.getUserFromSession(this.session).getInterfaceType())){
					de.setType(DataElementType.CHECKBOX);
					if( ServletUtils.isCheckBoxEnabled(applicaMTOM) || CostantiRegistroServizi.ABILITATO.equals(applicaMTOM) ){
						de.setSelected(true);
					}
				}
				else{
					de.setType(DataElementType.HIDDEN);
					de.setValue(applicaMTOM);
				}
			 
				de.setName(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_APPLICA_MTOM);
				dati.addElement(de);
			}

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_RISPOSTE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoConn = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_NEW,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_REPLY
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setValues(tipoConn);
			de.setSelected(connessione);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INDIRIZZO_TELEMATICO);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoU = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setValues(tipoU);
			de.setSelected(utilizzo);
			dati.addElement(de);
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_MANIFEST_ATTACHMENTS);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoGM = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_GESTMAN);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_GESTMAN);
		de.setValues(tipoGM);
		de.setSelected(gestman);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_REGISTRO_SERVIZI);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ACCESSO_REGISTRO_SERVIZI);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_TABELLA_DI_ROUTING);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROUTING);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA_SISTEMA);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_SYSTEM_PROPERTIES_LIST);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);

		return dati;
	}

	public Vector<DataElement> addConfigurazioneErrorModeToDati( User user,
			String inoltromin, String stato,
			String controllo, String severita, String severita_log4j,
			String integman, String nomeintegman, String profcoll,
			String connessione, String utilizzo, String validman,
			String gestman, String registrazioneTracce, String dump, String xsd,
			String tipoValidazione, String confPers, Configurazione configurazione,
			Vector<DataElement> dati) {
		Boolean contaListe = ServletUtils.getContaListeFromSession(this.session);
		DataElement de = new DataElement();

		if (InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setValue(inoltromin);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setSize( getSize());
			dati.addElement(de);
		} else {

			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INOLTRO_BUSTE_NON_RISCONTRATE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setValue(inoltromin);
			de.setType(DataElementType.TEXT_EDIT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INOLTRO_MIN);
			de.setRequired(true);
			de.setSize(getSize());
			dati.addElement(de);
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_VALIDAZIONE_BUSTE);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoStato = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_STATO);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_STATO);
		de.setValues(tipoStato);
		de.setSelected(stato);
		dati.addElement(de);

		String[] tipoControllo = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_RIGIDO,
				ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONTROLLO_NORMALE
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONTROLLO);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONTROLLO);
		de.setValues(tipoControllo);
		de.setSelected(controllo);
		dati.addElement(de);

		String[] tipoPF = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_PROFILO_COLLABORAZIONE);
		de.setValues(tipoPF);
		de.setSelected(profcoll);
		dati.addElement(de);

		String[] tipoVM = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_VALIDMAN);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_VALIDMAN);
		de.setValues(tipoVM);
		de.setSelected(validman);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_MESSAGGI_DIAGNOSTICI);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		//					String[] tipoMsg = { "off", "fatalOpenspcoop", "errorSpcoop", "errorOpenspcoop", "infoSpcoop", "infoOpenspcoop",
		//							"debugLow", "debugMedium", "debugHigh", "all" };
		String[] tipoMsg = { LogLevels.LIVELLO_OFF, LogLevels.LIVELLO_FATAL, LogLevels.LIVELLO_ERROR_PROTOCOL, LogLevels.LIVELLO_ERROR_INTEGRATION, 
				LogLevels.LIVELLO_INFO_PROTOCOL, LogLevels.LIVELLO_INFO_INTEGRATION,
				LogLevels.LIVELLO_DEBUG_LOW, LogLevels.LIVELLO_DEBUG_MEDIUM, LogLevels.LIVELLO_DEBUG_HIGH,
				LogLevels.LIVELLO_ALL};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA);
		de.setValues(tipoMsg);
		de.setSelected(severita);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA_LOG4J);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_LIVELLO_SEVERITA_LOG4J);
		de.setValues(tipoMsg);
		de.setSelected(severita_log4j);
		dati.addElement(de);

		if (!InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			if (this.confCore.isMsgDiagnostici_showConfigurazioneCustomAppender()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_APPENDER_LIST);
				if (contaListe) {
					int totAppender = 0;
					if (configurazione.getMessaggiDiagnostici() != null)
						totAppender =
						configurazione.getMessaggiDiagnostici().sizeOpenspcoopAppenderList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, (long)totAppender);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, null);
				dati.addElement(de);
			}
			if (this.confCore.isMsgDiagnostici_showSorgentiDatiDatabase()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_DIAGNOSTICA_DATASOURCE_LIST);
				if (contaListe) {
					int totDs = 0;
					if (configurazione.getMessaggiDiagnostici() != null)
						totDs =
						configurazione.getMessaggiDiagnostici().sizeOpenspcoopSorgenteDatiList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI, (long)totDs);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI);
				dati.addElement(de);
			}
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_TRACCIAMENTO);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoBuste = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_REGISTRAZIONE_TRACCE);
		de.setValues(tipoBuste);
		de.setSelected(registrazioneTracce);
		dati.addElement(de);

		String[] tipoDump = {
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_DUMP);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_DUMP);
		de.setValues(tipoDump);
		de.setSelected(dump);
		dati.addElement(de);

		if (!InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			if (this.confCore.isTracce_showConfigurazioneCustomAppender()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_APPENDER_LIST);
				if (contaListe) {
					int totAppender = 0;
					if (configurazione.getTracciamento() != null)
						totAppender =
						configurazione.getTracciamento().sizeOpenspcoopAppenderList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, (long)totAppender);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_APPENDER, null);
				dati.addElement(de);
			}
			if (this.confCore.isTracce_showSorgentiDatiDatabase()) {
				de = new DataElement();
				de.setType(DataElementType.LINK);
				de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_TRACCIAMENTO_DATASOURCE_LIST);
				if (contaListe) {
					int totDs = 0;
					if (configurazione.getTracciamento() != null)
						totDs =
						configurazione.getTracciamento().sizeOpenspcoopSorgenteDatiList();
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI, (long)totDs);
				} else
					ServletUtils.setDataElementVisualizzaCustomLabel(de, ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_SORGENTI_DATI);
				dati.addElement(de);
			}
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INTEGRATION_MANAGER);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		int totEl = 3;
		if (confPers.equals(Costanti.CHECK_BOX_ENABLED_TRUE))
			totEl++;
		String[] tipoIM = new String[totEl];
		tipoIM[0] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_SSL;
		tipoIM[1] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC;
		tipoIM[2] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_BASIC_SSL;
		if (confPers.equals(Costanti.CHECK_BOX_ENABLED_TRUE))
			tipoIM[3] = ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM;
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_INTEGMAN);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_INTEGMAN);
		de.setValues(tipoIM);
		de.setSelected(integman);
		//					de.setOnChange("CambiaIntegMan('" +
		//							InterfaceType.STANDARD.equals(user.getInterfaceType()) +
		//							"')");
		de.setPostBack(true);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
		if (integman == null || !integman.equals(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_IM_CUSTOM))
			de.setType(DataElementType.HIDDEN);
		else
			de.setType(DataElementType.TEXT_EDIT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_NOME_INTEGMAN);
		de.setValue(nomeintegman);
		dati.addElement(de);

		if (InterfaceType.STANDARD.equals(user.getInterfaceType())) {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setValue(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_REPLY);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setType(DataElementType.HIDDEN);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setValue(CostantiConfigurazione.DISABILITATO.toString());
			de.setSelected(utilizzo);
			dati.addElement(de);
		} else {
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_VALIDAZIONE_CONTENUTI_APPLICATIVI);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoXsd = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY 
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_XSD);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_XSD);
			de.setValues(tipoXsd);
			//						de.setOnChange("CambiaValidazione('" +
			//							InterfaceType.STANDARD.equals(user.getInterfaceType()) +
			//							"')");
			de.setPostBack(true);
			if (xsd == null) {
				de.setSelected(ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO);
			} else {
				de.setSelected(xsd);
			}
			dati.addElement(de);

			if (ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO.equals(xsd) || 
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_STATO_WARNING_ONLY .equals(xsd)) {
				//String[] tipi_validazione = { "xsd", "wsdl", "openspcoop" };
				String[] tipi_validazione = { 
						ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_XSD,
						ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_WSDL
				};
				de = new DataElement();
				de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE);
				de.setType(DataElementType.SELECT);
				de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE);
				de.setValues(tipi_validazione);
				if (tipoValidazione == null) {
					de.setSelected(ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_TIPO_VALIDAZIONE_XSD);
				} else {
					de.setSelected(tipoValidazione);
				}
				dati.addElement(de);
			}

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_RISPOSTE);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoConn = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_NEW,
					ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_CONNESSIONE_REPLY
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_CONNESSIONE);
			de.setValues(tipoConn);
			de.setSelected(connessione);
			dati.addElement(de);

			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_INDIRIZZO_TELEMATICO);
			de.setType(DataElementType.TITLE);
			dati.addElement(de);

			String[] tipoU = { 
					ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
					ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
			};
			de = new DataElement();
			de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setType(DataElementType.SELECT);
			de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_UTILIZZO);
			de.setValues(tipoU);
			de.setSelected(utilizzo);
			dati.addElement(de);
		}

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_MANIFEST_ATTACHMENTS);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		String[] tipoGM = { 
				ConfigurazioneCostanti.DEFAULT_VALUE_ABILITATO,
				ConfigurazioneCostanti.DEFAULT_VALUE_DISABILITATO
		};
		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_GESTMAN);
		de.setType(DataElementType.SELECT);
		de.setName(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_GESTMAN);
		de.setValues(tipoGM);
		de.setSelected(gestman);
		dati.addElement(de);



		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_REGISTRO_SERVIZI);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ACCESSO_REGISTRO_SERVIZI);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_TABELLA_DI_ROUTING);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ROUTING);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);

		de = new DataElement();
		de.setLabel(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_PROPRIETA_SISTEMA);
		de.setType(DataElementType.TITLE);
		dati.addElement(de);

		de = new DataElement();
		de.setType(DataElementType.LINK);
		de.setUrl(ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_SYSTEM_PROPERTIES_LIST);
		ServletUtils.setDataElementVisualizzaLabel(de);
		dati.addElement(de);


		return dati;
	}

}
