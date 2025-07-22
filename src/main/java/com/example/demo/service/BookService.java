package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    Page<Book> getAllBooks(Pageable pageable);

    Page<Book> getBooksByCategory(String category, Pageable pageable);

    Page<Book> searchBooks(String keyword, Pageable pageable);

    Book getBookById(Long id);

}
