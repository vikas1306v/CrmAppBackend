package com.crm.repository;

import com.crm.entity.CampaignRule;
import com.crm.repository.custom.CampaignRuleCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignRuleRepository extends JpaRepository<CampaignRule, Long>, CampaignRuleCustomRepository {
    @Query("SELECT c FROM CampaignRule c ORDER BY c.startDate DESC")
    List<CampaignRule> findAllOrderByStartDateDesc();
}
