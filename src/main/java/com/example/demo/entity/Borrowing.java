package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.enums.BorrowingStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Borrowings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
}
