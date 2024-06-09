package com.crm.service.impl;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.entity.Customer;
import com.crm.entity.CustomerLoginActivity;
import com.crm.entity.Order;
import com.crm.enums.OrderStatus;
import com.crm.repository.CustomerLoginActivityRepository;
import com.crm.repository.CustomerRepository;
import com.crm.repository.OrderRepository;
import com.crm.service.LoginActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginActivityServiceImpl implements LoginActivityService {
    private final CustomerLoginActivityRepository customerLoginActivityRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void saveLoginActivity(LoginActivityDto loginActivityDto) {
        Long customerId = loginActivityDto.getCustomerId();
        Customer customer=customerRepository.findById(customerId).get();
        customerLoginActivityRepository.findByCustomer(customer).ifPresentOrElse(
                customerLoginActivity -> {
                    switch (loginActivityDto.isLoginActivity() ? 0 : 1) {
                        case 0:
                            customerLoginActivity.setNoOfSuccessfulLogins(customerLoginActivity.getNoOfSuccessfulLogins() + 1);
                            break;
                        case 1:
                            customerLoginActivity.setTotalSpends(calculateTotalSpendByPurchasingOrder(customerId)+loginActivityDto.getSpend());
                            customerLoginActivityRepository.save(customerLoginActivity);
                            return;
                    }
                    customerLoginActivity.setLoginDate(loginActivityDto.getLoginDate());
                    customerLoginActivity.setLoginTime(loginActivityDto.getLoginTime());
                    customerLoginActivityRepository.save(customerLoginActivity);
                },
                () -> {
                    CustomerLoginActivity customerLoginActivity = new CustomerLoginActivity();
                    customerLoginActivity.setCustomer(customer);
                    customerLoginActivity.setLoginDate(loginActivityDto.getLoginDate());
                    customerLoginActivity.setLoginTime(loginActivityDto.getLoginTime());
                    customerLoginActivity.setNoOfSuccessfulLogins(1);
                    customerLoginActivityRepository.save(customerLoginActivity);
                });
    }
    public Double calculateTotalSpendByPurchasingOrder(Long customerId) {
        if(orderRepository.findAllByCustomerId(customerId).isEmpty())
            return 0.0D;
        //calculate for those only who are delivered
     return  orderRepository.findAllByCustomerId(customerId).stream().
             filter((order)->order.getOrderStatus()== OrderStatus.DELIVERED).
             mapToDouble(Order::getTotalOrderAmount).sum();
    }
}
