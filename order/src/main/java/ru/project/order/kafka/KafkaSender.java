package ru.project.order.kafka;

import ru.project.order.dto.CreateOrderDto;

public interface KafkaSender {
    void sendOrder(CreateOrderDto createOrderDto);
}
