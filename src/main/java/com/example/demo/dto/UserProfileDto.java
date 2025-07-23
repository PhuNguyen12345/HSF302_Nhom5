package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;

import com.example.demo.enums.MembershipRole;

@Data
public class UserProfileDto {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number")
    private String phone;
    
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;
    
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;
    
    private MembershipRole membershipRole;
    
    // For password change
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;
    
    private String confirmNewPassword;
    
    public boolean isNewPasswordMatching() {
        if (newPassword == null || newPassword.isEmpty()) {
            return true; // No password change
        }
        return newPassword.equals(confirmNewPassword);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") String name) {
        this.name = name;
    }

    public @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number") String getPhone() {
        return phone;
    }

    public void setPhone(@Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number") String phone) {
        this.phone = phone;
    }

    public @Past(message = "Date of birth must be in the past") LocalDate getDob() {
        return dob;
    }

    public void setDob(@Past(message = "Date of birth must be in the past") LocalDate dob) {
        this.dob = dob;
    }

    public @Size(max = 255, message = "Address must not exceed 255 characters") String getAddress() {
        return address;
    }

    public void setAddress(@Size(max = 255, message = "Address must not exceed 255 characters") String address) {
        this.address = address;
    }

    public MembershipRole getMembershipRole() {
        return membershipRole;
    }

    public void setMembershipRole(MembershipRole membershipRole) {
        this.membershipRole = membershipRole;
    }

    public @Size(min = 6, message = "Password must be at least 6 characters") String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@Size(min = 6, message = "Password must be at least 6 characters") String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
