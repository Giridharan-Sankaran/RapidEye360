Feature:Supplier_class

  Scenario: To check if POST API creates new records
    Given the application is running
    And I Set POST supplier service api endpoint
    And add the brand name as "Cucumber1"
    And add the supplier email as "Cucumber1@example.com"
    And add the supplier contact as "+1 111-111-1111"
    When I send a POST request
    Then the response status code should be 200

  Scenario: To check if GET API is displaying all the records
    Given the application is running
    When I request all suppliers
    Then the response status code should be 200
#    And the brand name of record 2 should be "Neal Brothers"
#    And the brand name of record 1 should be "Weight Watchers (Smart Ones)"
#    And the supplier email of record 2 should be "nealbrothers.supplier@example.com"
#    And the supplier email of record 1 should be "smartones.supplier@example.com"
#    And the supplier contact of record 2 should be "+1 905-555-4321"
#    And the supplier contact of record 1 should be "+1 514-555-9876"

  Scenario: To check if GET API is displaying records with given brandname
    Given the application is running
    When I request supplier details with brandname "Cucumber1"
    Then the response status code should be 200
    And the brand name of record 0 should be "Cucumber1"
    And the supplier email of record 0 should be "clubhouse.supplier1@example.com"
    And the supplier contact of record 0 should be "+1 604-555-5679"

  Scenario: To check if PUT API is working as expected
    Given the application is running
    And I Set PUT supplier service api endpoint
    When I update the supplier details for the brand name "Cucumber1"
    And I send a PUT request
    Then the response status code should be 200
    And the brand name of record 0 should be "Cucumber1"
    And the supplier email of record 0 should be "clubhouse.supplier1@example.com"
    And the supplier contact of record 0 should be "+1 604-555-5679"

  Scenario: Deleting a Supplier by Brand Name
    Given the application is running
    When I request to delete the supplier with brand name "Cucumber1"
    Then the response status code should be 200
    And the response message should be "Expired Product Thrown Away. Customer First Approach."




