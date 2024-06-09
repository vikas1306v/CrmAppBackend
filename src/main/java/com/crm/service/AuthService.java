package com.crm.service;

import com.crm.dto.request.AuthRequestDto;
import com.crm.dto.response.AuthResponseDto;
import com.crm.dto.response.GenericResponseBean;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<GenericResponseBean<AuthResponseDto>> authenticateCustomer(AuthRequestDto authRequestDto);
    ResponseEntity<GenericResponseBean<AuthResponseDto>> createCustomer(AuthRequestDto authRequestDto);
}
