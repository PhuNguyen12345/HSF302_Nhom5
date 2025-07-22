package com.example.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.enums.MembershipRole;

import lombok.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String passwordHash;

    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Please provide a valid phone number")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Membership role is required")
    private MembershipRole membershipRole;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (membershipRole == null) {
            membershipRole = MembershipRole.MEMBER;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
