package com.crm.config;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.kafka.MessageDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private Map<String, Object> commonConsumerConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, List.of("com.crm.dto.kafka","com.crm.entity"));
        return config;
    }
    @Bean
    public ConsumerFactory<String, LoginActivityDto> loginActivityEventConsumerFactory() {
        Map<String, Object> config = commonConsumerConfigs();
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.crm.dto.kafka.LoginActivityDto");
        return new DefaultKafkaConsumerFactory<>(config);
    }
    @Bean
    public ConsumerFactory<String, OrderDeliveryDto> orderDeliveryEventConsumerFactory() {
        Map<String, Object> config = commonConsumerConfigs();
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.crm.dto.kafka.OrderDeliveryDto");
        return new DefaultKafkaConsumerFactory<>(config);
    }
    @Bean
    public ConsumerFactory<String, MessageDto> bulkMessageSendEventConsumerFactory() {
        Map<String, Object> config = commonConsumerConfigs();
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.crm.dto.kafka.MessageDto");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoginActivityDto> loginActivityEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LoginActivityDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(loginActivityEventConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderDeliveryDto> orderDeliveryEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderDeliveryDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderDeliveryEventConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageDto> bulkMessageSendEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(bulkMessageSendEventConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}
