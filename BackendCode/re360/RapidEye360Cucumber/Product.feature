Feature:Product_Inventory_class

  Scenario: To check if POST API creates new records
    Given the product application is running
    And I Set POST product service api endpoint
#    And add the upcid as 9899899899
    And add the category as "CucumberCateg"
    And add the name as "CucumberName"
    And add the brand as "CucumberBrand"
    And add the price as 5.55
    And add the quantity as 5
    And add the date of procurement as "2024-03-09"
    And add the date of expiry as "2024-07-09"
    And set the difference as 4
    And add the aisle number as "7"
    When I send a POST request for product
    Then the response status code for product should be 201
#    And I save the value of "upcid" from the response as "generatedUpcid"

  Scenario: To check if GET API is displaying all the records for product inventory
    Given the product application is running
    When I request all products from inventory
    Then the response status code for product should be 202
#    And the upcid of record 1 should be 123456789
#    And the upcid of record 2 should be 4569876543
#    And the category of record 1 should be "Frozen Meals & Entrees"
#    And the category of record 2 should be "Meat & Seafood"
#    And the name of record 1 should be "Pizza for One Suprema  Frozen Pizza"
#    And the name of record 2 should be "Organic Turkey Burgers"
#    And the brand of record 1 should be "Dr. Oetker"
#    And the brand of record 2 should be "Blue Goose"
#    And the price of record 1 should be 7.49
#    And the price of record 2 should be 9.79
#    And the quantity of record 1 should be 1
#    And the quantity of record 2 should be 1
#    And the date of procurement of record 1 should be "2024-01-23T00:00:00.000"
#    And the date of procurement of record 2 should be "2024-01-23T00:00:00.000"
#    And the date of expiry of record 1 should be "2024-06-23T00:00:00.000"
#    And the date of expiry of record 2 should be "2024-08-23T00:00:00.000"

#
#  Scenario: To check if GET API is displaying records with given UPCID
#    Given the product application is running
#    When I request product details with upcid 9899899899
#    Then the response status code for product should be 200
#    And the upcid of record 0 for GET should be 9899899899
#    And the category of record 0 for GET should be "CucumberCateg"
#    And the name of record 0 for GET should be "CucumberName"
#    And the brand of record 0 for GET should be "CucumberBrand"
#    And the price of record 0 for GET should be 5.55
#    And the quantity of record 0 for GET should be 5
#    And the date of procurement of record 0 for GET should be "2024-03-09T05:00:00.000"
#    And the date of expiry of record 0 for GET should be "2024-07-09T04:00:00.000"

  Scenario: To check if GET API is displaying records with given Category
    Given the product application is running
    When I request product details with category "CucumberCateg"
    Then the response status code for product should be 200
#    And the upcid of record 0 should be 9899899899
    And the category of record 0 should be "CucumberCateg"
    And the name of record 0 should be "CucumberName"
    And the brand of record 0 should be "CucumberBrand"
    And the price of record 0 should be 5.55
    And the quantity of record 0 should be 5
    And the date of procurement of record 0 should be "2024-03-09T05:00:00.000"
    And the date of expiry of record 0 should be "2024-07-09T04:00:00.000"
#
  Scenario: To check if GET API is displaying records with given Brand
    Given the product application is running
    When I request product details with brand "CucumberBrand"
    Then the response status code for product should be 200
#    And the upcid of record 0 should be 9899899899
    And the category of record 0 should be "CucumberCateg"
    And the name of record 0 should be "CucumberName"
    And the brand of record 0 should be "CucumberBrand"
    And the price of record 0 should be 5.55
    And the quantity of record 0 should be 5
    And the date of procurement of record 0 should be "2024-03-09T05:00:00.000"
    And the date of expiry of record 0 should be "2024-07-09T04:00:00.000"

  Scenario: To check if GET API is displaying records with given date of procurement
    Given the product application is running
    When I request product details with procurement date "2024-03-09T05:00:00.000"
    Then the response status code for product should be 200
#    And the upcid of record 0 should be 9899899899
    And the category of record 0 should be "CucumberCateg"
    And the name of record 0 should be "CucumberName"
    And the brand of record 0 should be "CucumberBrand"
    And the price of record 0 should be 5.55
    And the quantity of record 0 should be 5
    And the date of procurement of record 0 should be "2024-03-09T05:00:00.000"
    And the date of expiry of record 0 should be "2024-07-09T04:00:00.000"

  Scenario: To check if GET API is displaying records with given date of expiry
    Given the product application is running
    When I request product details with expiry date "2024-07-09T04:00:00.000"
    Then the response status code for product should be 200
#    And the upcid of record 0 should be 9899899899
    And the category of record 0 should be "CucumberCateg"
    And the name of record 0 should be "CucumberName"
    And the brand of record 0 should be "CucumberBrand"
    And the price of record 0 should be 5.55
    And the quantity of record 0 should be 5
    And the date of procurement of record 0 should be "2024-03-09T05:00:00.000"
    And the date of expiry of record 0 should be "2024-07-09T04:00:00.000"

#  Scenario: To check if PUT API is working as expected for product
#    Given the product application is running
#    And I Set PUT product service api endpoint
#    When I update the product details for the upcid 9899899899
#    And I send a PUT request for products
#    Then the response status code for product should be 200
##    And the upcid of record 0 for GET should be 9899899899
#    And the category of record 0 for GET should be "CucumberCateg1"
#    And the name of record 0 for GET should be "CucumberName1"
#    And the brand of record 0 for GET should be "CucumberBrand1"
#    And the price of record 0 for GET should be 6.10
#    And the quantity of record 0 for GET should be 10
#    And the date of procurement of record 0 for GET should be "2024-03-13T05:00:00.000"
#    And the date of expiry of record 0 for GET should be "2024-07-13T05:00:00.000"
#
  Scenario: Deleting a Product by UPCID and aisleNumber
    Given the product application is running
    When I request to delete the product with upcid 9899899899 and aisleNumber "7"
    Then the response status code for product should be 200
    And the response message for product deletion should be "Expired Product Thrown Away. Customer First Approach."




