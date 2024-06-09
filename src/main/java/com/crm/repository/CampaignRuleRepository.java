package com.crm.repository;

import com.crm.entity.CampaignRule;
import com.crm.repository.custom.CampaignRuleCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRuleRepository extends JpaRepository<CampaignRule, Long>, CampaignRuleCustomRepository {

}
