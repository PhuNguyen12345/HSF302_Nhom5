package com.example.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class BookCreateDTO {
    private String title;
    private String author;
    private String publisher;
    private Integer yearPublished;
    private String isbn;
    private String language;
    private Integer pages;
    private String description;
    private Long categoryId;

    private MultipartFile coverImage; // Ảnh bìa
    private MultipartFile pdfFile;    // Tệp sách (PDF)
}
