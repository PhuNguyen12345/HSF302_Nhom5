package com.example.demo.entity;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;

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
    private Long id;

    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private SimpleDateFormat dob;
    private String address;

    @Enumerated(EnumType.STRING)
    private MembershipRole membershipRole;

    private SimpleDateFormat createdAt;
    private SimpleDateFormat updatedAt;

}
