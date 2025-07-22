package com.example.demo.entity;

import com.example.demo.enums.BorrowingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "borrowings", indexes = {
        @Index(name = "book_id", columnList = "book_id"),
        @Index(name = "user_id", columnList = "user_id")
})
public class Borrowing {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDate returnedAt;

    @Column(name = "borrowed_at")
    private Instant borrowedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private BorrowingStatus status;

}
