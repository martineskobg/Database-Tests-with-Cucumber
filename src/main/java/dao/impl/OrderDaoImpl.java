package dao.impl;

import dao.OrderDao;
import entity.CustomerPojo;
import entity.ProductPojo;
import helpers.connections.DBFactoryConnection;
import helpers.queries.Queries;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private QueryRunner queryRunner;
    private final Connection conn;

    public OrderDaoImpl() {
        try (DBFactoryConnection db_conn = new DBFactoryConnection()) {
            this.conn = db_conn.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ProductPojo> getOrderProductsFromDB(Long orderId) {

        List<ProductPojo> productPojoList;

        queryRunner = new QueryRunner();
        ResultSetHandler<List<ProductPojo>> customerResultSetHandler = new BeanListHandler<>(ProductPojo.class);
        try {
            productPojoList = new ArrayList<>(queryRunner.query(conn, Queries.getOrderProducts, customerResultSetHandler, orderId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return productPojoList;
    }


    @Override
    public void save(Object object) {

    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<CustomerPojo> getAllRecords() {
        return null;
    }

    @Override
    public Object getById(int id) {
        return null;
    }

    @Override
    public int getAllRecordsCount() {
        return 0;
    }

    @Override
    public int getRandomId() {
        return 0;
    }

    @Override
    public List getRandomIds(int randomCount) {
        return null;
    }

    @Override
    public void insertRecords(int count) {

    }

    @Override
    public int getLastId() {
        return 0;
    }

    @Override
    public List getByIds(List ids) {
        return null;
    }
}
