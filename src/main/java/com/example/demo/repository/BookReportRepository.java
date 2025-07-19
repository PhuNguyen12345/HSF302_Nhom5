package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookReport;
import com.example.demo.entity.User;

@Repository
public interface BookReportRepository extends JpaRepository<BookReport, Long> {

}
