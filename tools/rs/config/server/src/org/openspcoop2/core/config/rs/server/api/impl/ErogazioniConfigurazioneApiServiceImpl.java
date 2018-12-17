package org.openspcoop2.core.config.rs.server.api.impl;

import org.openspcoop2.core.config.rs.server.api.ErogazioniConfigurazioneApi;
import org.openspcoop2.core.config.rs.server.model.APIImplAutenticazione;
import org.openspcoop2.core.config.rs.server.model.ApiImplStato;
import org.openspcoop2.core.config.rs.server.model.ControlloAccessiAutenticazione;
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
import org.openspcoop2.core.config.rs.server.model.GestioneCors;
import org.openspcoop2.core.config.rs.server.model.ProfiloEnum;
import org.openspcoop2.utils.jaxrs.fault.FaultCode;
import org.openspcoop2.utils.jaxrs.impl.AuthorizationConfig;
import org.openspcoop2.utils.jaxrs.impl.AuthorizationManager;
import org.openspcoop2.utils.jaxrs.impl.BaseImpl;
import org.openspcoop2.utils.jaxrs.impl.ServiceContext;
/**
 * GovWay Config API
 *
 * <p>Servizi per la configurazione di GovWay
 *
 */
public class ErogazioniConfigurazioneApiServiceImpl extends BaseImpl implements ErogazioniConfigurazioneApi {

	public ErogazioniConfigurazioneApiServiceImpl(){
		super(org.slf4j.LoggerFactory.getLogger(ErogazioniConfigurazioneApiServiceImpl.class));
	}

	private AuthorizationConfig getAuthorizationConfig() throws Exception{
		// TODO: Implement ...
		throw new Exception("NotImplemented");
	}

