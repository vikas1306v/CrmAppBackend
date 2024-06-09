package com.crm.controller;

import com.crm.dto.request.AuthRequestDto;
import com.crm.dto.response.AuthResponseDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;
    @PostMapping("/register")
    public ResponseEntity<GenericResponseBean<AuthResponseDto>> createCustomer(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return authService.createCustomer(authRequestDto);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<GenericResponseBean<AuthResponseDto>> authenticateCustomer(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return authService.authenticateCustomer(authRequestDto);
    }

}
