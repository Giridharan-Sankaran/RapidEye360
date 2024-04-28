Feature:Discount_class

  Scenario: To check if POST API creates new records for discount class
    Given the discount application is running
    And I Set POST discount service api endpoint
    And add the upcid for discount as 9899899899
    And add the category for discount as "CucumberCateg"
    And add the name for discount as "CucumberName"
    And add the brand for discount as "CucumberBrand"
    And add the originalprice for discount as 5.55
    And add the discountprice for discount as 2.55
    And add the aisleNumber for discount as 3
    When I send a POST request for discount
    Then the response status code for discount should be 200

  Scenario: To check if GET API is displaying all the records for discount inventory
    Given the discount application is running
    When I request all discounts from inventory
    Then the response status code for discount should be 200

  Scenario: To check if GET API is displaying records with given UPCID
    Given the discount application is running
    When I request discount details with upcid 9899899899
    Then the response status code for discount should be 200
    And the upcid of record 0 for discount GET should be 9899899899
    And the category of record 0 for discount GET should be "CucumberCateg"
    And the name of record 0 for discount GET should be "CucumberName"
    And the brand of record 0 for discount GET should be "CucumberBrand"
    And the originalprice of record 0 for discount GET should be 5.550000190734863
    And the discountprice of record 0 for discount GET should be 2.549999952316284

  Scenario: To check if PUT API is working as expected for discount
    Given the discount application is running
    And I Set PUT discount service api endpoint
    When I update the discount details for the upcid 9899899899
    And I send a PUT request for discount
    Then the response status code for discount should be 200
    And the upcid of record 0 for discount GET should be 9899899899
    And the category of record 0 for discount GET should be "CucumberCateg"
    And the name of record 0 for discount GET should be "CucumberName"
    And the brand of record 0 for discount GET should be "CucumberBrand"
    And the originalprice of record 0 for discount GET should be 5.550000190734863
    And the discountprice of record 0 for discount GET should be 3.11

  Scenario: Deleting a Discount by UPCID
    Given the discount application is running
    When I request to delete the discount with upcid 9899899899
    Then the response status code for discount should be 200
    And the response message for discount deletion should be "Expired Product Thrown Away. Customer First Approach."

