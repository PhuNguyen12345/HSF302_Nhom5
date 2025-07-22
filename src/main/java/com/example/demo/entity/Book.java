package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "books", indexes = {
        @Index(name = "category_id", columnList = "category_id")
})
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author must not exceed 255 characters")
    private String author;

    @Size(max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @Min(value = 1000, message = "Year published must be valid")
    @Max(value = 2030, message = "Year published must be valid")
    private Integer yearPublished;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Min(value = 1, message = "Pages must be at least 1")
    private int pages;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private String coverImageUrl;

    private String fileUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
