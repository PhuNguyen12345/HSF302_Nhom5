package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "book_reports", indexes = {
        @Index(name = "user_id", columnList = "user_id"),
        @Index(name = "book_id", columnList = "book_id")
})
public class BookReport {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "report_type", length = 50)
    private String reportType;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "report_at")
    private Instant reportAt;

}