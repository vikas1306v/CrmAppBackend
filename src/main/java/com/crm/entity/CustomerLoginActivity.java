package com.crm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CustomerLoginActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
    private LocalDate loginDate;
    private LocalTime loginTime;
    private int noOfSuccessfulLogins;
    private double totalSpends;
}
