package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class BookReportService {
	@Autowired
    private BookReportRepository bookReportRepository;

    public List<BookReport> findAll() {
        return bookReportRepository.findAll();
    }

    public Optional<BookReport> findById(Long id) {
        return bookReportRepository.findById(id);
    }

    public BookReport save(BookReport bookReport) {
        return bookReportRepository.save(bookReport);
    }

    public void deleteById(Long id) {
        bookReportRepository.deleteById(id);
    }
}
