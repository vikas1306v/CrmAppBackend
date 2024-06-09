package com.crm.controller;

import com.crm.dto.request.OrderRequestDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.dto.response.OrderResponseDto;
import com.crm.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponseBean<OrderResponseDto>> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }
}
