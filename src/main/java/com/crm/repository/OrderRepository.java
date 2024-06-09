package com.crm.repository;

import com.crm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findAllByCustomerId(@Param("customerId") Long customerId);
    Optional<Order> findByOrderId(String orderId);
}
