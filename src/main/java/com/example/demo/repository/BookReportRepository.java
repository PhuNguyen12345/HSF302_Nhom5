package com.example.demo.repository;

import com.example.demo.enums.ReportType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookReport;
import com.example.demo.entity.User;

import java.util.List;

@Repository
public interface BookReportRepository extends JpaRepository<BookReport, Long> {
    List<BookReport> findByBook_Id(Long bookId);

    @Query("SELECT r FROM BookReport r " +
            "WHERE (:userId IS NULL OR r.user.id = :userId) " +
            "AND (:bookId IS NULL OR r.book.id = :bookId) " +
            "AND (:reportType IS NULL OR r.reportType = :reportType)")
    Page<BookReport> findByFilters(@Param("userId") Long userId,
                                   @Param("bookId") Long bookId,
                                   @Param("reportType") ReportType reportType,
                                   Pageable pageable);

}
