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

    @Min(value = 0)
    @Max(value = 5)
    private Double rating;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title is required") @Size(max = 255, message = "Title must not exceed 255 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") @Size(max = 255, message = "Title must not exceed 255 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Author is required") @Size(max = 255, message = "Author must not exceed 255 characters") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotBlank(message = "Author is required") @Size(max = 255, message = "Author must not exceed 255 characters") String author) {
        this.author = author;
    }

    public @Size(max = 255, message = "Publisher must not exceed 255 characters") String getPublisher() {
        return publisher;
    }

    public void setPublisher(@Size(max = 255, message = "Publisher must not exceed 255 characters") String publisher) {
        this.publisher = publisher;
    }

    public @Min(value = 1000, message = "Year published must be valid") @Max(value = 2030, message = "Year published must be valid") Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(@Min(value = 1000, message = "Year published must be valid") @Max(value = 2030, message = "Year published must be valid") Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    public @Size(max = 20, message = "ISBN must not exceed 20 characters") String getIsbn() {
        return isbn;
    }

    public void setIsbn(@Size(max = 20, message = "ISBN must not exceed 20 characters") String isbn) {
        this.isbn = isbn;
    }

    public @Size(max = 50, message = "Language must not exceed 50 characters") String getLanguage() {
        return language;
    }

    public void setLanguage(@Size(max = 50, message = "Language must not exceed 50 characters") String language) {
        this.language = language;
    }

    @Min(value = 1, message = "Pages must be at least 1")
    public int getPages() {
        return pages;
    }

    public void setPages(@Min(value = 1, message = "Pages must be at least 1") int pages) {
        this.pages = pages;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public @Size(max = 1000, message = "Description must not exceed 1000 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 1000, message = "Description must not exceed 1000 characters") String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public @Min(value = 0) @Max(value = 5) Double getRating() {
        return rating;
    }

    public void setRating(@Min(value = 0) @Max(value = 5) Double rating) {
        this.rating = rating;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
