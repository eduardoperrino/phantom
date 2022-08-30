Feature: Subscriber resource must support CRUD operations

  Background:
    * def subscriberSchema = { id: '#uuid', name: '##string', email: '##string' }
    * url baseUrl

  Scenario: Perform multiple CRUD operations to Subscriber resource
    Given path '/subscriber/'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[0] subscriberSchema'

    Given path '/subscriber/'
    And request { name: 'This is a name', email: 'This is an email' }
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match response == { id: '#uuid', name: 'This is a name', email: 'This is an email' }

    Given path '/subscriber/'
    And request { name: 'This is a name' }
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match response == { id: '#uuid', name: 'This is a name', email: '#notpresent' }
    And def itemWithNameOnlyId = response.id

    Given path '/subscriber/'
    And request { email: 'This is an email' }
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match response == { id: '#uuid', name: '#notpresent', email: 'This is an email' }
    And def itemWithDescriptionOnlyId = response.id

    Given path '/subscriber/'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[3] subscriberSchema'

    Given path '/subscriber/', itemWithNameOnlyId
    When method delete
    Then status 204
    And match response == ''

    Given path '/subscriber/'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[2] subscriberSchema'

    Given path '/subscriber/', itemWithDescriptionOnlyId
    When method delete
    Then status 204
    And match response == ''

    Given path '/subscriber/'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[1] subscriberSchema'

    Given path '/subscriber/'
    When method delete
    Then status 204
    And match response == ''

    Given path '/subscriber/'
    And header Accept = 'application/json'
    When method get
    Then status 200
    And match response == '#[0] subscriberSchema'
