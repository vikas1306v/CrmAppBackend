package com.crm.exception;

import com.crm.dto.response.ErrorResponse;
import com.crm.dto.response.GenericResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponseBean<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage);
        return ResponseEntity.badRequest().body(GenericResponseBean.<ErrorResponse>builder()
                .status(false)
                .error(errorResponse)
                .build());
    }

}
