package com.crm.service.impl;

import com.crm.dto.kafka.LoginActivityDto;
import com.crm.dto.request.AuthRequestDto;
import com.crm.dto.response.AuthResponseDto;
import com.crm.dto.response.GenericResponseBean;
import com.crm.entity.Customer;
import com.crm.producer.event.CustomerLoggingEvent;
import com.crm.repository.CustomerRepository;
import com.crm.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerLoggingEvent customerLoggingEvent;
    @Override
    public ResponseEntity<GenericResponseBean<AuthResponseDto>> authenticateCustomer(AuthRequestDto authRequestDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
            Customer customer = (Customer) authenticate.getPrincipal();
            System.out.println("authenticate------->"+ customer.getName());
            String accessToken = jwtService.generateToken(authRequestDto.getEmail());
            //generate the event for login activity
            handleCustomerSuccessfullyLogin(customer);
            return ResponseEntity.ok(GenericResponseBean.<AuthResponseDto>builder()
                    .status(true).
                    data(AuthResponseDto.builder().accessToken(accessToken)
                            .email(authRequestDto.getEmail()).name(customer.getName()).build()).build());
        } catch (AuthenticationException authenticationException) {
            return ResponseEntity.badRequest().body(GenericResponseBean.<AuthResponseDto>builder()
                    .status(false).message("Invalid Credentials").build());
        }
    }

    private void handleCustomerSuccessfullyLogin(Customer customer) {
        customerLoggingEvent.sendLoginActivityEvent(LoginActivityDto.builder().customerId(customer.getId())
                .loginDate(LocalDate.now()).loginTime(LocalTime.now()).isLoginActivity(true).
                build());
    }

    @Transactional
    @Override
    public ResponseEntity<GenericResponseBean<AuthResponseDto>> createCustomer(AuthRequestDto authRequestDto) {
        Customer customer = customerRepository.findByEmail(authRequestDto.getEmail()).orElse(null);
        if(customer != null) {
            return ResponseEntity.badRequest().body(GenericResponseBean.<AuthResponseDto>builder()
                    .status(false).message("User already exists").build());
        }
        customer = new Customer();
        customerRepository.save(customer);
        createNewCustomer(authRequestDto, customer);
        String accessToken = jwtService.generateToken(authRequestDto.getEmail());
        return ResponseEntity.status(201).body(GenericResponseBean.<AuthResponseDto>builder()
                        .data(AuthResponseDto.builder().email(authRequestDto.getEmail()).accessToken(accessToken).name(authRequestDto.getName()).build())
                .status(true).message("User created successfully").build());
    }

    private void createNewCustomer(AuthRequestDto authRequestDto, Customer customer) {
        customer.setEmail(authRequestDto.getEmail());
        customer.setName(authRequestDto.getName());
        customer.setRole(authRequestDto.getRole());
        customer.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));
    }
}
