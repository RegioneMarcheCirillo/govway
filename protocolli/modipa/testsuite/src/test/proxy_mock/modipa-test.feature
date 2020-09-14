Feature: payment service contract test

Background:

* def createOrUpdateConfig = read('classpath:utils/config-loader/createOrUpdate.js') 
* def deleteConfig = read('classpath:utils/config-loader/delete.js') 

# * callonce createOrUpdateConfig modipa_test_bundle
# * configure afterFeature = function() { deleteConfig(modipa_test_bundle) }


* def url_invocazione_fruizione = govway_base_path + '/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRestHttpProxy/v1'
* url url_invocazione_fruizione

Scenario: Test Demo con mock proxy

    Given path 'resources', 1, 'M'
    And request { amount: 5.67, description: 'test one' }
    When method post
    Then status 200
    And match response == { amount: 5.67, description: 'test one' }