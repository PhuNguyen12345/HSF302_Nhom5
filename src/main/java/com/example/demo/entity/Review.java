package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "user_id", columnList = "user_id")
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating")
    private float rating;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
