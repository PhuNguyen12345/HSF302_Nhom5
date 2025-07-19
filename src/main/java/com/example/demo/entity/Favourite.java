package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Reccomendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Favourite {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favourite_id", nullable=false, unique=true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String reason;
    private LocalDateTime createdAt;
}
