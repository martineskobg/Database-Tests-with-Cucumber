# JDBC: Database Tests with Cucumber

### Description
In this project I'm using JDBC and Cucumber to test customers and addresses tables


#### Practical tasks:
**Task 1**

**Background: Create test data before test execution and verify the table is not empty**

Description: Create tests that validate the data in the customer's table. Verify:

create a new record, save it and verify that it was saved successfully, verify there are more entries in the table

delete a record, and verify that it doesn’t exist in the database and there are fewer entries in the table.

get X random customers by getting X random customer IDs and verify that their mandatory fields are with data

verify the data count for the customers table

**Create tests for Customer Addresses**

get X random customers by getting X random customer IDs and verify they have an address.

get X random addresses by getting X random IDs and verify that they have all mandatory fields with data
### Instruction
#### Prerequisites
1. SQL: Local database environment setup.
2. customers table with example data
3. addresses table with example data
##### Useful links:
1. https://cucumber.io/