package com.crm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;



@AllArgsConstructor
@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;

}
