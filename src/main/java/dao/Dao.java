package dao;

import entity.CustomerPojo;
import helpers.customer.builder.Customer;
import helpers.queries.Queries;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> extends Queries {
    void save(T object);

    void deleteById(Long id);

    void deleteAll();

    List<CustomerPojo> getAllRecords() throws SQLException;

    T getById(int id);

    List<T> getByIds(List<Integer> ids);

    int getAllRecordsCount();

    int getRandomId();

    List<Integer> getRandomIds(int randomCount);

    void insertRecords(int count) throws SQLException;

    int getLastId();
}
