package ru.project.order.repository;

import org.springframework.stereotype.Repository;
import ru.project.order.dto.OrderRequest;
import ru.project.order.model.Order;

import java.util.List;

@Repository
public interface OrderRepository {
    int getLastId();

    int getLastNum();

    List<Order> findAllOrders();

    Order findOrderById(Integer id);

    void addNewOrder(OrderRequest request);

    void deleteOrderById(Integer id);

    void changeOrderById(Integer id, OrderRequest request, double price, String productName);

}
