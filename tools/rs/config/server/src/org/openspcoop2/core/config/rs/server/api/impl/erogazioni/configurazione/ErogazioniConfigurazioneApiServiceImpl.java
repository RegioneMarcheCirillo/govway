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
package org.openspcoop2.core.config.rs.server.api.impl.erogazioni.configurazione;

import static org.openspcoop2.core.config.rs.server.api.impl.erogazioni.ErogazioniApiHelper.convert;
import static org.openspcoop2.core.config.rs.server.api.impl.erogazioni.ErogazioniApiHelper.correlazioneApplicativaRichiestaCheckData;
import static org.openspcoop2.core.config.rs.server.api.impl.erogazioni.ErogazioniApiHelper.correlazioneApplicativaRispostaCheckData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.AutorizzazioneRuoli;
import org.openspcoop2.core.config.AutorizzazioneScope;
import org.openspcoop2.core.config.CorrelazioneApplicativa;
import org.openspcoop2.core.config.CorrelazioneApplicativaElemento;
import org.openspcoop2.core.config.CorrelazioneApplicativaRispostaElemento;
import org.openspcoop2.core.config.CorsConfigurazione;
import org.openspcoop2.core.config.GestioneToken;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneServiziApplicativi;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneServizioApplicativo;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneSoggetti;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneSoggetto;
import org.openspcoop2.core.config.ResponseCachingConfigurazione;
import org.openspcoop2.core.config.Ruolo;
import org.openspcoop2.core.config.Scope;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.Soggetto;
import org.openspcoop2.core.config.ValidazioneContenutiApplicativi;
import org.openspcoop2.core.config.constants.StatoFunzionalitaConWarning;
import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.config.constants.TipoAutorizzazione;
import org.openspcoop2.core.config.constants.ValidazioneContenutiApplicativiTipo;
import org.openspcoop2.core.config.rs.server.api.ErogazioniConfigurazioneApi;
import org.openspcoop2.core.config.rs.server.api.impl.Enums;
import org.openspcoop2.core.config.rs.server.api.impl.Helper;
import org.openspcoop2.core.config.rs.server.api.impl.IdServizio;
import org.openspcoop2.core.config.rs.server.api.impl.erogazioni.ErogazioniApiHelper;
import org.openspcoop2.core.config.rs.server.api.impl.erogazioni.ErogazioniEnv;
import org.openspcoop2.core.config.rs.server.config.ServerProperties;
import org.openspcoop2.core.config.rs.server.model.APIImplAutenticazione;
import org.openspcoop2.core.config.rs.server.model.ApiImplStato;
import org.openspcoop2.core.config.rs.server.model.CachingRisposta;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutenticazione;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutenticazioneToken;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazione;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneApplicativi;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneApplicativo;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneRuoli;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneRuolo;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneScope;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneScopes;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneSoggetti;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneSoggetto;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutorizzazioneView;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiGestioneToken;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRichiesta;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRichiestaEnum;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRichiestaItem;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRisposta;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRispostaEnum;
import org.openspcoop2.core.config.rs.server.model.CorrelazioneApplicativaRispostaItem;
import org.openspcoop2.core.config.rs.server.model.GestioneCors;
import org.openspcoop2.core.config.rs.server.model.ListaCorrelazioneApplicativaRichiesta;
import org.openspcoop2.core.config.rs.server.model.ListaCorrelazioneApplicativaRisposta;
import org.openspcoop2.core.config.rs.server.model.ListaRateLimitingPolicy;
import org.openspcoop2.core.config.rs.server.model.ProfiloEnum;
import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyBaseErogazione;
import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyErogazioneNew;
import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyErogazioneView;
import org.openspcoop2.core.config.rs.server.model.RateLimitingPolicyItem;
import org.openspcoop2.core.config.rs.server.model.RegistrazioneMessaggi;
import org.openspcoop2.core.config.rs.server.model.StatoFunzionalitaConWarningEnum;
import org.openspcoop2.core.config.rs.server.model.TipoAutenticazioneEnum;
import org.openspcoop2.core.config.rs.server.model.Validazione;
import org.openspcoop2.core.controllo_traffico.AttivazionePolicy;
import org.openspcoop2.core.controllo_traffico.AttivazionePolicyFiltro;
import org.openspcoop2.core.controllo_traffico.AttivazionePolicyRaggruppamento;
import org.openspcoop2.core.controllo_traffico.beans.InfoPolicy;
import org.openspcoop2.core.controllo_traffico.constants.RuoloPolicy;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDServizioApplicativo;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.core.registry.constants.CredenzialeTipo;
import org.openspcoop2.core.registry.constants.RuoloContesto;
import org.openspcoop2.core.registry.constants.RuoloTipologia;
import org.openspcoop2.core.registry.constants.ScopeContesto;
import org.openspcoop2.core.registry.driver.FiltroRicercaRuoli;
import org.openspcoop2.core.registry.driver.FiltroRicercaScope;
import org.openspcoop2.utils.service.BaseImpl;
import org.openspcoop2.utils.service.authorization.AuthorizationConfig;
import org.openspcoop2.utils.service.authorization.AuthorizationManager;
import org.openspcoop2.utils.service.context.IContext;
import org.openspcoop2.utils.service.fault.jaxrs.FaultCode;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.ConsoleUtilities;
import org.openspcoop2.web.ctrlstat.servlet.config.ConfigurazioneUtilities;
import org.openspcoop2.web.ctrlstat.servlet.pa.PorteApplicativeCostanti;
import org.openspcoop2.web.ctrlstat.servlet.ruoli.RuoliCore;
import org.openspcoop2.web.lib.mvc.TipoOperazione;
/**
 * ErogazioniConfigurazioneApiServiceImpl
 * 
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class ErogazioniConfigurazioneApiServiceImpl extends BaseImpl implements ErogazioniConfigurazioneApi {

	public ErogazioniConfigurazioneApiServiceImpl(){
		super(org.slf4j.LoggerFactory.getLogger(ErogazioniConfigurazioneApiServiceImpl.class));
	}

	private AuthorizationConfig getAuthorizationConfig() throws Exception{
		return new AuthorizationConfig(ServerProperties.getInstance().getProperties());
	}

    /**
     * Aggiunta di applicativi all'elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di aggiungere applicativi all'elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public void addErogazioneControlloAccessiAutorizzazionePuntualeApplicativi(ControlloAccessiAutorizzazioneApplicativo body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);

			if ( !TipoAutorizzazione.isAuthenticationRequired(pa.getAutorizzazione()) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("L'autenticazione puntuale non è abilitata");
			}
			
			final IDServizioApplicativo idSA = new IDServizioApplicativo();
				idSA.setIdSoggettoProprietario(env.idSoggetto.toIDSoggetto());
				idSA.setNome(body.getApplicativo());
			
			final ServizioApplicativo sa = Helper.supplyOrNonValida( 
					() -> env.saCore.getServizioApplicativo(idSA),
					"Servizio Applicativo " + idSA.toString()
				);
					
			
			final org.openspcoop2.core.config.constants.CredenzialeTipo tipoAutenticazione = org.openspcoop2.core.config.constants.CredenzialeTipo.toEnumConstant(pa.getAutenticazione());	
			List<ServizioApplicativo> saCompatibili = env.saCore.soggettiServizioApplicativoList(env.idSoggetto.toIDSoggetto(),env.userLogin,tipoAutenticazione);
			if (!Helper.findFirst(saCompatibili, s -> s.getId() == sa.getId()).isPresent()) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("La modalità di autenticazione del servizio Applicativo scelto non è compatibile con il gruppo che si vuole configurare");
			}
			
			if (pa.getServiziApplicativiAutorizzati() == null)
				pa.setServiziApplicativiAutorizzati(new PortaApplicativaAutorizzazioneServiziApplicativi());
			
			if ( Helper.findFirst(
						pa.getServiziApplicativiAutorizzati().getServizioApplicativoList(),
						s -> s.getId() == sa.getId()
						).isPresent()
				) {
				throw FaultCode.CONFLITTO.toException("Servizio Applicativo già associato");
			}
			
			env.requestWrapper.overrideParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, pa.getId().toString());
			env.requestWrapper.overrideParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SOGGETTO, env.idSoggetto.getId().toString());
			env.requestWrapper.overrideParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_AUTORIZZATO, sa.getId().toString());
			if (!env.paHelper.porteAppServizioApplicativoAutorizzatiCheckData(TipoOperazione.ADD)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}
			
			PortaApplicativaAutorizzazioneServizioApplicativo paSaAutorizzato = new PortaApplicativaAutorizzazioneServizioApplicativo();
			paSaAutorizzato.setNome(sa.getNome());
			paSaAutorizzato.setTipoSoggettoProprietario(env.idSoggetto.getTipo());
			paSaAutorizzato.setNomeSoggettoProprietario(env.idSoggetto.getNome());

			pa.getServiziApplicativiAutorizzati().addServizioApplicativo(paSaAutorizzato);			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
			
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Aggiunta di soggetti all'elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di aggiungere soggetti all'elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public void addErogazioneControlloAccessiAutorizzazionePuntualeSoggetti(ControlloAccessiAutorizzazioneSoggetto body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			
			// Continua da qui, assicurati che l'autorizzazione puntuale sia abilitata guardando al TipoAutorizzazioneEnum
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if ( !TipoAutorizzazione.isAuthenticationRequired(pa.getAutorizzazione())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("L'autenticazione puntuale per soggetti non è abilitata");
			}
			
			final IDSoggetto daAutenticareID = new IDSoggetto(env.tipo_soggetto, body.getSoggetto());
			final Soggetto daAutenticare = Helper.supplyOrNonValida( 
					() -> env.soggettiCore.getSoggetto(daAutenticareID),
					"Soggetto " + body.getSoggetto() + " da autenticare"
				);
			
			final CredenzialeTipo tipoAutenticazione = CredenzialeTipo.toEnumConstant(pa.getAutenticazione());	
			final List<String> tipiSoggettiGestitiProtocollo = env.soggettiCore.getTipiSoggettiGestitiProtocollo(env.tipo_protocollo);
			
			// L'ultimo parametro è da configurare quando utilizzeremo il multitenant 
			final List<org.openspcoop2.core.registry.Soggetto> soggettiCompatibili = env.soggettiCore.getSoggettiFromTipoAutenticazione(tipiSoggettiGestitiProtocollo, null, tipoAutenticazione, null);
			
			if (!Helper.findFirst(soggettiCompatibili, s -> s.getId() == daAutenticare.getId()).isPresent()) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("Il soggetto scelto non supporta l'autenticazione per il gruppo");
			}
			
			if (pa.getSoggetti() == null) pa.setSoggetti(new PortaApplicativaAutorizzazioneSoggetti());
			
			if ( Helper.findFirst(
					pa.getSoggetti().getSoggettoList(),
					sogg -> new IDSoggetto(sogg.getTipo(),sogg.getNome()).equals(daAutenticareID)
					).isPresent()
				) {
				throw FaultCode.CONFLITTO.toException("Soggetto da autorizzare già presente");
			}
			
			env.requestWrapper.overrideParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, pa.getId().toString());
			env.requestWrapper.overrideParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SOGGETTO, daAutenticare.getId().toString());
			if (!env.paHelper.porteAppSoggettoCheckData(TipoOperazione.ADD)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}
			
		
			PortaApplicativaAutorizzazioneSoggetto paSoggetto = new PortaApplicativaAutorizzazioneSoggetto();
			paSoggetto.setTipo(daAutenticare.getTipo());
			paSoggetto.setNome(daAutenticare.getNome());
			
			pa.getSoggetti().addSoggetto(paSoggetto);			
        
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
			
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Aggiunta di ruoli all'elenco dei ruoli autorizzati
     *
     * Questa operazione consente di aggiungere ruoli all'elenco dei ruoli autorizzati
     *
     */
	@Override
    public void addErogazioneControlloAccessiAutorizzazioneRuoli(ControlloAccessiAutorizzazioneRuolo body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");
			
			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if ( !TipoAutorizzazione.isRolesRequired(pa.getAutorizzazione()) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("L'autorizzazione per ruoli non è abilitata per il gruppo scelto");	
			}
			
			final RuoliCore ruoliCore = new RuoliCore(env.stationCore);
			Helper.supplyOrNonValida( 
					() -> ruoliCore.getRuolo(body.getRuolo())
					, "Ruolo " + body.getRuolo()
				);
			
			if(pa.getRuoli()==null){
				pa.setRuoli(new AutorizzazioneRuoli());
			}
			
			FiltroRicercaRuoli filtroRuoli = new FiltroRicercaRuoli();
			filtroRuoli.setContesto(RuoloContesto.PORTA_APPLICATIVA);
			filtroRuoli.setTipologia(RuoloTipologia.QUALSIASI);
			if(TipoAutorizzazione.isInternalRolesRequired(pa.getAutorizzazione()) ){
				filtroRuoli.setTipologia(RuoloTipologia.INTERNO);
			}
			else if(TipoAutorizzazione.isExternalRolesRequired(pa.getAutorizzazione()) ){
				filtroRuoli.setTipologia(RuoloTipologia.ESTERNO);
			}
			
			List<String> ruoliAmmessi = env.stationCore.getAllRuoli(filtroRuoli);
			
			if ( !ruoliAmmessi.contains(body.getRuolo())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("Il ruolo " + body.getRuolo() + "non è tra i ruoli ammissibili per il gruppo"); 
			}
			
			final List<String> ruoliPresenti = pa.getRuoli().getRuoloList().stream().map( r -> r.getNome()).collect(Collectors.toList());
			
			if ( Helper.findFirst( ruoliPresenti, r -> r.equals(body.getRuolo())).isPresent()) {
				throw FaultCode.CONFLITTO.toException("Il ruolo " + body.getRuolo() + " è già associato al gruppo scelto");
			}
			
			if (!env.paHelper.ruoloCheckData(TipoOperazione.ADD, body.getRuolo(), ruoliPresenti)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}

			Ruolo ruolo = new Ruolo();
			ruolo.setNome(body.getRuolo());
			pa.getRuoli().addRuolo(ruolo);

			env.paCore.performUpdateOperation(env.userLogin, false, pa);

			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Aggiunta di scope all'elenco degli scope autorizzati
     *
     * Questa operazione consente di aggiungere scope all'elenco degli scope autorizzati
     *
     */
	@Override
    public void addErogazioneControlloAccessiAutorizzazioneScope(ControlloAccessiAutorizzazioneScope body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);			
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if(pa.getScope()==null) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("L'autorizzazione per scope non è abilitata");
			}
			
			FiltroRicercaScope filtroScope = new FiltroRicercaScope();
			filtroScope.setContesto(ScopeContesto.PORTA_APPLICATIVA);
			filtroScope.setTipologia("");
			
			final List<String> scopeAmmessi = env.stationCore.getAllScope(filtroScope);
			final List<String> scopePresenti = pa.getScope().getScopeList().stream().map(Scope::getNome).collect(Collectors.toList());
			
			if ( !scopeAmmessi.contains(body.getScope())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("Scope " + body.getScope() + " non presente fra gli scope ammissibili."); 
			}
			
			if ( scopePresenti.contains(body.getScope()) ) {
				throw FaultCode.CONFLITTO.toException("Scope " + body.getScope() + " già assegnato al gruppo");
			}
			
			if (!env.paHelper.scopeCheckData(TipoOperazione.ADD, body.getScope(), scopePresenti )) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
				
			}

			// Inserisco il scope nel db
			Scope scope = new Scope();
			scope.setNome(body.getScope());
			pa.getScope().addScope(scope);

			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);

			context.getLogger().info("Invocazione completata con successo");
             
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Aggiunta di una policy di rate limiting
     *
     * Questa operazione consente di aggiungere una policy di rate limiting
     *
     */
	@Override
    public void addErogazioneRateLimitingPolicy(RateLimitingPolicyErogazioneNew body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			org.openspcoop2.core.controllo_traffico.ConfigurazioneGenerale configurazioneControlloTraffico = env.confCore.getConfigurazioneControlloTraffico();

			AttivazionePolicy policy = new AttivazionePolicy();
			policy.setFiltro(new AttivazionePolicyFiltro());
			policy.setGroupBy(new AttivazionePolicyRaggruppamento());
			
			final RuoloPolicy ruoloPorta = RuoloPolicy.APPLICATIVA;
			final String nomePorta = pa.getNome();
			final String idPolicy = ErogazioniApiHelper.getIdInfoPolicy(body.getPolicy());
			
			policy.setIdPolicy(idPolicy);
			
			// Questo lo prendo paro paro dal codice della console.
			InfoPolicy infoPolicy = env.confCore.getInfoPolicy(policy.getIdPolicy());
			int counter = env.confCore.getFreeCounterForGlobalPolicy(infoPolicy.getIdPolicy());
			policy.setIdActivePolicy(infoPolicy.getIdPolicy()+":"+counter);
			
			ErogazioniApiHelper.override(body, env.idSoggetto.toIDSoggetto(), env.requestWrapper);
			
			String errorAttivazione = env.confHelper.readDatiAttivazionePolicyFromHttpParameters(policy, false, TipoOperazione.ADD, infoPolicy);
			if ( !StringUtils.isEmpty(errorAttivazione) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(errorAttivazione));
			}
			
			policy.getFiltro().setEnabled(true);
			policy.getFiltro().setProtocollo(env.tipo_protocollo);
			policy.getFiltro().setRuoloPorta(ruoloPorta);
			policy.getFiltro().setNomePorta(nomePorta);
		
			StringBuffer existsMessage = new StringBuffer();
			if ( ConfigurazioneUtilities.alreadyExists(
					TipoOperazione.ADD,
					env.confCore,
					env.confHelper,
					policy, 
					infoPolicy,
					ruoloPorta, 
					nomePorta, 
					existsMessage, 
					org.openspcoop2.core.constants.Costanti.WEB_NEW_LINE
				)) {
				throw FaultCode.CONFLITTO.toException(StringEscapeUtils.unescapeHtml(existsMessage.toString()));
			}

			if (! env.confHelper.attivazionePolicyCheckData(TipoOperazione.ADD, configurazioneControlloTraffico, policy,infoPolicy, ruoloPorta, nomePorta) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
		
			env.confCore.performCreateOperation(env.userLogin, false, policy);

			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Aggiunta di una regola di correlazione applicativa
     *
     * Questa operazione consente di registrare una regola di correlazione applicativa per la richiesta
     *
     */
	@Override
    public void addErogazioneTracciamentoCorrelazioneApplicativaRichiesta(CorrelazioneApplicativaRichiesta body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			if ( body.getElemento() == null )
				body.setElemento("");
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final Long idPorta = pa.getId();
			
			StringBuffer existsMessage = new StringBuffer();
			if ( ConsoleUtilities.alreadyExistsCorrelazioneApplicativaRichiesta(env.paCore, idPorta, body.getElemento(), 0, existsMessage)) {
				throw FaultCode.CONFLITTO.toException(StringEscapeUtils.unescapeHtml(existsMessage.toString()));
			}
			
			if ( !correlazioneApplicativaRichiestaCheckData(TipoOperazione.ADD, env.requestWrapper, env.paHelper, false, body, idPorta, null) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
									
			CorrelazioneApplicativa ca = pa.getCorrelazioneApplicativa();
			if (ca == null) {
				ca = new CorrelazioneApplicativa();
			}
			ca.addElemento(convert(body));
			pa.setCorrelazioneApplicativa(ca);
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);

					
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Aggiunta di una regola di correlazione applicativa
     *
     * Questa operazione consente di registrare una regola di correlazione applicativa per la risposta
     *
     */
	@Override
    public void addErogazioneTracciamentoCorrelazioneApplicativaRisposta(CorrelazioneApplicativaRisposta body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			Helper.throwIfNull(body);
			
			if ( body.getElemento() == null )
				body.setElemento("");
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final Long idPorta = pa.getId();
			
			
			StringBuffer existsMessage = new StringBuffer();
			if ( ConsoleUtilities.alreadyExistsCorrelazioneApplicativaRisposta(env.paCore, idPorta, body.getElemento(), 0, existsMessage)) {
				throw FaultCode.CONFLITTO.toException(StringEscapeUtils.unescapeHtml(existsMessage.toString()));
			}
			
			if ( !correlazioneApplicativaRispostaCheckData(TipoOperazione.ADD, env.requestWrapper, env.pdHelper, false, body, idPorta, null)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
                       			
			if ( pa.getCorrelazioneApplicativaRisposta() == null)
				pa.setCorrelazioneApplicativaRisposta(new org.openspcoop2.core.config.CorrelazioneApplicativaRisposta());
			
			pa.getCorrelazioneApplicativaRisposta().addElemento(convert(body));
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
			
			context.getLogger().info("Invocazione completata con successo");       
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina applicativi dall'elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di eliminare applicativi dall'elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public void deleteErogazioneControlloAccessiAutorizzazionePuntualeApplicativi(String nome, Integer versione, String applicativoAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (pa.getServiziApplicativiAutorizzati() == null)	pa.setServiziApplicativiAutorizzati(new PortaApplicativaAutorizzazioneServiziApplicativi());
			
			PortaApplicativaAutorizzazioneServizioApplicativo to_remove = Helper.findAndRemoveFirst(pa.getServiziApplicativiAutorizzati().getServizioApplicativoList(), sa -> sa.getNome().equals(applicativoAutorizzato));
			
			if (env.delete_404 && to_remove == null) {
				throw FaultCode.NOT_FOUND.toException("Nessun Applicativo " + applicativoAutorizzato + " è associato al gruppo scelto"); 
			} else if (to_remove != null) {
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}

        
			context.getLogger().info("Invocazione completata con successo");     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina soggetti all'elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di eliminare soggetti all'elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public void deleteErogazioneControlloAccessiAutorizzazionePuntualeSoggetti(String nome, Integer versione, String soggettoAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        

			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (pa.getSoggetti() == null)	pa.setSoggetti(new PortaApplicativaAutorizzazioneSoggetti());
			
			PortaApplicativaAutorizzazioneSoggetto to_remove = Helper.findAndRemoveFirst(pa.getSoggetti().getSoggettoList(), sogg -> sogg.getNome().equals(soggettoAutorizzato));
			
			if (env.delete_404 && to_remove == null) {
				throw FaultCode.NOT_FOUND.toException("Nessun Soggetto " + soggettoAutorizzato + " è associato al gruppo scelto"); 
			} else if ( to_remove != null ) {
			
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}
        
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina ruoli dall'elenco dei ruoli autorizzati
     *
     * Questa operazione consente di eliminare ruoli dall'elenco dei ruoli autorizzati
     *
     */
	@Override
    public void deleteErogazioneControlloAccessiAutorizzazioneRuoli(String nome, Integer versione, String ruoloAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (pa.getRuoli() == null)	pa.setRuoli(new AutorizzazioneRuoli());
			
			Ruolo to_remove = Helper.findAndRemoveFirst(pa.getRuoli().getRuoloList(), r -> r.getNome().equals(ruoloAutorizzato));
			
			if (env.delete_404 && to_remove == null) {
				throw FaultCode.NOT_FOUND.toException("Nessun Ruolo " + ruoloAutorizzato + " è associato al gruppo scelto"); 
			} else if ( to_remove != null ) {
			
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina scope dall'elenco degli scope autorizzati
     *
     * Questa operazione consente di eliminare scope dall'elenco degli scope autorizzati
     *
     */
	@Override
    public void deleteErogazioneControlloAccessiAutorizzazioneScope(String nome, Integer versione, String scopeAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (pa.getScope() == null)	pa.setScope(new AutorizzazioneScope());
			
			Scope to_remove = Helper.findAndRemoveFirst(pa.getScope().getScopeList(), s -> s.getNome().equals(scopeAutorizzato));
			
			if (env.delete_404 && to_remove == null) {
				throw FaultCode.NOT_FOUND.toException("Nessuno Scope " + scopeAutorizzato + " è associato al gruppo scelto"); 
			} else if ( to_remove != null ) {
			
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}
        
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina la policy dall'elenco delle policies attive
     *
     * Questa operazione consente di eliminare la policy dall'elenco delle policies attive
     *
     */
	@Override
    public void deleteErogazioneRateLimitingPolicy(String nome, Integer versione, String idPolicy, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			List<AttivazionePolicy> policies = env.confCore.attivazionePolicyList(null, RuoloPolicy.APPLICATIVA, pa.getNome());
			AttivazionePolicy policy = Helper.findFirst( policies, p -> p.getIdActivePolicy().equals(idPolicy) ).orElse(null);
			
			if ( policy != null ) {
				StringBuffer inUsoMessage = new StringBuffer();
				List<AttivazionePolicy> policyRimosse = new ArrayList<AttivazionePolicy>();
				
				ConfigurazioneUtilities.deleteAttivazionePolicy(
						new ArrayList<AttivazionePolicy>(Arrays.asList( policy )), 
						env.confHelper, 
						env.confCore, 
						env.userLogin, 
						inUsoMessage, 
						org.openspcoop2.core.constants.Costanti.WEB_NEW_LINE, policyRimosse
					);
				
				if ( inUsoMessage.length() > 0 ) {
					throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(inUsoMessage.toString()));
				}
				
			}
			else if ( env.delete_404 ) {
				throw FaultCode.NOT_FOUND.toException("Nessuna policy di rate limiting con id " + idPolicy );
			}
	   
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina la regola di correlazione applicativa dall'elenco di quelle attivate per la richiesta
     *
     * Questa operazione consente di eliminare la regola di correlazione applicativa dall'elenco di quelle attivate per la richiesta
     *
     */
	@Override
    public void deleteErogazioneTracciamentoCorrelazioneApplicativaRichiesta(String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final CorrelazioneApplicativa correlazioneApplicativa = pa.getCorrelazioneApplicativa();
			
			final String searchElemento = elemento.equals("*")
					? ""
				  	: elemento;	
									
			CorrelazioneApplicativaElemento to_del = Helper.evalnull( () -> Helper.findAndRemoveFirst( 
					correlazioneApplicativa.getElementoList(), 
					e -> e.getNome().equals(searchElemento)
				));
			
			if ( to_del != null ) {
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}
			
			else if (env.delete_404) {
				throw FaultCode.NOT_FOUND.toException("Correlazione applicativa per l'elemento " + elemento + " non trovata");
			}
				
			
			context.getLogger().info("Invocazione completata con successo");     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Elimina la regola di correlazione applicativa dall'elenco di quelle attivate per la risposta
     *
     * Questa operazione consente di eliminare la regola di correlazione applicativa dall'elenco di quelle attivate per la risposta
     *
     */
	@Override
    public void deleteErogazioneTracciamentoCorrelazioneApplicativaRisposta(String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			org.openspcoop2.core.config.CorrelazioneApplicativaRisposta correlazioneApplicativa = pa.getCorrelazioneApplicativaRisposta();
			
			final String searchElemento = elemento.equals("*")
					? ""
				  	: elemento;	
									
			CorrelazioneApplicativaRispostaElemento to_del = Helper.evalnull( () -> Helper.findAndRemoveFirst( 
					correlazioneApplicativa.getElementoList(), 
					e -> e.getNome().equals(searchElemento)
				));
			
			if ( to_del != null ) {
				env.paCore.performUpdateOperation(env.userLogin, false, pa);
			}
			
			else if (env.delete_404) {
				throw FaultCode.NOT_FOUND.toException("Correlazione applicativa per l'elemento " + elemento + " non trovata");
			}        
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la policy XACML associata all'autorizzazione
     *
     * Questa operazione consente di ottenere la policy XACML associata all'autorizzazione
     *
     */
	@Override
    public byte[] downloadErogazioneControlloAccessiAutorizzazioneXacmlPolicy(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (pa.getXacmlPolicy() == null)
				throw FaultCode.NOT_FOUND.toException("Xacml policy non assegnata al gruppo scelto");
			
			context.getLogger().info("Invocazione completata con successo");
			return Helper.evalnull( () -> pa.getXacmlPolicy().getBytes() );

		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco delle policy di rate limiting configurate
     *
     * Questa operazione consente di ottenere l'elenco delle policy di rate limiting configurate
     *
     */
	@Override
    public ListaRateLimitingPolicy findAllErogazioneRateLimitingPolicies(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final int idLista = Liste.CONFIGURAZIONE_CONTROLLO_TRAFFICO_ATTIVAZIONE_POLICY;
			final Search ricerca = Helper.setupRicercaPaginata(q, limit, offset, idLista, env.idSoggetto.toIDSoggetto(), env.tipo_protocollo);
			List<AttivazionePolicy> policies = env.confCore.attivazionePolicyList(ricerca, RuoloPolicy.APPLICATIVA, pa.getNome());

			if ( env.findall_404 && policies.isEmpty() ) {
				throw FaultCode.NOT_FOUND.toException("Nessuna policy di rate limiting associata");
			}
			
			ListaRateLimitingPolicy ret = Helper.costruisciListaPaginata(context.getServletRequest().getRequestURI(), offset, limit, ricerca.getNumEntries(idLista), ListaRateLimitingPolicy.class);
			
			policies.forEach( p -> {
				RateLimitingPolicyItem item = new RateLimitingPolicyItem();
				item.setIdentificativo(p.getIdActivePolicy());
				item.setNome(p.getAlias());ret.addItemsItem(item);
				ret.addItemsItem(item);
			});
                              
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco delle regole di correlazione applicativa per la richiesta
     *
     * Questa operazione consente di ottenere l'elenco delle regole di correlazione applicativa per la richiesta
     *
     */
	@Override
    public ListaCorrelazioneApplicativaRichiesta findAllErogazioneTracciamentoCorrelazioneApplicativaRichiesta(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final int idLista = Liste.PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA;
			final Search ricerca = Helper.setupRicercaPaginata(q, limit, offset, idLista, env.idSoggetto.toIDSoggetto(), env.tipo_protocollo);
			
			List<CorrelazioneApplicativaElemento> lista = env.paCore.porteApplicativeCorrelazioneApplicativaList(pa.getId().intValue(), ricerca);

			if ( env.findall_404 && lista.isEmpty() ) {
				throw FaultCode.NOT_FOUND.toException("Nessuna policy di rate limiting associata");
			}
			
			ListaCorrelazioneApplicativaRichiesta ret = Helper.costruisciListaPaginata(context.getServletRequest().getRequestURI(), offset, limit, ricerca.getNumEntries(idLista), ListaCorrelazioneApplicativaRichiesta.class);
			
			lista.forEach( c -> {
				CorrelazioneApplicativaRichiestaItem item = new CorrelazioneApplicativaRichiestaItem();
				item.setElemento( StringUtils.isEmpty(c.getNome()) 
						? "*"
						: c.getNome() 
					);
				
				item.setIdentificazioneTipo(CorrelazioneApplicativaRichiestaEnum.valueOf(c.getIdentificazione().name()));
				ret.addItemsItem(item);
			});

			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco delle regole di correlazione applicativa per la risposta
     *
     * Questa operazione consente di ottenere l'elenco delle regole di correlazione applicativa per la risposta
     *
     */
	@Override
    public ListaCorrelazioneApplicativaRisposta findAllErogazioneTracciamentoCorrelazioneApplicativaRisposta(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio, String q, Integer limit, Integer offset) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final int idLista = Liste.PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RISPOSTA;
			final Search ricerca = Helper.setupRicercaPaginata(q, limit, offset, idLista, env.idSoggetto.toIDSoggetto(), env.tipo_protocollo);
			
			List<CorrelazioneApplicativaRispostaElemento> lista = env.paCore.porteApplicativeCorrelazioneApplicativaRispostaList(pa.getId().intValue(), ricerca);

			if ( env.findall_404 && lista.isEmpty() ) {
				throw FaultCode.NOT_FOUND.toException("Nessuna policy di rate limiting associata");
			}
			
			ListaCorrelazioneApplicativaRisposta ret = Helper.costruisciListaPaginata(context.getServletRequest().getRequestURI(), offset, limit, ricerca.getNumEntries(idLista), ListaCorrelazioneApplicativaRisposta.class);
			
			lista.forEach( c -> {
				CorrelazioneApplicativaRispostaItem item = new CorrelazioneApplicativaRispostaItem();
				item.setElemento( StringUtils.isEmpty(c.getNome()) 
						? "*"
						: c.getNome() 
					);				item.setIdentificazioneTipo(CorrelazioneApplicativaRispostaEnum.valueOf(c.getIdentificazione().name()));
				ret.addItemsItem(item);
			});        
			
			context.getLogger().info("Invocazione completata con successo");
        	return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la configurazione relativa al caching delle risposte
     *
     * Questa operazione consente di ottenere la configurazione relativa al caching delle risposte
     *
     */
	@Override
    public CachingRisposta getErogazioneCachingRisposta(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			

			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			CachingRisposta ret = ErogazioniApiHelper.buildCachingRisposta(pa.getResponseCaching());
                      
        
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la configurazione relativa all'autenticazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di ottenere la configurazione relativa all'autenticazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public ControlloAccessiAutenticazione getErogazioneControlloAccessiAutenticazione(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final APIImplAutenticazione autRet = new APIImplAutenticazione();
			autRet.setOpzionale(Helper.statoFunzionalitaConfToBool( pa.getAutenticazioneOpzionale() ));
			
			if ( TipoAutenticazione.toEnumConstant(pa.getAutenticazione()) == null ) {
				autRet.setTipo(TipoAutenticazioneEnum.CUSTOM);
				autRet.setNome(pa.getAutenticazione());
			}
			else {
				autRet.setTipo( Enums.dualizeMap(Enums.tipoAutenticazioneFromRest).get(TipoAutenticazione.toEnumConstant(pa.getAutenticazione())) );
			}
			
			ControlloAccessiAutenticazioneToken token = Helper.evalnull( () -> pa.getGestioneToken().getAutenticazione() ) != null
					? ErogazioniApiHelper.fromGestioneTokenAutenticazione(pa.getGestioneToken().getAutenticazione())
					: null;
					
			ControlloAccessiAutenticazione ret = new ControlloAccessiAutenticazione();
			ret.setAutenticazione(autRet);
			ret.setToken(token);
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Restituisce la configurazione relativa all'autorizzazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di ottenere la configurazione relativa all'autorizzazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneView getErogazioneControlloAccessiAutorizzazione(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");    
			

			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			ControlloAccessiAutorizzazioneView ret = ErogazioniApiHelper.controlloAccessiAutorizzazioneFromPA(pa);
			
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Restituisce l'elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di ottenere l'elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneApplicativi getErogazioneControlloAccessiAutorizzazionePuntualeApplicativi(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			ControlloAccessiAutorizzazioneApplicativi ret = new ControlloAccessiAutorizzazioneApplicativi();
			ret.setApplicativi(pa.getServizioApplicativoList().stream().map( saPA -> saPA.getNome()).collect(Collectors.toList()));
		
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di ottenere l'elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneSoggetti getErogazioneControlloAccessiAutorizzazionePuntualeSoggetti(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			ControlloAccessiAutorizzazioneSoggetti ret = new ControlloAccessiAutorizzazioneSoggetti();
			List<String> soggetti = Helper.evalnull( () -> pa.getSoggetti().getSoggettoList().stream().map( s -> s.getNome()).collect(Collectors.toList()) );
					
			ret.setSoggetti(soggetti);
        
			context.getLogger().info("Invocazione completata con successo");
			return ret;
 
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco dei ruoli autorizzati
     *
     * Questa operazione consente di ottenere l'elenco dei ruoli autorizzati
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneRuoli getErogazioneControlloAccessiAutorizzazioneRuoli(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final ControlloAccessiAutorizzazioneRuoli ret = new ControlloAccessiAutorizzazioneRuoli(); 
                        
			ret.setRuoli(Helper.evalnull( () -> pa.getRuoli().getRuoloList().stream().map(Ruolo::getNome).collect(Collectors.toList()) ));
        
			context.getLogger().info("Invocazione completata con successo");
        	return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce l'elenco degli scope autorizzati
     *
     * Questa operazione consente di ottenere l'elenco degli scope autorizzati
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneScopes getErogazioneControlloAccessiAutorizzazioneScope(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     

			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);	
			final ControlloAccessiAutorizzazioneScopes ret = new ControlloAccessiAutorizzazioneScopes();
			
			ret.setScope(Helper.evalnull( () -> pa.getScope().getScopeList().stream().map(Scope::getNome).collect(Collectors.toList()) ));
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la configurazione relativa alla gestione dei token per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di ottenere la configurazione relativa alla gestione dei token per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public ControlloAccessiGestioneToken getErogazioneControlloAccessiGestioneToken(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
		
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);	
			
			final ControlloAccessiGestioneToken ret = new ControlloAccessiGestioneToken();
			final GestioneToken paToken = pa.getGestioneToken();
			
			ret.setAbilitato(paToken != null);
			if (paToken != null) {
				ret.setIntrospection(StatoFunzionalitaConWarningEnum.fromValue(paToken.getIntrospection().getValue()));
				ret.setPolicy(paToken.getPolicy());
				ret.setTokenForward( Helper.statoFunzionalitaConfToBool( paToken.getForward() ));
				ret.setTokenOpzionale( Helper.statoFunzionalitaConfToBool( paToken.getTokenOpzionale() ));
				ret.setUserInfo(StatoFunzionalitaConWarningEnum.fromValue( paToken.getUserInfo().getValue() ));
				ret.setValidazioneJwt(StatoFunzionalitaConWarningEnum.fromValue( paToken.getValidazione().getValue() ));
			}
        
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce le informazioni sulla configurazione CORS associata all'erogazione
     *
     * Questa operazione consente di ottenere le informazioni sulla configurazione CORS associata all'erogazione identificata dal nome e dalla versione
     *
     */
	@Override
    public GestioneCors getErogazioneGestioneCORS(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			final ErogazioniEnv env = new ErogazioniEnv(context.getServletRequest(), profilo, soggetto, context);
			final AccordoServizioParteSpecifica asps = Helper.supplyOrNotFound( () -> ErogazioniApiHelper.getServizioIfErogazione(tipoServizio , nome, versione, env.idSoggetto.toIDSoggetto(), env), "Erogazione");
			final IdServizio idAsps = new IdServizio(env.idServizioFactory.getIDServizioFromAccordo(asps), asps.getId());
			
			final IDPortaApplicativa idPa = Helper.supplyOrNotFound( () -> ErogazioniApiHelper.getIDGruppoPADefault( idAsps, env.apsCore ),  "Gruppo default per l'erogazione scelta" );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(idPa);
			final CorsConfigurazione paConf = pa.getGestioneCors();
			
			final GestioneCors ret = ErogazioniApiHelper.convert(paConf);
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;

		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce il dettaglio di una policy di rate limiting
     *
     * Questa operazione consente di ottenere il dettaglio di una policy di rate limiting
     *
     */
	@Override
    public RateLimitingPolicyErogazioneView getErogazioneRateLimitingPolicy(String nome, Integer versione, String idPolicy, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			List<AttivazionePolicy> policies = env.confCore.attivazionePolicyList(null, RuoloPolicy.APPLICATIVA, pa.getNome());
			AttivazionePolicy policy = Helper.findFirst( policies, p -> p.getIdActivePolicy().equals(idPolicy) ).orElse(null);
			
			if ( policy == null ) 
				throw FaultCode.NOT_FOUND.toException("Nessuna policy di rate limiting con id " + idPolicy );
			
			InfoPolicy infoPolicy = env.confCore.getInfoPolicy(policy.getIdPolicy());
						
			RateLimitingPolicyErogazioneView ret = ErogazioniApiHelper.convert(policy, infoPolicy, new RateLimitingPolicyErogazioneView());
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la configurazione relativa alla registrazione dei messaggi
     *
     * Questa operazione consente di ottenere la configurazione relativa alla registrazione dei messaggi
     *
     */
	@Override
    public RegistrazioneMessaggi getErogazioneRegistrazioneMessaggi(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);	
			final RegistrazioneMessaggi ret = ErogazioniApiHelper.fromDumpConfigurazione(pa.getDump());
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Restituisce l'indicazione sullo stato del gruppo
     *
     * Questa operazione consente di ottenere lo stato attuale del gruppo
     *
     */
	@Override
    public ApiImplStato getErogazioneStato(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
		
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final ApiImplStato ret = new ApiImplStato();
			ret.setAbilitato(Helper.statoFunzionalitaConfToBool(pa.getStato()));
                        
		
			context.getLogger().info("Invocazione completata con successo");
        	return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce il dettaglio di una regola di correlazione applicativa per la richiesta
     *
     * Questa operazione consente di ottenere il dettaglio di una regola di correlazione applicativa per la richiesta
     *
     */
	@Override
    public CorrelazioneApplicativaRichiesta getErogazioneTracciamentoCorrelazioneApplicativaRichiesta(String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final String searchElemento = elemento.equals("*")
					? ""
					: elemento;			
			List<CorrelazioneApplicativaElemento> lista = Helper.evalnull( () -> pa.getCorrelazioneApplicativa().getElementoList() );
            
			Optional<CorrelazioneApplicativaElemento> el = Helper.findFirst( lista, c -> c.getNome().equals(searchElemento) );
			
			if ( !el.isPresent() )
				throw FaultCode.NOT_FOUND.toException("CorrelazioneApplicativaRichiesta per l'elemento " + elemento + " non presente");
			
			CorrelazioneApplicativaRichiesta ret = ErogazioniApiHelper.convert(el.get());
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce il dettaglio di una regola di correlazione applicativa per la risposta
     *
     * Questa operazione consente di ottenere il dettaglio di una regola di correlazione applicativa per la risposta
     *
     */
	@Override
    public CorrelazioneApplicativaRisposta getErogazioneTracciamentoCorrelazioneApplicativaRisposta(String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");    
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final String searchElemento = elemento.equals("*")
					? ""
					: elemento;			
			
			List<CorrelazioneApplicativaRispostaElemento> lista = Helper.evalnull( () -> pa.getCorrelazioneApplicativaRisposta().getElementoList() );
            
			Optional<CorrelazioneApplicativaRispostaElemento> el = Helper.findFirst( lista, c -> c.getNome().equals(searchElemento) );
			
			if ( !el.isPresent() )
				throw FaultCode.NOT_FOUND.toException("CorrelazioneApplicativaRisposta per l'elemento " + elemento + " non presente");
			
			CorrelazioneApplicativaRisposta ret = ErogazioniApiHelper.convert(el.get());
			
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Restituisce la configurazione relativa alla validazione dei contenuti applicativi
     *
     * Questa operazione consente di ottenere la configurazione relativa alla validazione dei contenuti applicativi
     *
     */
	@Override
    public Validazione getErogazioneValidazione(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
            
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final Validazione ret = ErogazioniApiHelper.fromValidazioneContenutiApplicativi(pa.getValidazioneContenutiApplicativi());
        
			context.getLogger().info("Invocazione completata con successo");
			return ret;
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Consente di modificare la configurazione relativa al caching delle risposte
     *
     * Questa operazione consente di aggiornare la configurazione relativa al caching delle risposte
     *
     */
	@Override
    public void updateErogazioneCachingRisposta(CachingRisposta body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			// Così prendi le impostazioni di default.
			//  Configurazione configurazione = confCore.getConfigurazioneGenerale();
			//  ResponseCachingConfigurazioneGenerale responseCachingGenerale = configurazione.getResponseCaching();
			//  responseCachingGenerale.getConfigurazione();		
			
			if (body.isMaxResponseSize()) {
				if (body.getMaxResponseSizeKb() == null)
					throw FaultCode.RICHIESTA_NON_VALIDA.toException("Devi specificare il campo MaxResponseSizeKb");
			}
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
		
			if (!env.paHelper.checkDataConfigurazioneResponseCachingPorta(TipoOperazione.OTHER, true, body.getStato().toString())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
			
			ResponseCachingConfigurazione newConfigurazione = ErogazioniApiHelper.buildResponseCachingConfigurazione(body, env.paHelper);
			
			pa.setResponseCaching(newConfigurazione);
			env.paCore.performUpdateOperation(env.userLogin, false, pa);			
			
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Consente di modificare la configurazione relativa all'autenticazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di aggiornare la configurazione relativa all'autenticazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public void updateErogazioneControlloAccessiAutenticazione(ControlloAccessiAutenticazione body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa newPa = env.paCore.getPortaApplicativa(env.idPa);
			final PortaApplicativa oldPa = env.paCore.getPortaApplicativa(env.idPa);			

			ErogazioniApiHelper.fillPortaApplicativa(body, newPa);
			
			if (! ErogazioniApiHelper.controlloAccessiCheckPA(env, oldPa, newPa)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}
		
			env.paCore.performUpdateOperation(env.userLogin, false, newPa);
        
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Consente di modificare la configurazione relativa all'autorizzazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di aggiornare la configurazione relativa all'autorizzazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public void updateErogazioneControlloAccessiAutorizzazione(ControlloAccessiAutorizzazione body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);					
			final PortaApplicativa newPa = env.paCore.getPortaApplicativa(env.idPa);		
			
			ErogazioniApiHelper.fillPortaApplicativa(body, newPa);
			
			if (!ErogazioniApiHelper.controlloAccessiCheckPA(env, pa, newPa)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}
			
			env.paCore.performUpdateOperation(env.userLogin, false, newPa);
                        
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }

	/**
     * Consente di modificare la configurazione relativa alla gestione dei token per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di aggiornare la configurazione relativa alla gestione dei token per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public void updateErogazioneControlloAccessiGestioneToken(ControlloAccessiGestioneToken body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");
			
			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa oldPa = env.paCore.getPortaApplicativa(env.idPa);
			final PortaApplicativa newPa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (body.isAbilitato())
				newPa.setGestioneToken(ErogazioniApiHelper.buildGestioneToken(body, newPa, false, env.paHelper, env));
			else
				newPa.setGestioneToken(null);
			
			if ( !ErogazioniApiHelper.controlloAccessiCheckPA(env, oldPa, newPa) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(env.pd.getMessage());
			}
			
			env.paCore.performUpdateOperation(env.userLogin, false, newPa);
			
			context.getLogger().info("Invocazione completata con successo");
 
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Consente di modificare la configurazione CORS associata all'erogazione
     *
     * Questa operazione consente di aggiornare la configurazione CORS associata all'erogazione identificata dal nome e dalla versione
     *
     */
	@Override
    public void updateErogazioneGestioneCORS(GestioneCors body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);

			final ErogazioniEnv env = new ErogazioniEnv(context.getServletRequest(), profilo, soggetto, context);
			final AccordoServizioParteSpecifica asps = Helper.supplyOrNotFound( () -> ErogazioniApiHelper.getServizioIfErogazione(tipoServizio , nome, versione, env.idSoggetto.toIDSoggetto(), env), "Erogazione");
			final IdServizio idAsps = new IdServizio(env.idServizioFactory.getIDServizioFromAccordo(asps), asps.getId());
			
			final IDPortaApplicativa idPa = Helper.supplyOrNotFound( () -> ErogazioniApiHelper.getIDGruppoPADefault( idAsps, env.apsCore ),  "Gruppo default per l'erogazione scelta" );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(idPa);
			final CorsConfigurazione oldConf = pa.getGestioneCors();
			
			
			if (body.isRidefinito())
				pa.setGestioneCors(ErogazioniApiHelper.buildCorsConfigurazione(body, env, oldConf));
			else
				pa.setGestioneCors(null);
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
		    
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Modifica i dati di una policy di rate limiting
     *
     * Questa operazione consente di aggiornare i dati relativi ad una policy di rate limiting
     *
     */
	@Override
    public void updateErogazioneRateLimitingPolicy(RateLimitingPolicyBaseErogazione body, String nome, Integer versione, String idPolicy, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);            
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);		
			
			AttivazionePolicy policy = Helper.supplyOrNotFound( 
					() -> env.confCore.getAttivazionePolicy(idPolicy),
					"Rate Limiting Policy con id " + idPolicy
				);
			InfoPolicy infoPolicy = env.confCore.getInfoPolicy(policy.getIdPolicy());
			

			org.openspcoop2.core.controllo_traffico.ConfigurazioneGenerale configurazioneControlloTraffico = env.confCore.getConfigurazioneControlloTraffico();
			
			final RuoloPolicy ruoloPorta = RuoloPolicy.APPLICATIVA;
			final String nomePorta = pa.getNome();
 
			ErogazioniApiHelper.override(body, env.idSoggetto.toIDSoggetto(), env.requestWrapper);

			String errorAttivazione = env.confHelper.readDatiAttivazionePolicyFromHttpParameters(policy, false, TipoOperazione.CHANGE, infoPolicy);
			if ( !StringUtils.isEmpty(errorAttivazione) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(errorAttivazione));
			}
			
			if (! env.confHelper.attivazionePolicyCheckData(TipoOperazione.CHANGE, configurazioneControlloTraffico, policy,infoPolicy, ruoloPorta, nomePorta) ) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}			

			
			env.confCore.performUpdateOperation(env.userLogin, false, policy);

			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Consente di modificare la configurazione relativa alla registrazione dei messaggi
     *
     * Questa operazione consente di aggiornare la configurazione relativa alla registrazione dei messaggi
     *
     */
	@Override
    public void updateErogazioneRegistrazioneMessaggi(RegistrazioneMessaggi body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			if (body.isRidefinito())
				pa.setDump(ErogazioniApiHelper.buildDumpConfigurazione(body, true, env));
			else
				pa.setDump(null);
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);

			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Consente di modificare lo stato del gruppo
     *
     * Questa operazione consente di aggiornare lo stato del gruppo
     *
     */
	@Override
    public void updateErogazioneStato(ApiImplStato body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     		
			
			Helper.throwIfNull(body);	
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			IDPortaApplicativa oldIDPortaApplicativaForUpdate = new IDPortaApplicativa();
			oldIDPortaApplicativaForUpdate.setNome(pa.getNome());
			pa.setOldIDPortaApplicativaForUpdate(oldIDPortaApplicativaForUpdate);
			
			pa.setStato(Helper.boolToStatoFunzionalitaConf(body.isAbilitato()));
						
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
			context.getLogger().info("Invocazione completata con successo");
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Modifica i dati di una regola di correlazione applicativa per la richiesta
     *
     * Questa operazione consente di aggiornare i dati relativi ad una regola di correlazione applicativa per la richiesta
     *
     */
	@Override
    public void updateErogazioneTracciamentoCorrelazioneApplicativaRichiesta(CorrelazioneApplicativaRichiesta body, String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);
			
			if ( body.getElemento() == null )
				body.setElemento("");
			
			final String searchElemento = elemento.equals("*")
					? ""
					: elemento;
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final Long idPorta = pa.getId();
			
			if (pa.getCorrelazioneApplicativa() == null)
				pa.setCorrelazioneApplicativa(new org.openspcoop2.core.config.CorrelazioneApplicativa());
			
			final List<CorrelazioneApplicativaElemento> correlazioni = pa.getCorrelazioneApplicativa().getElementoList();
			final CorrelazioneApplicativaElemento oldElem = Helper.findAndRemoveFirst(correlazioni, c -> c.getNome().equals(searchElemento));
			
			if ( oldElem == null ) 
				throw FaultCode.NOT_FOUND.toException("Correlazione Applicativa Richiesta per l'elemento " + elemento + " non trovata ");
			
			if ( !correlazioneApplicativaRichiestaCheckData(TipoOperazione.CHANGE, env.requestWrapper, env.pdHelper, false, body, idPorta, oldElem.getId())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
			
			correlazioni.add(convert(body));
                        
			pa.getCorrelazioneApplicativa().setElementoList(correlazioni);
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
			
			context.getLogger().info("Invocazione completata con successo");
         
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Modifica i dati di una regola di correlazione applicativa per la risposta
     *
     * Questa operazione consente di aggiornare i dati relativi ad una regola di correlazione applicativa per la risposta
     *
     */
	@Override
    public void updateErogazioneTracciamentoCorrelazioneApplicativaRisposta(CorrelazioneApplicativaRisposta body, String nome, Integer versione, String elemento, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
			
			Helper.throwIfNull(body);
			
			if ( body.getElemento() == null )
				body.setElemento("");
			
			final String searchElemento = elemento.equals("*")
					? ""
					: elemento;
                        
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			final Long idPorta = pa.getId();
			
			if (pa.getCorrelazioneApplicativaRisposta() == null)
				pa.setCorrelazioneApplicativaRisposta(new org.openspcoop2.core.config.CorrelazioneApplicativaRisposta());
			
			final List<CorrelazioneApplicativaRispostaElemento> correlazioni = pa.getCorrelazioneApplicativaRisposta().getElementoList();
			final CorrelazioneApplicativaRispostaElemento oldElem = Helper.findAndRemoveFirst(correlazioni, c -> c.getNome().equals(searchElemento));
			
			if ( oldElem == null ) 
				throw FaultCode.NOT_FOUND.toException("Correlazione Applicativa Risposta per l'elemento " + elemento + " non trovata ");
			
			if ( !correlazioneApplicativaRispostaCheckData(TipoOperazione.CHANGE, env.requestWrapper, env.pdHelper, false, body, idPorta, oldElem.getId())) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(env.pd.getMessage()));
			}
			
			correlazioni.add(convert(body));
                        
			pa.getCorrelazioneApplicativaRisposta().setElementoList(correlazioni);
			
			env.paCore.performUpdateOperation(env.userLogin, false, pa);
        
			context.getLogger().info("Invocazione completata con successo");
        
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
    /**
     * Consente di modificare la configurazione relativa alla validazione dei contenuti applicativi
     *
     * Questa operazione consente di aggiornare la configurazione relativa alla validazione dei contenuti applicativi
     *
     */
	@Override
    public void updateErogazioneValidazione(Validazione body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo, String tipoServizio) {
		IContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");
			
			Helper.throwIfNull(body);	
			
			final ErogazioniConfEnv env = new ErogazioniConfEnv(context.getServletRequest(), profilo, soggetto, context, nome, versione, gruppo, tipoServizio );
			final PortaApplicativa pa = env.paCore.getPortaApplicativa(env.idPa);
			
			final String stato = Helper.evalnull( () -> body.getStato().toString());
			final String tipoValidazione = Helper.evalnull( () -> body.getTipo().toString() );
			
			env.requestWrapper.overrideParameter(CostantiControlStation.PARAMETRO_PORTE_XSD, stato);
			if (!env.paHelper.validazioneContenutiCheck(TipoOperazione.OTHER, false)) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException(StringEscapeUtils.unescapeHtml(  env.pd.getMessage() ));
			}
			
			final ValidazioneContenutiApplicativi vx = new ValidazioneContenutiApplicativi();
			// Imposto Mtom al valore eventualmente già presente nel db.
			vx.setAcceptMtomMessage( Helper.evalnull( () -> pa.getValidazioneContenutiApplicativi().getAcceptMtomMessage()) );
			vx.setStato( StatoFunzionalitaConWarning.toEnumConstant(stato) );
			vx.setTipo( ValidazioneContenutiApplicativiTipo.toEnumConstant(tipoValidazione) );
			
			pa.setValidazioneContenutiApplicativi(vx);

			env.paCore.performUpdateOperation(env.userLogin, false, pa);
        
			context.getLogger().info("Invocazione completata con successo");
     
		}
		catch(javax.ws.rs.WebApplicationException e) {
			context.getLogger().error("Invocazione terminata con errore '4xx': %s",e, e.getMessage());
			throw e;
		}
		catch(Throwable e) {
			context.getLogger().error("Invocazione terminata con errore: %s",e, e.getMessage());
			throw FaultCode.ERRORE_INTERNO.toException(e);
		}
    }
    
}

