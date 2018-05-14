package org.openspcoop2.web.monitor.core.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import org.openspcoop2.web.monitor.core.bean.UserDetailsBean;
import org.openspcoop2.web.monitor.core.core.Utils;
import org.openspcoop2.web.monitor.core.bean.ApplicationBean;
import org.openspcoop2.web.monitor.core.bean.LoginBean;
import org.openspcoop2.web.monitor.core.core.Utility;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.utils.ContentAuthorizationCostanti;
import org.openspcoop2.web.monitor.core.utils.ContentAuthorizationManager;

public class ContentAuthorizationFilter implements Filter {

	/** Logger utilizzato per debug. * */
	private static Logger log = LoggerManager.getPddMonitorCoreLogger();

	// configurazione filtro
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	// messaggio da visualizzare per l'utente
	private static final String msgErrorePre = "Attenzione! L'utente ";

	private static final String msgErrorePost = " non dispone delle autorizzazioni necessarie per visualizzare la pagina richiesta";

	// pagina da mostrare all'utente al posto di quella richiesta
	private static final String jspErrore = "/pages/welcome.jsf";
	private static final String jspLogin = "/public/login.jsf";

	// id delle variabili in sessione per il controllo del messaggio da
	// visualizzare all'utente
	private static final String msgErroreKey = "acclim";

	private static final String accLimKey = "acclimflag";


	private List<String> excludedPaths = null;

	/***************************************************************************
	 * Metodo destroy
	 */
	@Override
	public void destroy() {
		log.debug("DISTRUIZIONE FILTRO: AuthorizationFilter");
		this.filterConfig = null;
	}

