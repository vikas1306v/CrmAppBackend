package com.crm.controller;

import com.crm.dto.request.RuleRequestDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.CampaignRule;
import com.crm.entity.CommunicationLog;
import com.crm.entity.Customer;
import com.crm.service.impl.CampaignRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaign")
public class CampaignRuleController {
    private final CampaignRuleService campaignRuleService;


    @PostMapping("/get-audience-size")
    public ResponseEntity<GenericResponseBean<Integer>> sizeAudience(@RequestBody  RuleRequestDto ruleRequestDto) {
        return campaignRuleService.sizeAudience(ruleRequestDto);
    }
    @PostMapping("/create-campaign")
    public ResponseEntity<GenericResponseBean<CampaignRule>> saveAudience(@RequestBody  RuleRequestDto ruleRequestDto) {
        return campaignRuleService.saveAudience(ruleRequestDto);
    }
    @GetMapping("/get-all-campaign")
    public ResponseEntity<GenericResponseBean<List<CampaignRule>>> getAllCampaignRule(
    ) {
        return campaignRuleService.getAllCampaign();
    }

}
