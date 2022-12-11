package ru.project.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.project.order.dto.OrderRequest;
import ru.project.order.dto.ProductDto;
import ru.project.order.model.Order;
import ru.project.order.service.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> allOrders() {
        return orderService.allOrders();
    }

    @GetMapping("/order/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderService.getById(id);
    }

    @GetMapping("/personalDeal")
    public List<ProductDto> personalDeal (@RequestParam int id) {
        return orderService.getSaleProducts(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/order")
    public void addOrder(@RequestBody OrderRequest request) {
        orderService.addOrder(request);
    }

    @PutMapping("/order")
    public void updateOrder(@RequestParam int id, @RequestBody OrderRequest request) {
        orderService.changeOrder(id, request);
    }

    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/product")
    public ProductDto getProduct(@RequestParam int id) {
        return orderService.getProduct(id);
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProduct() {
        return orderService.getProducts();
    }

}
