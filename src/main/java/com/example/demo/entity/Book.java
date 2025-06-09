package com.example.demo.entity;

import java.text.SimpleDateFormat;

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
    private SimpleDateFormat createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;
}
