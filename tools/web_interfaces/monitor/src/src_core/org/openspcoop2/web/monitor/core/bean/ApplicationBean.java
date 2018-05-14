package org.openspcoop2.web.monitor.core.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import org.openspcoop2.web.monitor.core.bean.UserDetailsBean;
import org.openspcoop2.web.monitor.core.bean.UserDetailsBean.RuoloBean;
import org.openspcoop2.web.monitor.core.utils.BrowserInfo;
import org.openspcoop2.web.monitor.core.utils.BrowserInfo.BrowserFamily;
import org.openspcoop2.web.monitor.core.core.PddMonitorProperties;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;

public class ApplicationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static ApplicationBean _instance = null;

	public static ApplicationBean getInstance(){
		if(ApplicationBean._instance == null)
			init();

		return ApplicationBean._instance;
	}

	private static synchronized void init(){
		if(ApplicationBean._instance == null){
			ApplicationBean._instance = new ApplicationBean();
		}
	}

	/* Funzionalita */
	public static final String FUNZIONALITA_ALLARMI = "allarmi";
	public static final String FUNZIONALITA_STATISTICHE_BASE = "statistiche_base";
	public static final String FUNZIONALITA_STATISTICHE_PERSONALIZZATE = "statistiche_personalizzate";
	public static final String FUNZIONALITA_TRANSAZIONI_BASE = "transazioni_base";
	public static final String FUNZIONALITA_TRANSAZIONI_LIVE = "transazioni_live";
	public static final String FUNZIONALITA_TRANSAZIONI_LIVE_OPERATORE = "transazioni_live_operatore";
	public static final String FUNZIONALITA_EXPORT_TRANSAZIONI = "export_transazioni";
	public static final String FUNZIONALITA_TRANSAZIONI_CONTENUTI = "transazioni_contenuti";
	public static final String FUNZIONALITA_RICERCHE_PERSONALIZZATE = "ricerche_personalizzate";
	public static final String FUNZIONALITA_ESITI_LIVE = "esiti_live";
	public static final String FUNZIONALITA_ESITI_LIVE_OPERATORE = "esiti_live_operatore";
	public static final String FUNZIONALITA_PROCESSI = "processi";
	public static final String FUNZIONALITA_EXPORT_PROCESSI = "export_processi";
	public static final String FUNZIONALITA_SONDE_APPLICATIVE = "sonde_applicative";
	public static final String FUNZIONALITA_AUDITING = "auditing";
	public static final String FUNZIONALITA_EVENTI = "eventi";
	public static final String FUNZIONALITA_ANALISI_DATI = "analisi_dati";
	public static final String FUNZIONALITA_UTENTI = "utenti";
	public static final String FUNZIONALITA_STATUS_PDD = "status_pdd";
	public static final String FUNZIONALITA_GENERICHE = "funzionalita_generiche";
	public static final String FUNZIONALITA_ARCHIVIAZIONE_DATI = "dump_contenuti";
	public static final String FUNZIONALITA_GESTIONE_PASSWORD = "gestione_password";
	public static final String FUNZIONALITA_GRAFICI_SVG = "grafici_svg";
	public static final String FUNZIONALITA_REPORT = "report";

	/* Ruoli */
	public static final String RUOLO_USER = UserDetailsBean.RUOLO_USER;
	public static final String RUOLO_OPERATORE = UserDetailsBean.RUOLO_OPERATORE;
	public static final String RUOLO_CONFIGURATORE = UserDetailsBean.RUOLO_CONFIGURATORE;
	public static final String RUOLO_AMMINISTRATORE = UserDetailsBean.RUOLO_AMMINISTRATORE;

	private static Logger log =  LoggerManager.getPddMonitorCoreLogger(); 

	private transient LoginBean loginBean;
	private Map<String, Boolean> funzionalita = new HashMap<String, Boolean>();
	private Map<String, Boolean> roles = null;

	private Boolean backwardCompatibilityOpenspcoop1 = false;

	private static Map<String, Boolean> funzionalitaStaticInstance = null;
	private synchronized void initializeFunzionalita(PddMonitorProperties pddMonitorProperties) throws Exception{
		if(funzionalitaStaticInstance==null){
			funzionalitaStaticInstance = new HashMap<String, Boolean>();
			
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE, pddMonitorProperties.isAttivoModuloTransazioniBase());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_TRANSAZIONI_LIVE, pddMonitorProperties.isAttivoModuloTransazioniBase());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_TRANSAZIONI_LIVE_OPERATORE, pddMonitorProperties.isAttivoModuloTransazioniBase() && pddMonitorProperties.isAttivoLiveRuoloOperatore());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_STATISTICHE_BASE, pddMonitorProperties.isAttivoModuloTransazioniStatisticheBase());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_ALLARMI, pddMonitorProperties.isAttivoModuloAllarmi());
			boolean attivoModuloTransazioniPersonalizzate = pddMonitorProperties.isAttivoModuloTransazioniPersonalizzate();
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI, attivoModuloTransazioniPersonalizzate); 
			boolean attivoModuloRicerchePersonalizzate = pddMonitorProperties.isAttivoModuloRicerchePersonalizzate();
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_RICERCHE_PERSONALIZZATE, attivoModuloRicerchePersonalizzate);
			boolean attivoModuloTransazioniStatistichePersonalizzate = pddMonitorProperties.isAttivoModuloTransazioniStatistichePersonalizzate();
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_STATISTICHE_PERSONALIZZATE,attivoModuloTransazioniStatistichePersonalizzate);
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_PROCESSI, pddMonitorProperties.isAttivoModuloProcessi());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_SONDE_APPLICATIVE, pddMonitorProperties.isAttivoModuloSonde());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_AUDITING, pddMonitorProperties.isAuditingEnabled());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_EVENTI, pddMonitorProperties.isAttivoModuloEventi());
			// lazy initialization per i ruoli, dato che il bean login verra' // inizializzato solo dopo il login dell'utente.
			// Funzionalita analisi dei dati abilitata se almeno una delle categorie personalizzabili e' abilitata
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_ANALISI_DATI, (attivoModuloTransazioniPersonalizzate || attivoModuloRicerchePersonalizzate || attivoModuloTransazioniStatistichePersonalizzate ));
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_ESITI_LIVE, pddMonitorProperties.isAttivoTransazioniEsitiLive());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_ESITI_LIVE_OPERATORE, pddMonitorProperties.isAttivoTransazioniEsitiLive() && pddMonitorProperties.isAttivoLiveRuoloOperatore());
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_STATUS_PDD, pddMonitorProperties.isStatusPdDEnabled());

			// Funzionalita' che non sono gestite dal file di properties, ma necessarie per il controllo dei contenuti
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_UTENTI,true);
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_GENERICHE,true);
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_ARCHIVIAZIONE_DATI, true);
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_EXPORT_TRANSAZIONI, true);
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_EXPORT_PROCESSI, true);
			
			// Funzionalita' gestione password, controlla la gestione della password quando la console viene utilizzata in modalita' login esterno.
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_GESTIONE_PASSWORD, pddMonitorProperties.isGestionePasswordUtentiAttiva());

			// funzionalita utilizza grafici in modalita' svg
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_GRAFICI_SVG, pddMonitorProperties.isGraficiSvgEnabled());
			
			// funzionalita visualizzazione dei report in formato PDF 
			funzionalitaStaticInstance.put(ApplicationBean.FUNZIONALITA_REPORT, pddMonitorProperties.isAttivoModuloReports());
		}
	}
	
	public void disabilitaFunzionalita(String nomeFunzionalita){
		// devo rimuoverlo in entrambe le mappe
		this.funzionalita.remove(nomeFunzionalita);
		this.funzionalita.put(nomeFunzionalita,false);
		funzionalitaStaticInstance.remove(nomeFunzionalita);
		funzionalitaStaticInstance.put(nomeFunzionalita,false);
	}
	
	public ApplicationBean() {
		// inizializzazione
		try {
			PddMonitorProperties pddMonitorProperties = PddMonitorProperties.getInstance(ApplicationBean.log);

			this.backwardCompatibilityOpenspcoop1 = pddMonitorProperties.isBackwardCompatibilityOpenspcoop1();

			initializeFunzionalita(pddMonitorProperties);
			this.funzionalita.putAll(funzionalitaStaticInstance);
			

		} catch (Exception e) {
			ApplicationBean.log
			.error("Errore durante l'inizializzazione del ApplicationBean.",
					e);
		}
	}

	private void initRoles() {
		try {
			if (this.loginBean != null
					&& this.loginBean.getLoggedUser() != null) {
				UserDetailsBean u = this.loginBean.getLoggedUser();
				this.roles = getRuoliUtente(u);
			}
		} catch (Exception e) {
			ApplicationBean.log.error(e.getMessage(), e);
		}
	}

	public boolean isFunzionalitaAbilitata(String nomeFunzionalita){
		boolean abilitata = false;

		if(this.funzionalita != null){
			Boolean ab = this.funzionalita.get(nomeFunzionalita);

			if(ab!= null){
				abilitata = ab.booleanValue();
			}
		}
		return abilitata;
	}
	
	public Map<String, Boolean>  getRuoliUtente(UserDetailsBean u) {
		Map<String, Boolean> ruoli = new HashMap<String, Boolean>();
		List<RuoloBean> auths = u.getAuthorities();
		if (auths != null && !auths.isEmpty()) {

			ruoli.put(ApplicationBean.RUOLO_AMMINISTRATORE, false);
			ruoli.put(ApplicationBean.RUOLO_CONFIGURATORE, false);
			ruoli.put(ApplicationBean.RUOLO_OPERATORE, false);
			ruoli.put(ApplicationBean.RUOLO_USER, false);

			Iterator<RuoloBean> itAuths = auths.iterator();
			while (itAuths.hasNext()) {
				RuoloBean grantedAuthority = 
						itAuths.next();
				String a = grantedAuthority.getAuthority();
				if (UserDetailsBean.RUOLO_AMMINISTRATORE.equals(a))
					ruoli.put(ApplicationBean.RUOLO_AMMINISTRATORE, true);
				if (UserDetailsBean.RUOLO_CONFIGURATORE.equals(a))
					ruoli.put(ApplicationBean.RUOLO_CONFIGURATORE, true);
				if (UserDetailsBean.RUOLO_OPERATORE.equals(a))
					ruoli.put(ApplicationBean.RUOLO_OPERATORE, true);
				if (UserDetailsBean.RUOLO_USER.equals(a))
					ruoli.put(ApplicationBean.RUOLO_USER, true);
			}
		}

		return ruoli;
	}

	public LoginBean getLoginBean() {
		return this.loginBean;
	}

	public Map<String, Boolean> getModules() {
		return this.funzionalita;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setModules(Map<String, Boolean> modules) {
		this.funzionalita = modules;
	}

	private void checkRoles() {
		if (this.roles == null)
			initRoles();
	}

	/*
	 * Calcolo proprieta di render
	 */


	/********************************************************/

	/******** Voci del menu' sezione MONITORAGGIO   ********/	

	/*******************************************************/


	public boolean getShowTransazioniBase() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le transazioni sono visualizzabili dall' operatore
		if (
				//	this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)	||

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}

	public boolean getShowTransazioniContenuti() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le transazioni sono visualizzabili dall' operatore
		if (
				this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)	||

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}

	public boolean getShowRicerchePersonalizzate() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_RICERCHE_PERSONALIZZATE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le ricerche sono visualizzabili dall' operatore
		if (
				//				this.roles.get(ApplicationBean.AMMINISTRATORE)	||

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}



	public boolean getShowSonde() {

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_SONDE_APPLICATIVE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// gli allarmi sono visualizzabili dall' operatore
		if (
				this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)				|| 
				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}



	public boolean getShowAllarmi() {

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_ALLARMI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// gli allarmi sono visualizzabili dall' operatore
		if (
				this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)				|| 
				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}


	public boolean getShowTransazioniLive() {
		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		/// sezione visibile solo all'operatore ed amministratore
		if(this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_LIVE)){
			return true;
		}
		//per il ruolo operatore bisogna verificare se e' abilitato il live per i non admin
		if (this.roles.get(ApplicationBean.RUOLO_OPERATORE) && this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_LIVE_OPERATORE) )
			return true;

		return false;
	}


	public boolean getShowEsitiLive() {
		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		/// sezione visibile solo all'operatore ed amministratore
		if(this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_ESITI_LIVE)){
			return true;
		}
		//per il ruolo operatore bisogna verificare se e' abilitato il live per i non admin
		if (this.roles.get(ApplicationBean.RUOLO_OPERATORE) && this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_BASE)
				&& this.funzionalita.get(ApplicationBean.FUNZIONALITA_ESITI_LIVE_OPERATORE) )
			return true;

		return false;
	}

	public boolean getShowProcessi() {
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_PROCESSI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// processi visualizzabili solo da amministratore   
		if (
				this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)			
				//				|| 	this.roles.get(ApplicationBean.OPERATORE)
				)
			return true;

		return false;
	}

	public boolean getShowEventi() {
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_EVENTI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// eventi visualizzabili solo da amministratore   
		if (
				this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE)			
				//				|| 	this.roles.get(ApplicationBean.OPERATORE)
				)
			return true;

		return false;
	}



	/********************************************************/

	/******** Voci del menu' sezione TRANSAZIONI GRID   ********/	

	/*******************************************************/

	public boolean getShowInformazioniContenutiTransazioniGrid() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le informazioni sono visualizzabili dall' operatore
		if (
				//				this.roles.get(ApplicationBean.AMMINISTRATORE)	||

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;

	}

	public boolean getShowInformazioniEventiTransazioniGrid() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_EVENTI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le informazioni sono visualizzabili dall' operatore
		if (
				//				this.roles.get(ApplicationBean.AMMINISTRATORE)	||

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;

	}


	/********************************************************/

	/******** Voci del menu' sezione STATISTICHE   ********/	

	/*******************************************************/

	public boolean getShowStatisticheBase() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_BASE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le statistiche sono visualizzabili dall' operatore
		if (
				//				this.roles.get(ApplicationBean.AMMINISTRATORE)				|| 

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}

	public boolean getShowStatistichePersonalizzate() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_PERSONALIZZATE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le statistiche sono visualizzabili dall' operatore
		if (
				//						this.roles.get(ApplicationBean.AMMINISTRATORE)				|| 

				this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_BASE);
	}


	public boolean getShowStatisticheConfigurazioniGenerali() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_BASE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le statistiche sono visualizzabili dall' amministratore
		if (this.roles.get(ApplicationBean.RUOLO_OPERATORE)
				|| this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
			return true;

		return false;
	}


	/********************************************************/

	/******** Voci del menu' sezione CONFIGURAZIONE ********/	

	/*******************************************************/

	public boolean getShowSezioneConfigurazione() {
		// la sezione configurazione puo essere vista da amministratore e
		// configuratore
		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;


		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE)
				|| this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
			return true;

		return false;
	}



	public boolean getShowConfigStatistichePersonalizzate() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_PERSONALIZZATE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// la configurazione delle statistiche e' visibile al configuratore
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		// return this.modules.get(ApplicationBean.STATISTICHE_BASE);
		return false;
	}

	public boolean getShowConfigRicerchePersonalizzate() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_RICERCHE_PERSONALIZZATE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// le la configurazione delle ricerche e' visualizzabile dal configuratore
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;
	}


	public boolean getShowConfigTransazioniContenuti() {

		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;
	}



	public boolean getShowAnalisiContenuti() {
		// l'analisi contenuti e' visibile solo all'amministratore
		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		//		if (this.roles.get(ApplicationBean.AMMINISTRATORE))
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;

	}

	public boolean getShowConfigurazioneSonde() {
		// la sezione delle sonde e' visibile solo all'amministratore
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_SONDE_APPLICATIVE))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;

	}

	public boolean getShowConfigurazioneLibreria() {

		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// sezione visibile solo al configuratore
		if (!this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return false;

		if (this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_PERSONALIZZATE)
				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_RICERCHE_PERSONALIZZATE)
				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI)
				//				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_ALLARMI)
				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_PROCESSI))
			return true;

		return false;
	}

	public boolean getShowRuoloConfiguratore() {

		// Il ruolo configuratore deve essere visualizzato nella maschera di creazione degli utenti esattamente come viene visualizzato nel menu'
		return this.getShowSezioneConfigurazione() || this.getShowCambiaPassword();

		//		if (this.funzionalita.get(ApplicationBean.FUNZIONALITA_STATISTICHE_PERSONALIZZATE)
		//				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_RICERCHE_PERSONALIZZATE)
		//				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_TRANSAZIONI_CONTENUTI)
		//				|| this.funzionalita.get(ApplicationBean.FUNZIONALITA_ALLARMI))
		//			return true;
		//
		//		return false;
	}

	/**
	 * Visualizza la sezione di configurazione:{Gestione Utenti, Auditing}
	 * 
	 * @return
	 */
	public boolean getShowUserManagement() {

		checkRoles();

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// visualizzazione consentita solo all'amministratore
		if (this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
			return true;

		return false;
	}


	public boolean getShowCambiaPassword() {

		checkRoles();
		
		// gestione password deve essere abilitata a prescindere dall'utenza
		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_GESTIONE_PASSWORD))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

