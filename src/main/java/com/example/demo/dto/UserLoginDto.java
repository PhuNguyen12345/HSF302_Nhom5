package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserLoginDto {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    private boolean rememberMe = false;
}
