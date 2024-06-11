package com.crm.service.impl;

import com.crm.dto.request.ConditionRequestDto;
import com.crm.dto.request.RuleRequestDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.CampaignRule;
import com.crm.entity.Customer;
import com.crm.repository.CampaignRuleRepository;
import com.crm.repository.CommunicationLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CampaignRuleService {
    private final CampaignRuleRepository campaignRuleRepository;
    private final CommunicationLogRepository communicationLogRepository;
    public CampaignRule createCampaignRule(RuleRequestDto ruleRequestDto) {
        CampaignRule campaignRule = new CampaignRule();
        String replaceAll=getStringOfCampaignRule(ruleRequestDto);
        campaignRule.setRule(replaceAll);
        return campaignRule;
    }
    private static String getStringOfCampaignRule(RuleRequestDto ruleRequestDto) {
        StringBuilder rule= new StringBuilder();
        for(ConditionRequestDto conditionRequestDto: ruleRequestDto.getRules()){
            if(conditionRequestDto.getConnector()==null){
                rule.append(conditionRequestDto.getField()).append(" ").append(conditionRequestDto.getCondition()).append(" ").append(conditionRequestDto.getValue()).append(" ");
                continue;
            }
            rule.append(conditionRequestDto.getField()).append(" ").append(conditionRequestDto.getCondition()).append(" ").append(conditionRequestDto.getValue()).append(" ").append(conditionRequestDto.getConnector().toLowerCase()).append(" ");
        }
        String ruleString = rule.toString();
        return ruleString.replaceAll("null", "").trim();

    }
    public ResponseEntity<GenericResponseBean<Integer>> sizeAudience(RuleRequestDto ruleRequestDto) {
        List<Customer> audienceFromCampaignRule = campaignRuleRepository.findAudienceFromCampaignRule(ruleRequestDto);
        System.out.println(audienceFromCampaignRule.size());
        Integer size=  audienceFromCampaignRule.size();
        return ResponseEntity.status(200).body(GenericResponseBean.<Integer>builder().
                message("AudienceSize ").status(true).data(size).build());
    }
    @Transactional
    public ResponseEntity<GenericResponseBean<CampaignRule>> saveAudience(RuleRequestDto ruleRequestDto) {
        List<Customer> audienceFromCampaignRule = campaignRuleRepository.findAudienceFromCampaignRule(ruleRequestDto);
        if(audienceFromCampaignRule.isEmpty()){
            return ResponseEntity.status(200).body(GenericResponseBean.<CampaignRule>builder().
                    message("AudienceSize isZero No Need To create this campaign ").status(false).data(null).build());
        }
        CampaignRule campaignRule = createCampaignRule(ruleRequestDto);
        campaignRule.setStartDate(LocalDateTime.now());
        campaignRule.setCustomers(audienceFromCampaignRule);
        campaignRule.setCampaignOver(false);
        campaignRule=campaignRuleRepository.save(campaignRule);
        return ResponseEntity.status(200).body(GenericResponseBean.<CampaignRule>builder().
                message("AudienceSavedToLogTable ").status(true).data(campaignRule).build());
    }

    public  ResponseEntity<GenericResponseBean<List<CampaignRule>>> getAllCampaign() {
        return ResponseEntity.status(200).body(GenericResponseBean.<List<CampaignRule>>builder().
                message("AllCampaignRule ").status(true).data(campaignRuleRepository.findAllOrderByStartDateDesc()).build());

    }
}
