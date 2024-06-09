package com.crm.service.impl;

import com.crm.dto.kafka.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String sendMessage(MessageDto message) {
        // Send message to customer
        boolean isSent = Math.random() < 0.9; // 90% success rate
        return isSent ? "SENT" : "FAILED";
    }
}
