Feature: Abilita La disclosure degli errori di interoperabilità

Scenario:
    # TODO prendi username e pass dalla configurazione

    * def basic = read('classpath:utils/basic-auth.js')

    * def url_azione = govway_base_path + "/check?resourceName=ConfigurazionePdD&attributeName=transactionErrorForceSpecificTypeInternalResponseError&attributeBooleanValue=true"
    
    Given url url_azione
    And header Authorization = basic({username: 'admin', password: 'admin'})
    When method get
    Then status 200

    * def url_azione = govway_base_path + "/check?resourceName=ConfigurazionePdD&attributeName=transactionErrorForceSpecificTypeBadResponse&attributeBooleanValue=true"

    Given url url_azione
    And header Authorization = basic({username: 'admin', password: 'admin'})
    When method get
    Then status 200

    * def url_azione = govway_base_path + "/check?resourceName=ConfigurazionePdD&attributeName=transactionErrorForceSpecificDetails&attributeBooleanValue=true"

    Given url url_azione
    And header Authorization = basic({username: 'admin', password: 'admin'})
    When method get
    Then status 200
