package dao;

import entity.CustomerPojo;
import entity.OrderPojo;

import java.util.List;

public interface CustomerDao<T> extends Dao {
    List<T> setAddressToCustomer(); // Get customer's address

    List<OrderPojo> getCustomerOrdersFromDB(int id);

    void addOrdersToCustomers(List<CustomerPojo> customers);

    void printCustomerOrder(CustomerPojo customer);

    void printCustomerProducts(CustomerPojo customer);


}
