package com.crm.config;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.kafka.MessageDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {
    private Map<String, Object> commonProducerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return config;
    }
    @Bean
    public ProducerFactory<String, LoginActivityDto> loginActivityProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfigs());
    }

    @Bean
    public ProducerFactory<String, OrderDeliveryDto> orderDeliveryProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfigs());
    }
    @Bean
    public ProducerFactory<String, MessageDto> bulkMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(commonProducerConfigs());
    }


    @Bean
    public KafkaTemplate<String, LoginActivityDto> loginActivityKafkaTemplate() {
        return new KafkaTemplate<>(loginActivityProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, OrderDeliveryDto> orderDeliveryKafkaTemplate() {
        return new KafkaTemplate<>(orderDeliveryProducerFactory());
    }
    @Bean
    public KafkaTemplate<String, MessageDto> bulkMessageKafkaTemplate() {
        return new KafkaTemplate<>(bulkMessageProducerFactory());
    }

}
