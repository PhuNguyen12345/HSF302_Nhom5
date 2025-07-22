package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByCategory_NameIgnoreCase(String name, Pageable pageable);

    Page<Book> findAll(Pageable pageable);

    Book findBookById(Long bookId);
}
