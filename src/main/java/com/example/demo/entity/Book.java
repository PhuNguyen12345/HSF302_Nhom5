package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;
import java.util.List;

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

    @NotBlank(message = "Book's name is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Author's name is required")
    @Size(max = 255, message = "Author must not exceed 255 characters")
    private String author;

    @NotBlank(message = "Publisher is required")
    @Size(max = 255, message = "Publisher must not exceed 255 characters")
    private String publisher;

    @Min(value = 1000, message = "Year published must be above 1000")
    @Max(value = 2030, message = "Year published must be above 2000")
    private Integer yearPublished;

    @Size(max = 20, message = "ISBN must not exceed 20 characters")
    private String isbn;

    @Size(max = 50, message = "Language must not exceed 50 characters")
    private String language;

    @Min(value = 1, message = "Pages must be at least 1")
    private int pages;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @URL(message = "Cover image's URL must be a valid URL")
    private String coverImageUrl;

    @URL(message = "File's URL must be a valid URL")
    private String fileUrl;

    @OneToMany
    @JoinColumn(name = "review_id")
    private List<Review> reviews;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    public float getAverageRating() {
        if (reviews == null || reviews.isEmpty()) return 0f;
        float sum = 0;
        for (Review r : reviews) {
            sum += r.getRating();
        }
        return sum / reviews.size();
    }

}
