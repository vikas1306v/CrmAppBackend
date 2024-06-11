package com.crm.controller;

import com.crm.dto.request.AuthRequestDto;
import com.crm.dto.request.GoogleAuthRequestDto;
import com.crm.dto.response.AuthResponseDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
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
    @PostMapping("/google-auth")
    public ResponseEntity<GenericResponseBean<AuthResponseDto>> googleAuth(@RequestBody  GoogleAuthRequestDto authRequestDto) {
        return authService.googleAuth(authRequestDto);
    }
    @GetMapping("/get-all-customer")
    public ResponseEntity<GenericResponseBean<?>> getAllCustomer() {
        return authService.getAllCustomer();
    }

}
