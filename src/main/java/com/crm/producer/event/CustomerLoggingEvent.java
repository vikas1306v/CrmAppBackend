package com.crm.producer.event;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerLoggingEvent {

    @Qualifier("loginActivityKafkaTemplate")
    @Autowired
    private  KafkaTemplate<String, LoginActivityDto> loginActivityKafkaTemplate;

    public void sendLoginActivityEvent(LoginActivityDto loginActivityDto) {
        System.out.println("activity sending");
        loginActivityKafkaTemplate.send("login-activity", loginActivityDto);
    }

}
