package dao.impl;

import dao.Dao;
import entity.CustomerPojo;
import helpers.connections.DBFactoryConnection;
import helpers.customer.builder.Customer;
import helpers.customer.faker.CustomerFakerHelper;
import helpers.queries.Queries;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerDaoImpl implements Dao<Customer>{
    Customer customer;
    private PreparedStatement preparedStatement;
    private final Connection conn;


    public CustomerDaoImpl() {
        this.customer = new CustomerFakerHelper().createCustomer();

        try (DBFactoryConnection db_conn = new DBFactoryConnection()) {
            this.conn = db_conn.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Check if given id exists in customers table
     *
     * @param id int
     * @return boolean
     */
    public boolean checkIfIdExists(int id) {
        try {
            this.preparedStatement = this.conn.prepareStatement(checkIfIdExistsQuery);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("count") > 0) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates random integer number
     *
     * @param max maximum value
     * @return int
     */
    public int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


    @Override
    public void save(Customer customer) {
        try {
            this.preparedStatement = this.conn.prepareStatement(Queries.insertQuery);

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setInt(4, customer.getAge());
            preparedStatement.setLong(5, customer.getAddressId());
            preparedStatement.setBoolean(6, customer.isGdpr());
            preparedStatement.setBoolean(7, customer.isProfileActive());
            preparedStatement.setDate(8, customer.getProfileCreated());
            preparedStatement.setDate(9, customer.getProfileDeactivated());
            preparedStatement.setString(10, customer.getDeactivationReason());
            preparedStatement.setString(11, customer.getNote());

            preparedStatement.executeUpdate();
            System.out.println("Record saved successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void deleteById(Long id) {
        try {
            // Record with this id will be deleted
            int customerId = id.intValue();
            this.preparedStatement = this.conn.prepareStatement(deleteRecordQuery);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
            System.out.printf("Customer with id = %d has been deleted successfully", customerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteAll() {
        try {
            this.preparedStatement = this.conn.prepareStatement(deleteAllQuery);
            int result = this.preparedStatement.executeUpdate();
            System.out.printf("All %d records have been deleted!\n", result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CustomerPojo> getAllRecords() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();

        ResultSetHandler<List<CustomerPojo>> resultSetHandler = new BeanListHandler<>(CustomerPojo.class);

        return queryRunner.query(conn, Queries.selectAllCustomersQuery, resultSetHandler);

    }

    @Override
    public Customer getById(int id) {
        Statement stmt = null;
        try {

            this.preparedStatement = this.conn.prepareStatement(Queries.selectCustomerQuery);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                Long addressId = rs.getLong("address_id");
                boolean gdpr = rs.getBoolean("gdpr");
                boolean isProfileActive = rs.getBoolean("is_profile_active");

                customer = Customer.builder(name, email, addressId, gdpr, isProfileActive)
                        .phone(rs.getString("phone"))
                        .age(rs.getInt("age"))
                        .addressId(rs.getLong("address_id"))
                        .profileCreated(rs.getDate("profile_created"))
                        .profileDeactivated(rs.getDate("profile_deactivated"))
                        .deactivationReason(rs.getString(10))
                        .note(rs.getString("notes"))
                        .build();
            }
            return customer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getAllRecordsCount() {
        return 0;
    }

    @Override
    public int getRandomId() {
        int randomId = getRandomNumber(getRecordsCount());
        if (checkIfIdExists(randomId)) {
            return randomId;
        }
        return -1;
    }

    public int getRecordsCount() {
        int recordsCount = 0;
        try {
            ResultSet rs = conn.createStatement().executeQuery(Queries.getRecordsCountQuery);
            while (rs.next()) {
                recordsCount = rs.getInt("count");
                if (recordsCount > -1) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recordsCount;
    }


    @Override
    public List getRandomIds(int count) {
        int id = 0;
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            id = getRandomId();
            if (id > -1) {
                // select only unique ids
                if (!idsList.contains(id)) {
                    idsList.add(id);
                } else {
                    // if id has been added to idList we need additional iteration to reach desired count of ids
                    i--;
                }
            } else {
                // if id doesn't exist we need of an additional iteration
                i--;
            }
        }
        return idsList;
    }

    @Override
    public void insertRecords(int count) throws SQLException {
        AddressDaoImpl addressDao = new AddressDaoImpl();
        for (int i = 0; i < count; i++) {
            this.customer = new CustomerFakerHelper().createCustomer();
            if (addressDao.checkIfIdExists(customer.getAddressId()) || customer.getAddressId() == 2L) {
                i--;
                continue;
            }

            save(customer);
        }
    }


    @Override
    public List<Customer> getByIds(List<Integer> ids) {
        List<Customer> customers = new ArrayList<>();
        for (int id : ids) {
            customers.add(getById(id));
        }
        return customers;
    }


    /**
     * Get last id from customer table
     *
     * @return int
     */
    public int getLastId() {
        int id = -1;

        try {
            ResultSet rs = conn.createStatement().executeQuery(Queries.getLastIdQuery);

            while (rs.next()) {
                id = rs.getInt("customer_id");
                if (id > -1) {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }


}
