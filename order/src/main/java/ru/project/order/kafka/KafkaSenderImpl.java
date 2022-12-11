package ru.project.order.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.project.order.dto.CreateOrderDto;

@Component
@RequiredArgsConstructor
public class KafkaSenderImpl implements KafkaSender {

    private final KafkaTemplate<String, CreateOrderDto> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;


    @Override
    public void sendOrder(CreateOrderDto createOrderDto) {
        kafkaTemplate.send(topic, createOrderDto);
    }

}
