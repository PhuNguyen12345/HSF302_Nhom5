package com.example.demo.entity;

import jakarta.persistence.*;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year_published")
    private Integer yearPublished;

    @Column(name = "isbn", length = 50)
    private String isbn;

    @Column(name = "language", length = 50)
    private String language;

    @Column(name = "pages")
    private Integer pages;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "cover_img_url")
    private String coverImgUrl;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "created_at")
    private Instant createdAt;

}