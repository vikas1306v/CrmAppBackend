package com.crm.controller;

import com.crm.dto.response.GenericResponseBean;
import com.crm.service.impl.CommunicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/communication")
@CrossOrigin("*")
public class CommunicationController {
    private final CommunicationService communicationService;
   @PostMapping("/start-campaign/{campaignId}")
    public ResponseEntity<GenericResponseBean<?>> startCampaign(@PathVariable Long campaignId) {
       return  communicationService.startCampaign(campaignId);

    }
}
