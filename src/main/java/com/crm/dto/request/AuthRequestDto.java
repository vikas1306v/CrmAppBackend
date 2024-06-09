package com.crm.dto.request;

import com.crm.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDto {
    @JsonProperty("name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonProperty("email")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private Role role;
}