	/***************************************************************************
	 * 
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {

		String userName = "";
		try {

			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			HttpSession session = request.getSession();

			String urlRichiesta = request.getServletPath(); //request.getRequestURI();
			log.debug("Richiesta Risorsa ["+urlRichiesta+"]");

			//			String pathInfo = request.getServletPath();
			//			log.debug("Richiesta ServletPath ["+pathInfo+"]");

			boolean controlloAccessoRichiesto = Utils.isContentAuthorizationRequiredForThisResource(request,this.excludedPaths); 

			log.debug("Controllo Accesso per La risorsa richiesta ["+controlloAccessoRichiesto+"]");

			boolean effettuaRedirect = false;
			boolean utenteLoggato = true;
			if(controlloAccessoRichiesto){
				LoginBean lb =  Utility.getLoginBeanFromSession(session);

				log.debug("Richiesto Accesso per La risorsa protetta ["+urlRichiesta+"]");

				if (lb != null) {
					UserDetailsBean user = lb.getLoggedUser();

					// controllo l'utente loggato
					if (user != null) {
						Map<String, Boolean> ruoliUtente = ApplicationBean.getInstance().getRuoliUtente(user);
						log.debug("[Analisi attributi User: " + user.getUsername() + " ]");
						//							for (String ruoloKey : ruoliUtente.keySet()) {
						//								log.debug("[Ruolo ["+ruoloKey+"] : " + ruoliUtente.get(ruoloKey) + " ]");
						//
						//							}

						ApplicationBean applicationBean = ApplicationBean.getInstance(); // (ApplicationBean) session.getAttribute("applicationBean");
						// 1. Controllo che la risorsa richiesta faccia parte di un modulo che e' abilitato
						effettuaRedirect = !ContentAuthorizationManager.getInstance().isRisorsaRichiestaAbilitata(urlRichiesta,applicationBean);

						// 2. Se ho passato il primo controllo,verifico che la risorsa sia disponibile per l'utente in base al ruolo di cui dispone
						if(!effettuaRedirect)
							effettuaRedirect = !ContentAuthorizationManager.getInstance().checkRuoloRichiestoPerLaRisorsa(ruoliUtente, urlRichiesta,applicationBean);

						log.debug("[La risorsa richiesta " + (effettuaRedirect ? "non" : "") + " e' disponibile per l'utente.]");
					}
					else{
						// e' stata richiesta una risorsa protetta, ma non ho trovato il principal
						effettuaRedirect = true;	
						utenteLoggato = false;
					}
				}
				else {
					// e' stata richiesta una risorsa protetta, ma non ho trovato le info sull'utente
					effettuaRedirect = true;
					utenteLoggato = false;
				}
			} else {
				// richiesta una risorsa non protetta
				// controllo se ho richiesto la index
				if(StringUtils.contains(request.getRequestURI(), "public/login.jsf") || StringUtils.contains(request.getRequestURI(), "public/timeoutPage.jsf")){
					LoginBean lb =  Utility.getLoginBeanFromSession(session);
					log.debug("Richiesto Accesso per La risorsa ["+urlRichiesta+"]");
					if (lb != null) {
						UserDetailsBean user = lb.getLoggedUser();
						if (user != null) {

							log.debug("[User: " + user.getUsername() + "] e' autenticato ma ha richiesto la pagina di login o di timeout, redirect verso ["+jspErrore+"]");

							effettuaRedirect = true; // effettua redirect verso welcome.jsf
							utenteLoggato = true; // flag che uso per impostare un messaggio di errore = false per non visualizzare il messaggio di accesso errato. 
						}
					}

				}
			}

			// Rilevata infrazione allora faccio un redirect
			if(effettuaRedirect){

				StringBuilder sb = new StringBuilder(0);
				sb.append(msgErrorePre).append(userName).append(msgErrorePost);

				String msgErrore = sb.toString();
				// redirect verso una pagina alternativa
				log.debug("Redirect: "	+ request.getContextPath() + jspErrore);

				if(utenteLoggato){
					// setto gli attributi che riguardano il
					// messaggio da visualizzare all'utente
					session.setAttribute(msgErroreKey, msgErrore);
					session.setAttribute(accLimKey, true);
					// redirect
					response.sendRedirect(request.getContextPath()+ jspErrore);
				} else {
					// utente non loggato va alla pagina di login
					// redirect
					response.sendRedirect(request.getContextPath()+ jspLogin);
				}

				// blocco la catena di filtri
				return;
			}

			// log.debug("[Analisi URL richiesta]");
			// log.debug("URL richiesta (param size
			// "+request.getParameterMap().size()+"): " + urlRichiesta);
			// Enumeration<String> en = request.getParameterNames();
			// while (en.hasMoreElements()) {
			// String key = (String) en.nextElement();
			// log.debug("PAR["+key+"]["+request.getParameter(key)+"]" +
			// "[" + request.getParameter(key).length()+"]");
			// }

			// log.debug("SESSION ACCLIMKEY PRE: "
			// + session.getAttribute(accLimKey));

			// all'interno della pagina chiamata nel redirect mostro il messaggio di info.
			if (urlRichiesta.indexOf("/pages/welcome.jsf") != -1) {
				boolean used = session.getAttribute(accLimKey) != null ? ((Boolean) session.getAttribute(accLimKey)).booleanValue()	: false;

				// prima volta che viene richiamata la pagina welcome dopo la
				// redirect
				// indico che ho usato la informazione.
				if (used) {
					session.setAttribute(accLimKey, false);
				} else {
					// non visualizzo il messaggio
					session.setAttribute(accLimKey, false);
					session.setAttribute(msgErroreKey, null);
				}
			}

			// log.debug("SESSION ACCLIMKEY POST: "
			// + session.getAttribute(accLimKey));

			// faccio proseguire le chiamate ai filtri
			chain.doFilter(request, response);
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (ServletException e) {
			log.error(e.getMessage());
		}
	}

	/***************************************************************************
	 * 
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;

		this.excludedPaths = new ArrayList<String>();
		this.excludedPaths.addAll(Arrays.asList(ContentAuthorizationCostanti.listaPathConsentiti));
		//		this.excludedPaths.add("/a4j/");
		//		this.excludedPaths.add("/images/");
		//		this.excludedPaths.add("/css/");
		//		this.excludedPaths.add("/scripts/");
		//		this.excludedPaths.add("/pages/welcome.jsf");
		//		this.excludedPaths.add("/public/timeoutPage.jsf");
		//		this.excludedPaths.add("/public/login.jsf");
		//		this.excludedPaths.add("/FusionCharts/");
	}
}