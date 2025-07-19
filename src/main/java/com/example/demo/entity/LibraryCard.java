package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "library_cards", indexes = {
        @Index(name = "user_id", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "card_number", columnNames = {"card_number"})
})
public class LibraryCard {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number", length = 50)
    private String cardNumber;

    @Column(name = "barcode", length = 100)
    private String barcode;

    @Column(name = "issued_at")
    private Instant issuedAt;

    @Column(name = "expired_at")
    private Instant expiredAt;

}