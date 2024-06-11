package com.crm.producer.event;

import com.crm.dto.kafka.MessageDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import com.crm.entity.CommunicationLog;
import com.crm.repository.CommunicationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendMessageEvent {

    @Autowired
    @Qualifier("bulkMessageKafkaTemplate")
    private KafkaTemplate<String, MessageDto> bulkMessageKafkaTemplate;
    public void sendBulkMessageEvent(MessageDto messageDto) {
        bulkMessageKafkaTemplate.send("send-message", messageDto);
    }
}