//		if (!this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
//			return true;
		
		return true;
	}


	public boolean getShowConfigurazioneProcessi() {
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_PROCESSI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// processi visualizzabili dal configuratore
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;
	}

	public boolean getShowAuditing(){
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_AUDITING))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// visualizzazione consentita solo all'amministratore
		if (this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
			return true;

		return false;
	}

	public boolean getShowConfigurazioneAllarmi() {
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_ALLARMI))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// processi visualizzabili dal configuratore
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;
	}
	
	public boolean getShowReport(){
		checkRoles();

		if (!this.funzionalita.get(ApplicationBean.FUNZIONALITA_REPORT))
			return false;

		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// visualizzazione consentita solo all'amministratore
		if (this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;

		return false;
	}

	public boolean getBackwardCompatibilityOpenspcoop1(){
		return this.backwardCompatibilityOpenspcoop1;
	}

	public BrowserInfo getBrowserInfo(){
		BrowserInfo browserInfo = null;
		try {
			browserInfo = BrowserInfo.getBrowserInfo(FacesContext.getCurrentInstance());
		} catch (Exception e) {
			ApplicationBean.log.error("Errore durante la lettura delle info Browser:" + e.getMessage(),e);
		}

		return browserInfo;
	}

//	public void cleanSVG(){
//		BrowserInfo browserInfo = getBrowserInfo();
//		try {
//			if(browserInfo.getBrowserFamily().equals(BrowserFamily.IE)){
//				HttpServletResponse response = BrowserInfo.getResponse(FacesContext.getCurrentInstance());
//			//	response.setHeader("X-UA-Compatible", "IE=EmulateIE8");
//			}
//		} catch (Exception e) {
//			ApplicationBean.log.error("Errore durante la lettura delle info Browser:" + e.getMessage(),e);
//		}
//
//	}
	
	public boolean isAmministratore() {
		checkRoles();
		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// controllo utente possiede ruolo utente
		if (this.roles.get(ApplicationBean.RUOLO_AMMINISTRATORE))
			return true;
		
		return false;
	}

	public void setAmministratore(boolean amministratore) {
	}

	public boolean isUser() {
		checkRoles();
		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// controllo utente possiede ruolo utente
		if (this.roles.get(ApplicationBean.RUOLO_USER))
			return true;
		
		return false;
	}

	public void setUser(boolean user) {	}

	public boolean isConfiguratore() {
		checkRoles();
		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// controllo utente possiede ruolo configuratore
		if (this.roles.get(ApplicationBean.RUOLO_CONFIGURATORE))
			return true;

		return false;
	}

	public void setConfiguratore(boolean configuratore) {}

	public boolean isOperatore() {
		checkRoles();
		if(this.roles == null)
			return false;

		if(this.roles!= null && this.roles.isEmpty())
			return false;

		// controllo utente possiede ruolo operatore
		if (this.roles.get(ApplicationBean.RUOLO_OPERATORE))
			return true;
		
		return false;
	}

	public void setOperatore(boolean operatore) {}
	
	public boolean isGraficiSvgEnabled(){
		return this.funzionalita.get(ApplicationBean.FUNZIONALITA_GRAFICI_SVG);
	}

	public void cleanSVG(){
		BrowserInfo browserInfo = getBrowserInfo();
		try {
			if(browserInfo.getBrowserFamily().equals(BrowserFamily.IE)){
				// solo per <= 8
//				if(browserInfo.getVersion() != null && browserInfo.getVersion().intValue() <= 8){
//					HttpServletResponse response = BrowserInfo.getResponse(FacesContext.getCurrentInstance());
//					response.setHeader("X-UA-Compatible", "IE=EmulateIE8");
//				}
				
				// per tutte le versioni
				HttpServletResponse response = BrowserInfo.getResponse(FacesContext.getCurrentInstance());
				response.setHeader("X-UA-Compatible", "IE=edge");
			}
		} catch (Exception e) {
			ApplicationBean.log.error("Errore durante la lettura delle info Browser:" + e.getMessage(),e);
		}
	}

}