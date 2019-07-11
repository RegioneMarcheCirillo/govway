Feature: Ricerca Temporale Transazioni e Ricerca Eventi

Background: 
#* configure afterFeature = function(){ karate.call('classpath:cleanup_tests.feature'); }

* def ricercaUrl = monitorUrl + '/monitoraggio/transazioni'
* call read('classpath:crud_commons.feature')

* def setup = callonce read('classpath:prepare_tests.feature')
* def intervallo_temporale = ({ data_inizio: setup.dataInizio, data_fine: setup.dataFine })

* url ricercaUrl
* configure headers = ({ "Authorization": govwayMonitorCred }) 

@FiltroMittenteTokenInfo
Scenario: Ricerca con le possibili combinazioni del filtro mittente
    * call read('classpath:ricerca-transazioni-filtro-mittente-token-info.feature')

@FiltroTemporale
Scenario: Ricerca per FiltroTemporale
    * def filtro = read('classpath:bodies/ricerca-filtro-temporale.json')
    * set filtro.intervallo_temporale = intervallo_temporale
    
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 3

    * set filtro.tipo = 'fruizione'
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 3

@FiltroApi
Scenario: Ricerca per FiltroApi
    * def filtro = read('classpath:bodies/ricerca-filtro-api-erogazione.json')
    * set filtro.intervallo_temporale = intervallo_temporale
    * set filtro.api.nome = setup.erogazione_petstore.api_nome
    * set filtro.api.versione = setup.erogazione_petstore.api_versione

    * def expected_api = 
    """
    { 
        nome: '#(filtro.api.nome)',
        versione: '#(filtro.api.versione)',
        operazione: '#(filtro.azione)',
        erogatore: '#(soggettoDefault)',
        tipo: "gw",
        informazioni_erogatore: "#notnull",
        profilo_collaborazione: "##notnull"
    }
    """
    Given request filtro
    When method post
    Then status 200
    And match each response.items contains { api: '#(^expected_api)' }

    * set filtro.api.erogatore = setup.erogatore.nome
    * set filtro.tipo = 'fruizione'
    * set expected_api.erogatore = filtro.api.erogatore
    
    Given request filtro
    When method post
    Then status 200
    And match each response.items contains { api: '#(^expected_api)' }


@FiltroMittenteApplicativo
Scenario: Ricerca per Filtro Mittente Applicativo
    * def filtro = read('classpath:bodies/ricerca-filtro-mittente-applicativo.json')
    * eval filtro.intervallo_temporale = intervallo_temporale
    * eval filtro.mittente.id.soggetto = soggettoDefault
    * eval filtro.mittente.id.applicativo = setup.applicativo.nome

    * def expected_mittente = ({ applicativo: filtro.mittente.id.applicativo })

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }

    * set filtro.tipo = "fruizione"
    * set filtro.mittente.id.soggetto = null
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }

@FiltroMittenteIdAutenticatoHttp
Scenario: Ricerca per Filtro Mittente con autenticazione http
    * def filtro = read('classpath:bodies/ricerca-filtro-mittente-idautenticato.json')
    * eval filtro.mittente.id.id = setup.applicativo.credenziali.username
    * eval filtro.intervallo_temporale = intervallo_temporale
    * def expected_mittente = ({ applicativo: setup.applicativo.nome })

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }

    * set filtro.tipo = "fruizione"
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }

@FiltroMittenteIdAutenticatoHttps
Scenario: Ricerca per Filtro Mittente con autenticazione https
    * def filtro = read('classpath:bodies/ricerca-filtro-mittente-idautenticato.json')
    * set filtro.mittente.id = ({ ricerca_esatta: false, case_sensitive: false, autenticazione: 'ssl', id: "cn=client"})
    * set filtro.intervallo_temporale = intervallo_temporale
    
    * def expected_mittente = ({ fruitore: setup.soggetto_certificato.nome })

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }
    
    * set filtro.tipo = "fruizione"
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1


@FiltroMittenteIdAutenticatoPrincipal
Scenario: Ricerca per Filtro Mittente con autenticazione principal
    * def filtro = read('classpath:bodies/ricerca-filtro-mittente-idautenticato.json')
    * eval filtro.mittente.id.id = setup.applicativo_principal.credenziali.userid
    * eval filtro.intervallo_temporale = intervallo_temporale
    * eval filtro.mittente.id.autenticazione = 'principal'

    * def expected_mittente = ({ applicativo: setup.applicativo_principal.nome })

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }

    * set filtro.tipo = "fruizione"
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)' }
    
@FiltroMittenteSoggetto
Scenario: Ricerca per Filtro Mittente Soggetto
    * def filtro = read('classpath:bodies/ricerca-filtro-mittente-soggetto.json')
    * eval filtro.intervallo_temporale = intervallo_temporale
    * eval filtro.mittente.id.soggetto = setup.soggetto_http.nome

    * def expected_mittente = ({ fruitore: filtro.mittente.id.soggetto })

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1
    And match each response.items contains { mittente: '#(^expected_mittente)'}



