package ru.project.order.service;

import ru.project.order.dto.OrderRequest;
import ru.project.order.dto.ProductDto;
import ru.project.order.model.Order;

import java.util.List;


public interface OrderService {
    List<Order> allOrders();

    Order getById(Integer id);

    void addOrder(OrderRequest request);

    void deleteOrder(Integer id);

    void changeOrder(Integer id, OrderRequest request);

    ProductDto getProduct(int id);

    List<ProductDto> getProducts();

    public List<ProductDto> getSaleProducts(int id);
}
