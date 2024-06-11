package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rule;
    private boolean campaignOver;
    private LocalDateTime startDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_campaign",
            joinColumns = @JoinColumn(name = "campaign_rule_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )@JsonIgnore
    private List<Customer> customers;
    @OneToMany(mappedBy = "campaignRule", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CommunicationLog> communicationLog;
}
