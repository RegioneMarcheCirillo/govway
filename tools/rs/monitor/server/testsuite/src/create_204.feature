Feature: Template Create 204

Scenario: Template Test Create 204

#CREATE
Given url configUrl
And path resourcePath
And  header Authorization = govwayConfAuth
And request body
And params query_params
When method post
Then assert responseStatus == 204

#DELETE
Given url configUrl
And path resourcePath , key
And header Authorization = govwayConfAuth
And params query_params
When method delete
Then status 204