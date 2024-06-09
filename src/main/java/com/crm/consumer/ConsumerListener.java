package com.crm.consumer;


import com.crm.dto.kafka.DeliveryReceiptDto;
import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.kafka.MessageDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import com.crm.repository.CommunicationLogRepository;
import com.crm.service.impl.CommunicationService;
import com.crm.service.impl.LoginActivityServiceImpl;
import com.crm.service.impl.MessageService;
import com.crm.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerListener {


    private final LoginActivityServiceImpl loginActivityService;
    private final OrderServiceImpl  orderService;
    private final MessageService messageService;
    private final CommunicationService communicationService;
    @KafkaListener(topics = "login-activity", groupId = "login-activity-group",containerFactory = "loginActivityEventKafkaListenerContainerFactory")
    public void listenCustomerLoginActivity(LoginActivityDto loginActivityDto) {
        System.out.println("Received login activity event: " + loginActivityDto);
        loginActivityService.saveLoginActivity(loginActivityDto);
    }
    @KafkaListener(topics = "order-delivery", groupId = "order-delivery-group",containerFactory = "orderDeliveryEventKafkaListenerContainerFactory")
    public void listenOrderDeliveryEvent(OrderDeliveryDto orderDeliveryDto) {
        System.out.println("Received order delivery event: " + orderDeliveryDto);
        orderService.saveOrderWithStatus(orderDeliveryDto);
    }
    @KafkaListener(topics = "send-message", groupId = "message-group",containerFactory = "bulkMessageSendEventKafkaListenerContainerFactory")
    public void listenSendMessageEvent(MessageDto messageDto) {
        String status = messageService.sendMessage(messageDto);
        communicationService.handleDeliveryReceipt(DeliveryReceiptDto.builder()
                        .campaign(messageDto.getCampaign())
                        .customer(messageDto.getCustomer())
                .communicationLog(messageDto.getCommunicationLog())
                .status(status)
                .build());
    }
}