@TestNotFound
    Scenario: Test Not Found
    * def filtro = read('classpath:bodies/ricerca-filtro-api-erogazione.json')
    * eval filtro.intervallo_temporale = intervallo_temporale
    * eval filtro.api.nome = "$$FiltroInesistente$$"

    Given request filtro
    When method post
    Then assert ( responseStatus == 200 && response.items.length == 0) || responseStatus == 404


@FiltroEsitoError
Scenario: Ricerca per Esito Erroneo
    * def filtro = read('classpath:bodies/ricerca-filtro-esito-error.json')
    * eval filtro.intervallo_temporale = intervallo_temporale

    * def expected_risposta = { esito_consegna: '401' }

    # Controllo che le richieste con esito error siano proprio quelle non autorizzate fatte nei test
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length > 0
    And match each response.items contains { risposta: '#(^expected_risposta)' }

    * set filtro.tipo = 'fruizione'
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length > 0
    And match each response.items contains { risposta: '#(^expected_risposta)' }

@FiltroEsitoPersonalizzato
Scenario: Ricerca per Filtro Esito Personalizzato
    * def filtro = read('classpath:bodies/ricerca-filtro-esito-personalizzato.json')
    * eval filtro.intervallo_temporale = intervallo_temporale

    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1

    * set filtro.tipo = 'fruizione'
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length >= 1

@RicercaSempliceTransazioni
Scenario: RicercaSempliceTransazioni tramite richiesta GET
    
    * def filtro = read('classpath:bodies/ricerca-filtro-api-erogazione.json')
    * eval filtro.api.nome = setup.erogazione_petstore.api_nome
    * eval filtro.api.versione = setup.erogazione_petstore.api_versione    
    * eval filtro.intervallo_temporale = intervallo_temporale

    * def query =
    """ ({
        data_inizio: filtro.intervallo_temporale.data_inizio,
        data_fine: filtro.intervallo_temporale.data_fine,
        tipo: 'erogazione',
        nome_servizio: filtro.api.nome,
        versione_servizio: filtro.api.versione,
        azione: filtro.azione,
        esito: 'ok'
    })
    """
    Given params query
    When method get
    Then status 200
    And assert response.items.length > 0 && response.items.length <= 3

    * set query.soggetto_remoto = setup.erogatore.nome
    * set query.tipo = 'fruizione'
    Given params query
    When method get
    Then status 200
    And assert response.items.length > 0 && response.items.length <= 3


@IdMessaggioRisposta
Scenario: Ricerca singola transazione per Id Messaggio (Risposta)

    * def filtro = read('classpath:bodies/ricerca-filtro-api-erogazione.json')
    * eval filtro.api.nome = setup.erogazione_petstore.nome
    * eval filtro.api.versione = setup.erogazione_petstore.versione
    * eval filtro.intervallo_temporale =  ({ data_inizio: setup.dataInizio, data_fine: setup.dataFine })


    # Viene fatta prima una ricerca lasca per recuperare delle transazioni qualsiasi
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length > 0

    * def transazione = response.items[0]
    * def id_messaggio = transazione.risposta.id

    Given path 'id_messaggio'
    And params ({ tipo_messaggio: 'risposta', id: id_messaggio })
    When method get
    Then status 200

    * set transazione.data_emissione = "#ignore"
    * set response.items[0].data_emissione = "#ignore"
    * match response.items[0] == transazione

@IdTransazione
Scenario: Ricerca per Id Transazione.

    * def filtro = read('classpath:bodies/ricerca-filtro-api-erogazione.json')
    * eval filtro.api.nome = setup.erogazione_petstore.nome
    * eval filtro.api.versione = setup.erogazione_petstore.versione
    * eval filtro.intervallo_temporale =  ({ data_inizio: setup.dataInizio, data_fine: setup.dataFine })

     # Viene fatta prima una ricerca lasca per recuperare una transazione qualsiasi
    Given request filtro
    When method post
    Then status 200
    And assert response.items.length > 0

    * def id_transazione = response.items[0].id_traccia

    Given path id_transazione
    When method get
    Then status 200


@RicercaEventi
Scenario: Ricerca Lista Eventi ed evento singolo

    * def query =
    """
    {
        data_inizio: '2012-07-21T17:32:28Z',
        data_fine: '2022-07-21T17:32:28Z',
        severita: 'info',
        tipo: 'StatoGateway',
        codice: 'Start'
    }
    """

    # Faccio prima una ricerca lasca per poi recuperare un evento particolare
    Given url monitorUrl
    And path 'monitoraggio', 'eventi'
    And params query
    When method get
    Then status 200

    * def evento = response.items[0]
    
    Given url monitorUrl
    And path 'monitoraggio', 'eventi', evento.id
    And params query
    When method get
    Then status 200
    And match response == evento
