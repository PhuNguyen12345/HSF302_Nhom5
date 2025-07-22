package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    @Override
    public Page<Book> getBooksByCategory(String category, Pageable pageable) {
        return bookRepository.findByCategory_NameIgnoreCase(category, pageable);
    }
    @Override
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
