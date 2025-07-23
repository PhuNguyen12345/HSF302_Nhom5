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

    public @NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Confirm password is required") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Confirm password is required") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number") String phone) {
        this.phone = phone;
    }

    public @Size(max = 255, message = "Address must not exceed 255 characters") String getAddress() {
        return address;
    }

    public void setAddress(@Size(max = 255, message = "Address must not exceed 255 characters") String address) {
        this.address = address;
    }

    public @Past(message = "Date of birth must be in the past") LocalDate getDob() {
        return dob;
    }

    public void setDob(@Past(message = "Date of birth must be in the past") LocalDate dob) {
        this.dob = dob;
    }

    public MembershipRole getMembershipRole() {
        return membershipRole;
    }

    public void setMembershipRole(MembershipRole membershipRole) {
        this.membershipRole = membershipRole;
    }

    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }
}
