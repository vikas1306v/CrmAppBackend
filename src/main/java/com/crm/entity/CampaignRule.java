package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rule;
    private boolean campaignOver;
    private LocalDateTime startDate;
    @OneToMany(mappedBy = "campaignRule", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Customer> customers;
    @OneToMany(mappedBy = "campaignRule", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CommunicationLog> communicationLog;
}
