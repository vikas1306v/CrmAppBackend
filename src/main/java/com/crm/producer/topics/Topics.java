package com.crm.producer.topics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class Topics {
    @Bean
    public NewTopic loginActivity() {
        return TopicBuilder.name("login-activity")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic orderDelivery() {
        return TopicBuilder.name("order-delivery")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic orderCancellation() {
        return TopicBuilder.name("send-message")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
