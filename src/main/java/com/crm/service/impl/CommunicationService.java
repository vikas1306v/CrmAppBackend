package com.crm.service.impl;

import com.crm.dto.kafka.DeliveryReceiptDto;
import com.crm.dto.kafka.MessageDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.CampaignRule;
import com.crm.entity.CommunicationLog;
import com.crm.entity.Customer;
import com.crm.producer.event.SendMessageEvent;
import com.crm.repository.CampaignRuleRepository;
import com.crm.repository.CommunicationLogRepository;
import com.crm.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final CampaignRuleRepository campaignRuleRepository;
    private final CustomerRepository customerRepository;
    private final SendMessageEvent  sendMessageEvent;
    private final CommunicationLogRepository communicationLogRepository;
    public ResponseEntity<GenericResponseBean<?>> startCampaign(Long campaignId) {
        CampaignRule campaign = campaignRuleRepository.findById(campaignId).orElse(null);
        if(campaign == null) {
            return ResponseEntity.status(404).body(GenericResponseBean.builder()
                    .status(false).message("Campaign not Exist").build());
        }
        if(campaign.isCampaignOver()) {
            return ResponseEntity.status(400).body(GenericResponseBean.builder()
                    .status(false).message("Campaign is Over").build());
        }
//        List<Customer> customers = customerRepository.findByCampaignRules_Id(campaignId);
        campaign.getCustomers().forEach((customer)->{
            System.out.println("Sending message to "+customer.getName());
            sendMessages(customer,campaign);
        });
        return ResponseEntity.ok(GenericResponseBean.builder()
                .status(true).message("Campaign Started").build());
    }
    //create message body and send to another service through kafka
    public void sendMessages(Customer customer, CampaignRule  rule) {
            String message = String.format("Hi %s, here is 10%% off on your next order", customer.getName());
            sendMessageEvent.sendBulkMessageEvent(MessageDto.builder()
                    .customerId(customer.getId())
                    .message(message)
                    .campaignRuleId(rule.getId())
                    .build());
    }

    //delivery receipt which update the status of the communication log
    @Transactional
    public void handleDeliveryReceipt(DeliveryReceiptDto deliveryReceipt) {
        CommunicationLog communicationLog = deliveryReceipt.getCommunicationLog();
        communicationLog.setCampaignRule(campaignRuleRepository.findById(deliveryReceipt.getCampaign()).orElse(null));
        communicationLog.setCustomerId(deliveryReceipt.getCustomer());
        communicationLogRepository.save(communicationLog);
    }

}
