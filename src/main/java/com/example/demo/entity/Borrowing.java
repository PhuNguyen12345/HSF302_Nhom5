package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import com.example.demo.enums.BorrowingStatus;

import jakarta.persistence.*;
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
    @Column(name="borrowing_id", nullable=false, unique=true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime borrowedAt;
    private LocalDateTime dueDate;
    private LocalDateTime returnedAt;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;
}
