package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.example.demo.enums.BorrowingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull(message = "Book is required")
    private Book book;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private BorrowingStatus status;

    @PrePersist
    protected void onCreate() {
        borrowedAt = LocalDateTime.now();
        dueDate = LocalDate.now().plusDays(14); // 14 days borrowing period
        status = BorrowingStatus.BORROWED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "User is required") User getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "User is required") User user) {
        this.user = user;
    }

    public @NotNull(message = "Book is required") Book getBook() {
        return book;
    }

    public void setBook(@NotNull(message = "Book is required") Book book) {
        this.book = book;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public @NotNull(message = "Status is required") BorrowingStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") BorrowingStatus status) {
        this.status = status;
    }
}
