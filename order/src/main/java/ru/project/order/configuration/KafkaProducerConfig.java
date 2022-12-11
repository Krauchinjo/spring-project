package ru.project.order.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.project.order.dto.CreateOrderDto;
import ru.project.order.exception.OrderNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-server-config}")
    private String bootstrapServerConfig;



    @Bean
    public ProducerFactory<String, CreateOrderDto> producerFactory() {
        try{
            Map<String, Object> config = new HashMap<>();
            config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
            config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(config);
        } catch (Exception e) {
            throw new OrderNotFoundException("awd");
        }

    }


    @Bean
    public KafkaTemplate<String, CreateOrderDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
