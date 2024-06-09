package com.crm.repository.custom;

import com.crm.dto.request.RuleRequestDto;
import com.crm.entity.Customer;

import java.util.List;

public interface CampaignRuleCustomRepository {
     List<Customer> findAudienceFromCampaignRule(RuleRequestDto ruleRequestDto);

}
