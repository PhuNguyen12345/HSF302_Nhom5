package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

import com.example.demo.enums.MembershipRole;

@Data
public class UserRegistrationDto {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number")
    private String phone;
    
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;
    
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;
    
    private MembershipRole membershipRole = MembershipRole.MEMBER;
    
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
