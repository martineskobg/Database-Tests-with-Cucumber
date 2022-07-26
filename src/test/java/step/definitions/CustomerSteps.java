package step.definitions;

import dao.impl.AddressDaoImpl;
import dao.impl.CustomerDaoImpl;
import entity.AddressPojo;
import entity.CustomerPojo;
import helpers.customer.builder.Customer;
import helpers.customer.faker.CustomerNoFakerHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class CustomerSteps {

    private static final CustomerDaoImpl customerDao = new CustomerDaoImpl();
    private static final AddressDaoImpl addressDao = new AddressDaoImpl();
    private static final Customer customerNoFakerHelper = new CustomerNoFakerHelper().createCustomer();
    private static CustomerPojo testCustomer;
    private static List<CustomerPojo> customersForTest;
    private static List<AddressPojo> addressesForTest;
    private static List<CustomerPojo> customers;
    private static List<AddressPojo> addresses;


    @Given("A new customer into customers table")
    public void a_new_customer_into_customers_table() throws SQLException {
        customerDao.save(customerNoFakerHelper);
    }

    @Then("Check if the customer exists in customer table")
    public void check_if_the_customer_exists_in_customer_table() {
        Customer lastInsertedCustomer = customerDao.getById(customerDao.getLastId());
        Assertions.assertEquals(customerNoFakerHelper.getName(), lastInsertedCustomer.getName(), "The customer does NOT exists in the DB ");
    }


    @Given("Customers with address")
    public List<CustomerPojo> customersWithAddress() throws SQLException {
        return customerDao.getAllRecords();
    }

    @Then("Verify that each customer has address")
    public void verifyThatEachCustomerHasAddress() throws SQLException {
        customersWithAddress().forEach(customer ->
                Assertions.assertNotNull(customer.getAddress_id(),
                        String.format("Customer whit id = %s doesn't have address", customer.getName())));
    }

    @Given("An empty table")
    public void anEmptyTable() {
        customerDao.deleteAll();
    }

    @When("I insert {int} new records")
    public void iInsertNewRecords(int count) throws SQLException {
        customerDao.insertRecords(count);
    }

    @Then("Verify the table is not empty")
    public void verifyTheTableIsNotEmpty() throws SQLException {
        Assertions.assertTrue(customerDao.getAllRecords().size() > 0);
    }

    @When("Delete a record from customers")
    public void deleteARecordFromIt() throws SQLException {
        testCustomer = customerDao.getAllRecords().get(1);
        int randomId = testCustomer.getCustomer_id();
        if (customerDao.checkIfIdExists(randomId)) {
            customerDao.deleteById((long) randomId);
        }
    }

    @Then("Verify the records count in customers table is {int}")
    public void verifyTheRecordsCountInCustomersTableIs(int expectedCountOfRows) throws SQLException {
        int actualCountRows = customerDao.getAllRecords().size();
        Assertions.assertEquals(expectedCountOfRows, actualCountRows, "The count of rows in customers table is different then expected count!");
    }

    @And("Verify that it does not exist in the database")
    public void verifyThatItDoesNotExistInTheDatabase() {
        int id = testCustomer.getCustomer_id();
        Assertions.assertFalse(customerDao.checkIfIdExists(id), String.format("Customer with id = %d hasn't been deleted!", id));
    }


    @When("I take {int} customers records")
    public void iTakeCustomersRecords(int countOfRecords) throws SQLException {
        customers = customerDao.getAllRecords();
        customersForTest = new ArrayList<>();
        for (int i = 0; i < countOfRecords; i++) {
            customersForTest.add(customers.get(i));
        }
    }

    @Then("Verify that their mandatory fields are with data")
    public void verifyThatTheirMandatoryFieldsAreWithData() {
        // Check name is not null
        customersForTest.forEach(customer ->
                assertNotNull(String.format("Customer whit id = %d doesn't have name", customer.getCustomer_id()), customer.getName()));
        // Check email is not null
        customersForTest.forEach(customer ->
                assertNotNull(String.format("Customer whit id = %d doesn't have email", customer.getCustomer_id()), customer.getEmail()));
        // Check addressId is not null
        customersForTest.forEach(customer ->
                assertNotNull(String.format("Customer whit id = %d doesn't have address", customer.getCustomer_id()), customer.getAddress_id()));
    }

    @Given("count of test address is {int}")
    public void countOfTestAddressIs(int count) throws SQLException {
        addresses = addressDao.getAllRecords();
        addressesForTest = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            addressesForTest.add(addresses.get(i));
        }

    }

    @Then("Verify that all mandatory fields are with data")
    public void verifyThatAllMandatoryFieldsAreWithData() {
        addressesForTest.forEach(address ->
                assertNotNull(String.format("Address whit id = %d doesn't have value for address", address.getAddress_id()), address.getAddress()));

        addressesForTest.forEach(address ->
                assertNotNull(String.format("Address whit id = %d doesn't have value for country", address.getAddress_id()), address.getCountry()));
    }
}
