Feature: Template Create 201

Scenario: Template Test Create 201

#CREATE
Given url configUrl
And path resourcePath
And  header Authorization = govwayConfAuth
And request body
And params query_params
When method post
Then assert responseStatus == 201
And match responseHeaders contains { 'X-Api-Key': '#notpresent' }
And match responseHeaders contains { 'X-App-Id': '#notpresent' }

#DELETE
Given url configUrl
And path resourcePath , key
And header Authorization = govwayConfAuth
And params query_params
When method delete
Then status 204
