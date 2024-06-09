package com.crm.service;

import com.crm.dto.kafka.OrderDeliveryDto;
import com.crm.dto.request.OrderRequestDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.dto.response.OrderResponseDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<GenericResponseBean<OrderResponseDto>> createOrder(OrderRequestDto orderRequestDto);

    void saveOrderWithStatus(OrderDeliveryDto orderDeliveryDto);
}
