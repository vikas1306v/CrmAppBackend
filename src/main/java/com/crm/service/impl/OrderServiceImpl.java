package com.crm.service.impl;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.kafka.OrderDeliveryDto;
import com.crm.dto.request.OrderRequestDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.dto.response.OrderResponseDto;
import com.crm.entity.Customer;
import com.crm.entity.Item;
import com.crm.entity.Order;
import com.crm.enums.OrderStatus;
import com.crm.producer.event.CustomerLoggingEvent;
import com.crm.producer.event.OrderDeliveryEvent;
import com.crm.repository.CustomerRepository;
import com.crm.repository.ItemRepository;
import com.crm.repository.OrderRepository;
import com.crm.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    private final InventoryServiceImpl inventoryService;
    private final ItemRepository  itemRepository;
    private final OrderRepository orderRepository;
    private final CustomerLoggingEvent customerLoggingEvent;
    private final OrderDeliveryEvent orderDeliveryEvent;

    @Override
    @Transactional
    public ResponseEntity<GenericResponseBean<OrderResponseDto>> createOrder(OrderRequestDto orderRequestDto) {
        Double orderTotalAmount = getOrderTotalAmount(orderRequestDto);
        System.out.println("orderTotalAmount" + orderTotalAmount);
        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        boolean isSufficientAmount = accountService.checkBalanceInAccountForOrder(customer.getEmail(), orderTotalAmount);
        if (!isSufficientAmount) {
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponseBean.<OrderResponseDto>builder()
                    .message("InsufficientAmountInBalance").build());
        }

        orderRequestDto.getOrderItems().forEach(orderItem -> {
            if (!inventoryService.checkQuantityInInventory(orderItem.getItemId(), orderItem.getQuantity())) {
                throw new RuntimeException("Item not available in inventory");
            }
        });

        Order order = saveOrder(orderRequestDto, orderTotalAmount, customer);

        try {
            sendEventForOrderDelivery(order);
        } catch (Exception e) {
            log.error("Error sending order delivery event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseBean.<OrderResponseDto>builder()
                    .message("Error processing order delivery").build());
        }

        try {
            sendEventForTotalSpend(customer.getId(),orderTotalAmount);
        } catch (Exception e) {
            log.error("Error sending total spend event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponseBean.<OrderResponseDto>builder()
                    .message("Error processing total spend").build());
        }

        return generateOrderResponse(order);
    }


    private ResponseEntity<GenericResponseBean<OrderResponseDto>> generateOrderResponse(Order order) {
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseBean.<OrderResponseDto>builder()
                .message("Order created successfully")
                .data(OrderResponseDto.builder().orderId(order.getOrderId()).orderStatus(order.getOrderStatus())
                        .orderDeliveryAddress(order.getDeliveryAddress()).build()).status(true)
                .build());
    }

    @Override
    public void saveOrderWithStatus(OrderDeliveryDto orderDeliveryDto) {
        Order order=orderRepository.findByOrderId(orderDeliveryDto.getOrderId()).orElseThrow(()->new RuntimeException("Order not found"));
        order.setOrderStatus(orderDeliveryDto.getOrderStatus());
        orderRepository.save(order);
    }

    private void sendEventForOrderDelivery(Order order) {
        orderDeliveryEvent.sendOrderDeliveryEvent(OrderDeliveryDto.builder().orderId(order.getOrderId()).orderStatus(OrderStatus.DELIVERED).build());
    }

    private void sendEventForTotalSpend(Long id, Double orderTotalAmount) {
        LoginActivityDto loginDto=LoginActivityDto.builder().customerId(id).spend(orderTotalAmount).isLoginActivity(false).build();
        customerLoggingEvent.sendLoginActivityEvent(loginDto);
    }

    private Order saveOrder(OrderRequestDto orderRequestDto, Double orderTotalAmount, Customer customer) {
        String orderId=generateOrderId();
        Order order=new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalOrderAmount(orderTotalAmount);
        order.setCustomer(customer);
        order.setOrderItems(orderRequestDto.getOrderItems());
        order.setDeliveryAddress(orderRequestDto.getDeliveryAddress());
        return orderRepository.save(order);
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString();
    }


    private Double getOrderTotalAmount(OrderRequestDto orderRequestDto) {
        return orderRequestDto.getOrderItems().stream().mapToDouble(orderItem -> {
            Item item=itemRepository.findById(orderItem.getItemId()).orElseThrow(()->new RuntimeException("Item not found"));
            return item.getPrice()*orderItem.getQuantity();
        }).sum();
    }
}
