Feature: User API tests

  Background:
    * url 'http://localhost:8080/users'

  Scenario: Create a new user
    Given request { firstName: 'Jane', lastName: 'Doe', email: 'jane@example.com', password: 'password123' }
    When method POST
    Then status 200
    And match response.firstName == 'Jane'
    And match response.email == 'jane@example.com'


  Scenario: Get all users
    When method GET
    Then status 200
    And match response.length == 5
    And match response[0].firstName == 'Saint-Pierre'
    And match response[1].email == 'amz@clb.com'


  Scenario: Get a user by ID
    Given path '1'
    When method GET
    Then status 200
    And match response.firstName == 'Saint-Pierre'
    And match response.email == 'pierre@ksi.com'


  Scenario: Delete a user by ID
    Given path '2'
    When method DELETE
    Then status 200
    And match response == 'Utilisateur supprimé avec succès.'