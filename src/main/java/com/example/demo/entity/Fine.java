package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "fines", indexes = {
        @Index(name = "borrowing_id", columnList = "borrowing_id")
})
public class Fine {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "borrowing_id", nullable = false)
    private Borrowing borrowing;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Column(name = "paid_at")
    private Instant paidAt;

}
