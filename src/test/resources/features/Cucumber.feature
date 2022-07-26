@customersAndAddresses
Feature: Customers and Addresses table test

  Background: Delete the old records and Create test data before test execution and verify the table is not empty
    Given An empty table
    When I insert 8 new records
    Then Verify the table is not empty
  @customer
  Scenario: Check if customer has been saved successfully
    Given A new customer into customers table
    Then Check if the customer exists in customer table
  @customer
  Scenario: Check records count
    When Delete a record from customers
    Then Verify the records count in customers table is 7
    And  Verify that it does not exist in the database

  @customer
  Scenario: Customers have all mandatory fields filled
    When I take 3 customers records
    Then Verify that their mandatory fields are with data

  @address
  Scenario: Customers should have an address.
    Given Customers with address
    Then Verify that each customer has address

  @address
  Scenario: Check if address have all mandatory fields with data
    Given count of test address is 3
    Then Verify that all mandatory fields are with data