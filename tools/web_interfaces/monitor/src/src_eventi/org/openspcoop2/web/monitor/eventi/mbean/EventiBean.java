package org.openspcoop2.web.monitor.eventi.mbean;

import org.openspcoop2.core.eventi.constants.TipoSeverita;
import org.openspcoop2.web.monitor.core.core.PddMonitorProperties;
import org.openspcoop2.web.monitor.core.dao.IService;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.mbean.PdDBaseBean;
import org.openspcoop2.web.monitor.eventi.bean.EventiSearchForm;
import org.openspcoop2.web.monitor.eventi.bean.EventoBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;

/****
 * 
 * 
 * Bean web per la sezione delle sonde
 * 
 * 
 * @author pintori
 *
 */
public class EventiBean extends PdDBaseBean<EventoBean, Long, IService<EventoBean,Long>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private static Logger log = LoggerManager.getPddMonitorCoreLogger();


	private transient EventiSearchForm search;

	private EventoBean evento;

	private List<SelectItem> tipiSeverita = null;

	private String eventoAction = "add";
	
	private boolean visualizzaIdCluster = false;
	private boolean visualizzaIdClusterAsSelectList = false;
	private List<String> listIdCluster;
	
	public EventiBean(){

		// inizializzazione
		try {
			PddMonitorProperties pddMonitorProperties = PddMonitorProperties.getInstance(EventiBean.log);
			List<String> pddMonitorare = pddMonitorProperties.getListaPdDMonitorate_StatusPdD();
			this.setVisualizzaIdCluster(pddMonitorare!=null && pddMonitorare.size()>1);
			this.visualizzaIdClusterAsSelectList = pddMonitorProperties.isAttivoTransazioniUtilizzoSondaPdDListAsClusterId();
			if(pddMonitorare!=null && pddMonitorare.size()>1){
				this.listIdCluster = new ArrayList<String>();
				this.listIdCluster.add("--");
				this.listIdCluster.addAll(pddMonitorare);
			}
		} catch (Exception e) {
			EventiBean.log
			.error("Errore durante l'inizializzazione EventiBean.",
					e);
		}
	}



	public EventoBean getEvento() {
		if (this.evento == null){
			this.evento = new EventoBean();
		}
		return this.evento;
	}

	public void setEvento(EventoBean evento) {
		this.evento = evento;
	}

	public void setSearch(EventiSearchForm search) {
		this.search = search;
	}

	public EventiSearchForm getSearch() {
		return this.search;
	}

	public List<SelectItem> getTipiSeverita(){
		if(this.tipiSeverita!= null)
			return this.tipiSeverita;

		this.tipiSeverita = new ArrayList<SelectItem>();

		this.tipiSeverita.add(new SelectItem(EventiSearchForm.NON_SELEZIONATO));
		this.tipiSeverita.add(new SelectItem(TipoSeverita.FATAL.getValue()));
		this.tipiSeverita.add(new SelectItem(TipoSeverita.ERROR.getValue()));
		this.tipiSeverita.add(new SelectItem(TipoSeverita.WARN.getValue()));
		this.tipiSeverita.add(new SelectItem(TipoSeverita.INFO.getValue()));
		this.tipiSeverita.add(new SelectItem(TipoSeverita.DEBUG.getValue()));

		return this.tipiSeverita;
	}

	/**
	 * Listener eseguito prima di aggiungere una nuova sonda
	 * 
	 * @param ae
	 */
	@Override
	public void addNewListener(ActionEvent ae) {
		this.evento = null;
	}


	@Override
	public IService<EventoBean, Long> getService(){
		return this.service;
	}


	public String getEventoAction() {
		return this.eventoAction;
	}

	public void setEventoAction(String eventoAction) {
		this.eventoAction = eventoAction;

	}
	
	public boolean isVisualizzaIdCluster() {
		return this.visualizzaIdCluster;
	}

	public void setVisualizzaIdCluster(boolean visualizzaIdCluster) {
		this.visualizzaIdCluster = visualizzaIdCluster;
	}
	
	public boolean isVisualizzaIdClusterAsSelectList() {
		return this.visualizzaIdClusterAsSelectList;
	}

	public void setVisualizzaIdClusterAsSelectList(boolean visualizzaIdClusterAsSelectList) {
		this.visualizzaIdClusterAsSelectList = visualizzaIdClusterAsSelectList;
	}
	
	public List<SelectItem> getListIdCluster() {
		ArrayList<SelectItem> list = new ArrayList<SelectItem>();
		if(this.listIdCluster!=null && this.listIdCluster.size()>0){
			for (String id : this.listIdCluster) {
				list.add(new SelectItem(id));
			}
		}
		return list;
	}

}