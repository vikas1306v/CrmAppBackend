package com.crm.dto.kafka;

import com.crm.entity.CampaignRule;
import com.crm.entity.CommunicationLog;
import com.crm.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {
   private  Long customerId;
   private String message;
   private Long campaignRuleId;
}
