package com.crm.producer.event;

import com.crm.dto.kafka.OrderDeliveryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDeliveryEvent {
    @Autowired
    @Qualifier("orderDeliveryKafkaTemplate")
    private KafkaTemplate<String, OrderDeliveryDto> orderDeliveryKafkaTemplate;
    public void sendOrderDeliveryEvent(OrderDeliveryDto orderDeliveryDto) {
        orderDeliveryKafkaTemplate.send("order-delivery", orderDeliveryDto);
    }
}
