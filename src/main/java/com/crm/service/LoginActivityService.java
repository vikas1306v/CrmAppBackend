package com.crm.service;

import com.crm.dto.kafka.LoginActivityDto;

public interface LoginActivityService {
    void saveLoginActivity(LoginActivityDto loginActivityDto);
}
