package com.crm.entity;

import com.crm.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private Double totalOrderAmount;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;
    private String deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;
}
