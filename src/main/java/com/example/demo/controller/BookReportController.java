package com.example.demo.controller;

import com.example.demo.entity.BookReport;
import com.example.demo.enums.ReportType;
import com.example.demo.repository.BookReportRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    // ✅ Danh sách tất cả báo cáo
    @GetMapping
    public String listReports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        return "admin_test/report-list";
    }

    // ✅ Form tạo báo cáo mới (tạm thời cho user admin tạo thủ công)
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("report", new BookReport());
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("reportTypes", ReportType.values());
        return "admin_test/report-form";
    }

    // ✅ Xử lý tạo báo cáo
    @PostMapping("/save")
    public String saveReport(@ModelAttribute BookReport report) {
        report.setReportAt(Instant.now());
        reportRepository.save(report);
        return "redirect:/admin/reports";
    }

    // ✅ Form cập nhật báo cáo
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

    // ✅ Xoá báo cáo
    @GetMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportRepository.deleteById(id);
        return "redirect:/admin/reports";
    }
}
