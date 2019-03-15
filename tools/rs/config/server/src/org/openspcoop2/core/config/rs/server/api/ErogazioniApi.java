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
package org.openspcoop2.core.config.rs.server.api;

import org.openspcoop2.core.config.rs.server.model.ApiImplAllegato;
import org.openspcoop2.core.config.rs.server.model.ApiImplInformazioniGenerali;
import org.openspcoop2.core.config.rs.server.model.ApiImplInformazioniGeneraliView;
import org.openspcoop2.core.config.rs.server.model.ApiImplUrlInvocazioneView;
import org.openspcoop2.core.config.rs.server.model.ApiImplVersioneApi;
import org.openspcoop2.core.config.rs.server.model.ApiImplVersioneApiView;
import org.openspcoop2.core.config.rs.server.model.Connettore;
import org.openspcoop2.core.config.rs.server.model.ErogazioneViewItem;
import java.io.File;
import org.openspcoop2.core.config.rs.server.model.ListaApiImplAllegati;
import org.openspcoop2.core.config.rs.server.model.ListaErogazioni;
import org.openspcoop2.core.config.rs.server.model.Problem;
import org.openspcoop2.utils.service.beans.ProfiloEnum;
import org.openspcoop2.core.config.rs.server.model.TipoApiEnum;

import javax.ws.rs.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;

/**
 * GovWay Config API
 *
 * <p>Servizi per la configurazione di GovWay
 *
 */
@Path("/")
public interface ErogazioniApi  {

