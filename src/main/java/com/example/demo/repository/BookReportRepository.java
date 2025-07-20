package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookReport;
import com.example.demo.entity.User;

import java.util.List;

@Repository
public interface BookReportRepository extends JpaRepository<BookReport, Long> {
    List<BookReport> findByBook_Id(Long bookId);
}