    /**
     * Aggiunta di applicativi all&#x27;elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di aggiungere applicativi all&#x27;elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public void addControlloAccessiAutorizzazionePuntualeApplicativi(ControlloAccessiAutorizzazioneApplicativo body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Aggiunta di soggetti all&#x27;elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di aggiungere soggetti all&#x27;elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public void addControlloAccessiAutorizzazionePuntualeSoggetti(ControlloAccessiAutorizzazioneSoggetto body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Aggiunta di ruoli all&#x27;elenco dei ruoli autorizzati
     *
     * Questa operazione consente di aggiungere ruoli all&#x27;elenco dei ruoli autorizzati
     *
     */
	@Override
    public void addControlloAccessiAutorizzazioneRuoli(ControlloAccessiAutorizzazioneRuolo body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Aggiunta di scope all&#x27;elenco degli scope autorizzati
     *
     * Questa operazione consente di aggiungere scope all&#x27;elenco degli scope autorizzati
     *
     */
	@Override
    public void addControlloAccessiAutorizzazioneScope(ControlloAccessiAutorizzazioneScope body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Elimina applicativi dall&#x27;elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di eliminare applicativi dall&#x27;elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public void deleteControlloAccessiAutorizzazionePuntualeApplicativi(String nome, Integer versione, String applicativoAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Elimina soggetti all&#x27;elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di eliminare soggetti all&#x27;elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public void deleteControlloAccessiAutorizzazionePuntualeSoggetti(String nome, Integer versione, String soggettoAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Elimina ruoli dall&#x27;elenco dei ruoli autorizzati
     *
     * Questa operazione consente di eliminare ruoli dall&#x27;elenco dei ruoli autorizzati
     *
     */
	@Override
    public void deleteControlloAccessiAutorizzazioneRuoli(String nome, Integer versione, String ruoloAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Elimina scope dall&#x27;elenco degli scope autorizzati
     *
     * Questa operazione consente di eliminare scope dall&#x27;elenco degli scope autorizzati
     *
     */
	@Override
    public void deleteControlloAccessiAutorizzazioneScope(String nome, Integer versione, String scopeAutorizzato, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Restituisce la policy XACML associata all&#x27;autorizzazione
     *
     * Questa operazione consente di ottenere la policy XACML associata all&#x27;autorizzazione
     *
     */
	@Override
    public byte[] downloadControlloAccessiAutorizzazioneXacmlPolicy(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce la configurazione relativa all&#x27;autenticazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di ottenere la configurazione relativa all&#x27;autenticazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public ControlloAccessiAutenticazione getControlloAccessiAutenticazione(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce la configurazione relativa all&#x27;autorizzazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di ottenere la configurazione relativa all&#x27;autorizzazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneView getControlloAccessiAutorizzazione(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce l&#x27;elenco degli applicativi autorizzati puntualmente
     *
     * Questa operazione consente di ottenere l&#x27;elenco degli applicativi autorizzati puntualmente
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneApplicativi getControlloAccessiAutorizzazionePuntualeApplicativi(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce l&#x27;elenco dei soggetti autorizzati puntualmente
     *
     * Questa operazione consente di ottenere l&#x27;elenco dei soggetti autorizzati puntualmente
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneSoggetti getControlloAccessiAutorizzazionePuntualeSoggetti(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce l&#x27;elenco dei ruoli autorizzati
     *
     * Questa operazione consente di ottenere l&#x27;elenco dei ruoli autorizzati
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneRuoli getControlloAccessiAutorizzazioneRuoli(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce l&#x27;elenco degli scope autorizzati
     *
     * Questa operazione consente di ottenere l&#x27;elenco degli scope autorizzati
     *
     */
	@Override
    public ControlloAccessiAutorizzazioneScopes getControlloAccessiAutorizzazioneScope(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
    public ControlloAccessiGestioneToken getControlloAccessiGestioneToken(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce le informazioni sulla configurazione CORS associata all&#x27;erogazione
     *
     * Questa operazione consente di ottenere le informazioni sulla configurazione CORS associata all&#x27;erogazione identificata dal nome e dalla versione
     *
     */
	@Override
    public GestioneCors getGestioneCORS(String nome, Integer versione, ProfiloEnum profilo, String soggetto) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Restituisce l&#x27;indicazione sullo stato del gruppo
     *
     * Questa operazione consente di ottenere lo stato attuale del gruppo
     *
     */
	@Override
    public ApiImplStato getStato(String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
			context.getLogger().info("Invocazione completata con successo");
        return null;
     
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
     * Consente di modificare la configurazione relativa all&#x27;autenticazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di aggiornare la configurazione relativa all&#x27;autenticazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public void updateControlloAccessiAutenticazione(ControlloAccessiAutenticazione body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			APIImplAutenticazione impl_auth = new APIImplAutenticazione();
			//impl_auth.
        // TODO: Implement...
        
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
     * Consente di modificare la configurazione relativa all&#x27;autorizzazione per quanto concerne il controllo degli accessi
     *
     * Questa operazione consente di aggiornare la configurazione relativa all&#x27;autorizzazione per quanto concerne il controllo degli accessi
     *
     */
	@Override
    public void updateControlloAccessiAutorizzazione(ControlloAccessiAutorizzazione body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			
        // TODO: Implement...
        
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
    public void updateControlloAccessiGestioneToken(ControlloAccessiGestioneToken body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			//Questa è la schermata GestioneToken
		//	body.set
			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
     * Consente di modificare la configurazione CORS associata all&#x27;erogazione
     *
     * Questa operazione consente di aggiornare la configurazione CORS associata all&#x27;erogazione identificata dal nome e dalla versione
     *
     */
	@Override
    public void updateGestioneCORS(GestioneCors body, String nome, Integer versione, ProfiloEnum profilo, String soggetto) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
        // TODO: Implement...
        
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
    public void updateStato(ApiImplStato body, String nome, Integer versione, ProfiloEnum profilo, String soggetto, String gruppo) {
		ServiceContext context = this.getContext();
		try {
			context.getLogger().info("Invocazione in corso ...");     

			AuthorizationManager.authorize(context, getAuthorizationConfig());
			context.getLogger().debug("Autorizzazione completata con successo");     
                        
			
        // TODO: Implement...
        
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

