package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    boolean existsByTitle(String title);

    boolean existsByIsbn(String isbn);

    List<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    boolean existsByTitleAndIdNot(String title, Long id);

    List<Book> findByCategory_NameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsByIsbnAndIdNot(String isbn, Long id);

    @Query("SELECT b FROM Book b " +
            "WHERE (:title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId)")
    List<Book> findByFilters(@Param("title") String title,
                             @Param("author") String author,
                             @Param("categoryId") Long categoryId);

    @Query("SELECT b FROM Book b " +
            "WHERE (:title = '' OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:categoryId IS NULL OR b.category.id = :categoryId)")
    Page<Book> findByFiltersWithPaging(@Param("title") String title,
                                       @Param("author") String author,
                                       @Param("categoryId") Long categoryId,
                                       Pageable pageable);

    List<Book> findByCategory_NameContainingIgnoreCase(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByCategory_NameContainingIgnoreCaseAndRatingGreaterThan(String name, Double rating);

}
