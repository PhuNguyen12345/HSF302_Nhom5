package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer yearPublished;
    private String isbn;
    private String language;
    private int pages;
    private String description;
    private String coverImageUrl;
    private String fileUrl;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "book")
    private List<Review> reviews;
    @OneToMany(mappedBy = "book")
    private List<Favourite> favoredByUsers;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
