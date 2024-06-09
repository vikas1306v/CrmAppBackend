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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunicationService {
    private final CampaignRuleRepository campaignRuleRepository;
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
        campaign.getCustomers().forEach((customer)->{

            sendMessages(customer,campaign);

        });
        return ResponseEntity.ok(GenericResponseBean.builder()
                .status(true).message("Campaign Started").build());
    }
    //create message body and send to another service through kafka
    public void sendMessages(Customer customer, CampaignRule  rule) {
            String message = String.format("Hi %s, here is 10%% off on your next order", customer.getName());
            sendMessageEvent.sendBulkMessageEvent(MessageDto.builder()
                    .customer(customer)
                    .message(message)
                    .campaign(rule)
                    .build());
    }

    //delivery receipt which update the status of the communication log
    @Transactional
    public void handleDeliveryReceipt(DeliveryReceiptDto deliveryReceipt) {
        String status = deliveryReceipt.getStatus();
        CommunicationLog communicationLog = deliveryReceipt.getCommunicationLog();
        communicationLog.setStatus(status);
        communicationLogRepository.save(communicationLog);
    }

}
