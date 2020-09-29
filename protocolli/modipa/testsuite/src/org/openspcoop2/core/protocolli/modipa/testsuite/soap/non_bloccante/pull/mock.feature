Feature: Server di mock contattato dalla erogazione, svolge il ruolo del TestService di echo.

Background: 
    * configure responseHeaders = { 'Content-type': "application/soap+xml" }

# Catch all

# Scenario giro ok, richiesta stato non pronta
Scenario: methodIs('post') && bodyPath('/Envelope/Body/MProcessingStatus') != null && bodyPath('/Envelope/Header/X-Correlation-ID') == 'd2f49459-1624-4710-b80c-15e33d64b608_NOT_READY'

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-stato-not-ready-response.xml')
* def responseStatus = 200


Scenario: methodIs('post') && bodyPath('/Envelope/Body/MProcessingStatus') != null && bodyPath('/Envelope/Header/X-Correlation-ID') == 'd2f49459-1624-4710-b80c-15e33d64b608'

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-stato-ready-response.xml')
* def responseStatus = 200


Scenario: methodIs('post') && bodyPath('/Envelope/Body/MResponse') != null && bodyPath('/Envelope/Header/X-Correlation-ID') == 'd2f49459-1624-4710-b80c-15e33d64b608'

* def response = read('classpath:src/test/soap/non-bloccante/pull/recupero-risposta-response.xml')
* def responseStatus = 200


Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-erogazione-validazione')

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-no-correlation-response.xml')
* def responseStatus = 200


Scenario: methodIs('post') && headerContains('GovWay-TestSuite-Test-Id', 'no-correlation-in-request-erogazione')

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-no-correlation-response.xml')
* def responseStatus = 200

Scenario: 

* def response = read('classpath:src/test/soap/non-bloccante/pull/richiesta-applicativa-response.xml')
* def responseStatus = 200
