package dao;

import dao.Dao;
import entity.OrderPojo;
import entity.ProductPojo;

import java.util.List;

public interface OrderDao<T> extends Dao {
    List<ProductPojo> getOrderProductsFromDB(Long orderId);

}
