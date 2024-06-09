package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="communications_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommunicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_rule_id")
    @JsonBackReference
    private CampaignRule campaignRule;
    private Long customerId;
    private String status;
}
