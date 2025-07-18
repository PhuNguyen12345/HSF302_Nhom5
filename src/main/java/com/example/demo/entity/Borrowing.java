package com.example.demo.entity;

import java.text.SimpleDateFormat;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private SimpleDateFormat borrowedAt;
    private SimpleDateFormat dueDate;
    private SimpleDateFormat returnedAt;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;
}
