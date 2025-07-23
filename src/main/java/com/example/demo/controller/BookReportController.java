package com.example.demo.controller;


import com.example.demo.entity.BookReport;
import com.example.demo.enums.ReportType;
import com.example.demo.repository.BookReportRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@Controller
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class BookReportController {

    private final BookReportRepository reportRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    // List of all reports
    @GetMapping
    public String listReports(@RequestParam(required = false) Long userId,
                              @RequestParam(required = false) Long bookId,
                              @RequestParam(required = false) ReportType reportType,
                              @RequestParam(defaultValue = "0") int page,
                              Model model) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<BookReport> reportPage;

        if (userId == null && bookId == null && reportType == null) {
            reportPage = reportRepository.findAll(pageable);
        } else {
            reportPage = reportRepository.findByFilters(userId, bookId, reportType, pageable);
        }

        model.addAttribute("reportPage", reportPage);
        model.addAttribute("reports", reportPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportPage.getTotalPages());

        // giữ lại giá trị tìm kiếm
        model.addAttribute("userId", userId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("reportType", reportType);

        // để render dropdown
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("reportTypes", ReportType.values());

        return "admin_test/report-list";
    }

    // Show create report form (manual)
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("report", new BookReport());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("reportTypes", ReportType.values());
        return "admin_test/report-form";
    }

    // Save/Update report
    @PostMapping("/save")
    public String saveReport(@ModelAttribute BookReport report) {
        report.setReportAt(Instant.now());
        reportRepository.save(report);
        return "redirect:/admin/reports";
    }

    // Update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<BookReport> report = reportRepository.findById(id);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
            model.addAttribute("books", bookRepository.findAll());
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("reportTypes", ReportType.values());
            return "admin_test/report-form";
        } else {
            return "redirect:/admin/reports";
        }
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
        return "redirect:/admin/reports";
    }
}