    /**
     * Creazione di un'erogazione di API
     *
     * Questa operazione consente di creare una erogazione di API
     *
     */
    @POST
    @Path("/erogazioni")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Creazione di un'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Erogazione creata con successo"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "409", description = "Conflict (L'entità che si vuole creare risulta già esistente)", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void createErogazione(@Valid Object body, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto);

    /**
     * Creazione di un allegato nell'erogazione di API
     *
     * Questa operazione consente di aggiungere un allegato all'erogazione di API identificata dal nome e dalla versione
     *
     */
    @POST
    @Path("/erogazioni/{nome}/{versione}/allegati")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Creazione di un allegato nell'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Allegato creato con successo"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "409", description = "Conflict (L'entità che si vuole creare risulta già esistente)", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void createErogazioneAllegato(@Valid ApiImplAllegato body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Elimina un'erogazione di api
     *
     * Questa operazione consente di eliminare un'erogazione di API identificata dal nome e dalla versione
     *
     */
    @DELETE
    @Path("/erogazioni/{nome}/{versione}")
    @Produces({ "application/problem+json" })
    @Operation(summary = "Elimina un'erogazione di api", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Erogazione di API eliminata con successo"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void deleteErogazione(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Elimina un allegato dall'erogazione
     *
     * Questa operazione consente di eliminare un'allegato dell'erogazione di API identificata dal nome e dalla versione
     *
     */
    @DELETE
    @Path("/erogazioni/{nome}/{versione}/allegati/{nome_allegato}")
    @Produces({ "application/problem+json" })
    @Operation(summary = "Elimina un allegato dall'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Allegato eliminato con successo"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void deleteErogazioneAllegato(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @PathParam("nome_allegato") String nomeAllegato, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce l'allegato di una erogazione
     *
     * Questa operazione consente di ottenere l'allegato di un'erogazione di API identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/allegati/{nome_allegato}/download")
    @Produces({ "application/_*", "text/_*", "application/problem+json" })
    @Operation(summary = "Restituisce l'allegato di una erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Allegato dell'erogazione restituito con successo", content = @Content(schema = @Schema(implementation = File.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public byte[] downloadErogazioneAllegato(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @PathParam("nome_allegato") String nomeAllegato, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Elenco allegati di un'erogazione di API
     *
     * Questa operazione consente di ottenere gli allegati di un'erogazione di API identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/allegati")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Elenco allegati di un'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Ricerca eseguita correttamente", content = @Content(schema = @Schema(implementation = ListaApiImplAllegati.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ListaApiImplAllegati findAllErogazioneAllegati(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio, @QueryParam("q") String q, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset);

    /**
     * Ricerca erogazioni di api
     *
     * Elenca le erogazioni di API
     *
     */
    @GET
    @Path("/erogazioni")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Ricerca erogazioni di api", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Ricerca eseguita correttamente", content = @Content(schema = @Schema(implementation = ListaErogazioni.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ListaErogazioni findAllErogazioni(@QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("q") String q, @QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset, @QueryParam("tipo_api") TipoApiEnum tipoApi);

    /**
     * Restituisce i dettagli di un'erogazione di API
     *
     * Questa operazione consente di ottenere i dettagli di una erogazione di API identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce i dettagli di un'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Dettagli di un'erogazione di API restituiti con successo", content = @Content(schema = @Schema(implementation = ErogazioneViewItem.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ErogazioneViewItem getErogazione(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce le informazioni sull'API implementata dall'erogazione
     *
     * Questa operazione consente di ottenere le informazioni sull'API implementata dall'erogazione identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/api")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce le informazioni sull'API implementata dall'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Informazioni sull'API restituite con successo", content = @Content(schema = @Schema(implementation = ApiImplVersioneApiView.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ApiImplVersioneApiView getErogazioneAPI(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce il dettaglio di un allegato dell'erogazione
     *
     * Questa operazione consente di ottenere il dettaglio di un allegato dell'erogazione di API identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/allegati/{nome_allegato}")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce il dettaglio di un allegato dell'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Dati dell'allegato restituiti con successo", content = @Content(schema = @Schema(implementation = ApiImplAllegato.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ApiImplAllegato getErogazioneAllegato(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @PathParam("nome_allegato") String nomeAllegato, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce le informazioni su connettore associato all'erogazione
     *
     * Questa operazione consente di ottenere le informazioni sul connettore associato all'erogazione identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/connettore")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce le informazioni su connettore associato all'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Informazioni sul connettore restituite con successo", content = @Content(schema = @Schema(implementation = Connettore.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public Connettore getErogazioneConnettore(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce le informazioni generali di un'erogazione di API
     *
     * Questa operazione consente di ottenere le informazioni generali di un'erogazione di API identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/informazioni")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce le informazioni generali di un'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Informazioni generali restituite con successo", content = @Content(schema = @Schema(implementation = ApiImplInformazioniGeneraliView.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ApiImplInformazioniGeneraliView getErogazioneInformazioniGenerali(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Restituisce le informazioni sull'url di invocazione necessaria ad invocare l'erogazione
     *
     * Questa operazione consente di ottenere le informazioni sull'url di invocazione necessaria ad invocare l'erogazione identificata dal nome e dalla versione
     *
     */
    @GET
    @Path("/erogazioni/{nome}/{versione}/url-invocazione")
    @Produces({ "application/json", "application/problem+json" })
    @Operation(summary = "Restituisce le informazioni sull'url di invocazione necessaria ad invocare l'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Informazioni sull'url di invocazione restituite con successo", content = @Content(schema = @Schema(implementation = ApiImplUrlInvocazioneView.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public ApiImplUrlInvocazioneView getErogazioneUrlInvocazione(@PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Consente di modificare la versione dell'API implementata dall'erogazione
     *
     * Questa operazione consente di aggiornare la versione dell'API implementata dall'erogazione identificata dal nome e dalla versione
     *
     */
    @PUT
    @Path("/erogazioni/{nome}/{versione}/api")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Consente di modificare la versione dell'API implementata dall'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Erogazione di API aggiornata correttamente"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void updateErogazioneAPI(@Valid ApiImplVersioneApi body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Modifica i dati di un allegato dell'erogazione
     *
     * Questa operazione consente di aggiornare i dettagli di un allegato dell'erogazione di API identificata dal nome e dalla versione
     *
     */
    @PUT
    @Path("/erogazioni/{nome}/{versione}/allegati/{nome_allegato}")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Modifica i dati di un allegato dell'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "L'allegato è stato aggiornato correttamente"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void updateErogazioneAllegato(@Valid ApiImplAllegato body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @PathParam("nome_allegato") String nomeAllegato, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Consente di modificare la configurazione del connettore associato all'erogazione
     *
     * Questa operazione consente di aggiornare la configurazione del connettore associato all'erogazione identificata dal nome e dalla versione
     *
     */
    @PUT
    @Path("/erogazioni/{nome}/{versione}/connettore")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Consente di modificare la configurazione del connettore associato all'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Configurazione dell'erogazione aggiornata correttamente"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void updateErogazioneConnettore(@Valid Connettore body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Consente di modificare le informazioni generali di un'erogazione di API
     *
     * Questa operazione consente di aggiornare le informazioni generali di un'erogazione di API identificata dal nome e dalla versione
     *
     */
    @PUT
    @Path("/erogazioni/{nome}/{versione}/informazioni")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Consente di modificare le informazioni generali di un'erogazione di API", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Erogazione di API aggiornata correttamente"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void updateErogazioneInformazioniGenerali(@Valid ApiImplInformazioniGenerali body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);

    /**
     * Consente di modificare la configurazione utilizzata per identificare l'azione invocata dell'API implementata dall'erogazione
     *
     * Questa operazione consente di aggiornare la configurazione utilizzata dal Gateway per identificare l'azione invocata
     *
     */
    @PUT
    @Path("/erogazioni/{nome}/{versione}/url-invocazione")
    @Consumes({ "application/json" })
    @Produces({ "application/problem+json" })
    @Operation(summary = "Consente di modificare la configurazione utilizzata per identificare l'azione invocata dell'API implementata dall'erogazione", tags={ "erogazioni" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Configurazione dell'erogazione aggiornata correttamente"),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "401", description = "Non sono state fornite le credenziali necessarie", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "403", description = "Autorizzazione non concessa per l'operazione richiesta", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "429", description = "Too many requests", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "200", description = "Unexpected error", content = @Content(schema = @Schema(implementation = Problem.class))) })
    public void updateErogazioneUrlInvocazione(@Valid Object body, @PathParam("nome") String nome, @PathParam("versione") Integer versione, @QueryParam("profilo") ProfiloEnum profilo, @QueryParam("soggetto") String soggetto, @QueryParam("tipo_servizio") String tipoServizio);
}
