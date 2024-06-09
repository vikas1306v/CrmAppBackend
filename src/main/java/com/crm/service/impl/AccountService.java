package com.crm.service.impl;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public boolean checkBalanceInAccountForOrder(String email, double orderAmount) {
        //fake implementation
        //in real world third party api call will be made to check the balance
        return true;
    }
}
