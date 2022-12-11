package ru.project.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.project.order.client.OrderClient;
import ru.project.order.dto.CreateOrderDto;
import ru.project.order.dto.OrderRequest;
import ru.project.order.dto.ProductDto;
import ru.project.order.exception.BadRequestException;
import ru.project.order.exception.OrderNotFoundException;
import ru.project.order.exception.UncorrectProductCountException;
import ru.project.order.kafka.KafkaSender;
import ru.project.order.model.Order;
import ru.project.order.repository.OrderRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderClient orderClient;
    private final KafkaSender kafkaSender;


    @Override
    public List<Order> allOrders() {
        return orderRepository.findAllOrders();
    }

    @Override
    public Order getById(Integer id) throws OrderNotFoundException {
        return orderRepository.findOrderById(id);
    }

    @Override
    public List<ProductDto> getSaleProducts(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        LocalDate dateNow = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String products = orderRepository.findAllOrders().stream()
                .filter(orders -> LocalDate.parse(orders.getOrderDate(), formatter).isAfter(dateNow.minusMonths(1)))
                .filter(orders -> orders.getCustomerId() == id)
                .map(orders -> orders.getProductId())
                .distinct()
                .collect(Collectors.toList()).toString()
                .replace("[", "").replace("]", "");
        List<ProductDto> saleProducts = orderClient.getSaleProducts(products);
        return saleProducts;
    }

    @Override
    public void addOrder(OrderRequest request) throws BadRequestException {
        int id = request.getProductId();
        int orderCount = request.getCount();
        ProductDto productDto = orderClient.getProduct(id);
        int productCount = productDto.getCount();
        if (orderCount <= productCount) {
            request.setProductName(productDto.getName());
            request.setPrice(productDto.getPrice());
            request.setSum(request.getPrice() * orderCount);
            orderRepository.addNewOrder(request);
            try {
                kafkaSender.sendOrder(new CreateOrderDto(id, orderCount, CreateOrderDto.Sign.reduce));
            } catch (Exception e) {
                log.error("Kafka error", e) ;
            }
        } else throw new UncorrectProductCountException(
                String.format("Ukazannoe kolichestvo dlya pokupki (%s) bolshe, chem est tovara na sklade (%s)", orderCount, productCount));
    }


    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteOrderById(id);
    }
 String awd = new String("dwa");
    int i =10;
    @Override
    public void changeOrder(Integer id, OrderRequest request) {
        awd = "awdawdawd";
        i=11;
        int productId = request.getProductId();
        int orderCount = request.getCount();
        ProductDto productDto = orderClient.getProduct(productId);
        int productCount = productDto.getCount();
        double price = productDto.getPrice();
        String productName = productDto.getName();
        if (orderCount <= productCount) {
            int currentOrderCount = orderRepository.findOrderById(id).getCount();
            if (currentOrderCount > orderCount) {
                orderRepository.changeOrderById(id, request, price, productName);
                int delta = currentOrderCount - orderCount;
                kafkaSender.sendOrder(new CreateOrderDto(productId, delta, CreateOrderDto.Sign.add));
            } else if (currentOrderCount < orderCount) {
                orderRepository.changeOrderById(id, request, price, productName);
                int delta = orderCount - currentOrderCount;
                kafkaSender.sendOrder(new CreateOrderDto(productId, delta, CreateOrderDto.Sign.reduce));
            } else {
                orderRepository.changeOrderById(id, request, price, productName);
            }
        } else throw new UncorrectProductCountException(
                String.format("Ukazannoe kolichestvo dlya pokupki (%s) bolshe, chem est tovara na sklade (%s)", orderCount, productCount));

    }

    @Override
    public ProductDto getProduct(int id) {
        return orderClient.getProduct(id);
    }

    @Override
    public List<ProductDto> getProducts() {
        return orderClient.getAllProducts();
    }

}
