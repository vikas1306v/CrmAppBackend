package com.crm.dto.request;

import com.crm.entity.OrderItem;
import com.crm.enums.PaymentMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequestDto {
    private Long customerId;
    private List<OrderItem> orderItems;
    private PaymentMode paymentMode;
    private String deliveryAddress;
}
