package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    List<Book> findByCategory_NameContainingIgnoreCase(String name, Pageable pageable);

    List<Book> findByCategory_NameContainingIgnoreCase(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByCategory_NameContainingIgnoreCaseAndRatingGreaterThan(String name, Double rating);

}
