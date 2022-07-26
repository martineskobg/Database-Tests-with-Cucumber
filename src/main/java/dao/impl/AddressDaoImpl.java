package dao.impl;

import dao.AddressDao;
import entity.AddressPojo;
import entity.CustomerPojo;
import helpers.connections.DBFactoryConnection;
import helpers.queries.Queries;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddressDaoImpl implements AddressDao, Queries {
    private PreparedStatement preparedStatement;
    private final Connection conn;

    public AddressDaoImpl() {
        try (DBFactoryConnection db_conn = new DBFactoryConnection()) {
            this.conn = db_conn.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfIdExists(Long id) throws SQLException {
        try {
            this.preparedStatement = this.conn.prepareStatement(checkIfAddressIdExistsQuery);
            preparedStatement.setLong(1, id);
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
    public List<AddressPojo> getAllRecords() throws SQLException {
        QueryRunner queryRunner = new QueryRunner();

        ResultSetHandler<List<AddressPojo>> resultSetHandler = new BeanListHandler<>(AddressPojo.class);

        return queryRunner.query(conn, Queries.selectAllAddressesQuery, resultSetHandler);

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
