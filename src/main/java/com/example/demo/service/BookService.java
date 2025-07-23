package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

@Service
public class BookService {
	@Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // Tìm kiếm sách theo tiêu chí
    public List<Book> searchBooks(String title, String author, String category, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
        } else if (category != null && !category.isEmpty()) {
            return bookRepository.findByCategory_NameContainingIgnoreCase(category, pageable);
        } else {
            return bookRepository.findAll(pageable).getContent();
        }
    }
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
