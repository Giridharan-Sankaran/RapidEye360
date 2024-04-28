Feature:Expiry_class

  Scenario: To check if POST API creates new records for expiry class
    Given the expiry application is running
    And I Set POST expiry service api endpoint
    And add the upcid for expiry as 9899899899
    And add the category for expiry as "CucumberCateg"
    And add the name for expiry as "CucumberName"
    And add the brand for expiry as "CucumberBrand"
    And add the aisleNumber for expiry as 5
    And add the dateOfExpiry for expiry as "2024-07-09"
    When I send a POST request for expiry
    Then the response status code for expiry should be 200

  Scenario: To check if GET API is displaying all the records for expiry inventory
    Given the expiry application is running
    When I request all expired list from inventory
    Then the response status code for expiry should be 200


  Scenario: To check if GET API is displaying expiry records with given UPCID
    Given the expiry application is running
    When I request expiry details with upcid 9899899899
    Then the response status code for expiry should be 200
    And the upcid of record 0 for expiry GET should be 9899899899
    And the category of record 0 for expiry GET should be "CucumberCateg"
    And the name of record 0 for expiry GET should be "CucumberName"
    And the brand of record 0 for expiry GET should be "CucumberBrand"
    And the date of expiry of record 0 for expiry GET should be "2024-07-09T04:00:00.000"

  Scenario: To check if GET API is displaying expiry records with given date of expiry
    Given the expiry application is running
    When I request expiry details with expiry date "2024-07-09T04:00:00.000"
    Then the response status code for expiry should be 200
      And the upcid of record 0 for expiry should be 9899899899
    And the category of record 0 for expiry should be "CucumberCateg"
    And the name of record 0 for expiry should be "CucumberName"
    And the brand of record 0 for expiry should be "CucumberBrand"
    And the date of expiry of record 0 for expiry should be "2024-07-09T04:00:00.000"

  Scenario: To check if PUT API is working as expected for expiry
    Given the expiry application is running
    And I Set PUT expiry service api endpoint
    When I update the expiry details for the upcid 9899899899
    And I send a PUT request for expiry
    Then the response status code for expiry should be 200
    And the upcid of record 0 for expiry GET should be 9899899899
    And the category of record 0 for expiry GET should be "CucumberCateg"
    And the name of record 0 for expiry GET should be "CucumberName"
    And the brand of record 0 for expiry GET should be "CucumberBrand"
    And the date of expiry of record 0 for expiry GET should be "2024-07-12T00:00:00.000"

  Scenario: Deleting a expiry by UPCID
    Given the expiry application is running
    When I request to delete the expiry with upcid 9899899899
    Then the response status code for expiry should be 200
    And the response message for expiry deletion should be "Expired Product Thrown Away. Customer First Approach."