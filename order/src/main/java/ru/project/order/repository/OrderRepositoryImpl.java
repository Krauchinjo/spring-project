package ru.project.order.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.project.order.client.OrderClient;
import ru.project.order.dto.OrderRequest;
import ru.project.order.exception.OrderNotFoundException;
import ru.project.order.model.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OrderClient orderClient;

    @Override
    public int getLastId() {
        Order order;
        order = jdbcTemplate.queryForObject("SELECT * FROM orders ORDER BY \"orderId\" DESC LIMIT 1",
                new BeanPropertyRowMapper<>(Order.class));
        return order.getOrderId() + 1;
    }

    @Override
    public int getLastNum() {
        Order order;
        order = jdbcTemplate.queryForObject("SELECT * FROM orders ORDER BY \"orderId\" DESC LIMIT 1",
                new BeanPropertyRowMapper<>(Order.class));
        return order.getOrderNumber() + 1;
    }

    @Override
    public List<Order> findAllOrders() {
        return jdbcTemplate.query("SELECT * FROM orders", new BeanPropertyRowMapper<>(Order.class));
    }


    @Override
    public Order findOrderById(Integer id) throws OrderNotFoundException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM orders WHERE \"orderId\"=? ", new Object[]{id}, new BeanPropertyRowMapper<>(Order.class));
        } catch (EmptyResultDataAccessException e) {
            throw new OrderNotFoundException(String.format("Order with id %s not found", id));
        }

    }


    @Override
    public void addNewOrder(OrderRequest request) {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

        jdbcTemplate.update("INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                new OrderRepositoryImpl(jdbcTemplate, orderClient).getLastId(),
                getLastNum(),
                formatForDateNow.format(dateNow),
                request.getCustomerId(),
                request.getProductId(),
                request.getProductName(),
                request.getPrice(),
                request.getCount(),
                request.getSum());
    }

    @Override
    public void deleteOrderById(Integer id) {
        jdbcTemplate.update("DELETE FROM orders WHERE \"orderId\" =?", id);
    }


    @Override
    public void changeOrderById(Integer id, OrderRequest request, double price, String productName) {
        jdbcTemplate.update("UPDATE orders SET \"orderNumber\"=?, \"orderDate\"=?, " +
                        "\"customerId\"=?, \"productId\"=?, \"productName\"=?, price=?, count=?, sum=? WHERE \"orderId\" =?",
                request.getOrderNumber(),
                request.getOrderDate(),
                request.getCustomerId(),
                request.getProductId(),
                productName,
                price,
                request.getCount(),
                request.getCount() * price,
                id);
    }
}
