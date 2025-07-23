package com.example.demo.entity;

import java.text.SimpleDateFormat;

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
public class Recomendation {
	@Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String reason;
    private SimpleDateFormat createdAt;
}
