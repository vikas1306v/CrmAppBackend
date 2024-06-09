package com.crm.repository;

import com.crm.entity.Customer;
import com.crm.entity.CustomerLoginActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerLoginActivityRepository extends JpaRepository<CustomerLoginActivity, Long> {
    Optional<CustomerLoginActivity> findByCustomer(Customer customer);
}
