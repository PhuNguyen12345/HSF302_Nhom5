package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrowing;
import com.example.demo.entity.User;
import com.example.demo.enums.BorrowingStatus;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByUser(User user);

    List<Borrowing> findByBook(Book book);

    List<Borrowing> findByStatus(BorrowingStatus status);

    @Query("SELECT b.book, COUNT(b) as borrowCount FROM Borrowing b " +
           "GROUP BY b.book ORDER BY borrowCount DESC")
    List<Object[]> findMostBorrowedBooks();

    @Query("SELECT b.book, COUNT(b) as borrowCount FROM Borrowing b " +
           "WHERE b.book.category.id = :categoryId " +
           "GROUP BY b.book ORDER BY borrowCount DESC")
    List<Object[]> findMostBorrowedBooksByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(b) FROM Borrowing b WHERE b.borrowedAt >= :startDate")
    long countBorrowingsSince(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT b FROM Borrowing b WHERE b.status = :status AND b.dueDate < CURRENT_DATE")
    List<Borrowing> findOverdueBorrowings(@Param("status") BorrowingStatus status);

    @Query("SELECT COUNT(b) FROM Borrowing b WHERE (:user IS NULL OR b.user = :user) AND b.status = :status")
    long countByUserAndStatus(@Param("user") User user, @Param("status") BorrowingStatus status);

    long countByStatus(BorrowingStatus status);
}
