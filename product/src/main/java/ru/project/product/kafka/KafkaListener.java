package ru.project.product.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.project.product.dto.CreateOrderDto;
import ru.project.product.exception.IncorrectOrderException;
import ru.project.product.service.ProductService;


@RequiredArgsConstructor
@Component
public class KafkaListener {

    private final ProductService productService;
    private final ObjectMapper objectMapper;


    @org.springframework.kafka.annotation.KafkaListener(topics = "orders", groupId = "product-group", containerFactory = "kafkaListenerContainerFactory")
    public void orderListener(String order) {
        try {
            CreateOrderDto orderDto = objectMapper.readValue(order, CreateOrderDto.class);
            productService.changeProductCount(orderDto.getProductId(), orderDto.getOrderCount(), orderDto.getOrderSign());
        } catch (Exception e) {
            throw new IncorrectOrderException("Incorrect order!");
        }
    }
}
