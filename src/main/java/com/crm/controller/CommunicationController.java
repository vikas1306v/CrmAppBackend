package com.crm.controller;

import com.crm.dto.response.GenericResponseBean;
import com.crm.service.impl.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communication")
public class CommunicationController {
    private final CommunicationService communicationService;
   @PostMapping("/start-campaign/{campaignId}")
    public ResponseEntity<GenericResponseBean<?>> startCampaign(@PathVariable Long campaignId) {
       return  communicationService.startCampaign(campaignId);

    }
}
