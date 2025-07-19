package com.example.demo.entity;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id;

    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private LocalDateTime dob;
    private String address;

    @Enumerated(EnumType.STRING)
    private MembershipRole membershipRole;
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
    @OneToMany(mappedBy = "user")
    private List<Favourite> favoriteBooks;
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
