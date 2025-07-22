package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    // ✅ Hiển thị danh sách sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "admin_test/book-list";
    }

    // ✅ Hiển thị form thêm sách mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin_test/book-form";
    }

    // ✅ Hiển thị form cập nhật sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("book", optionalBook.get());
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin_test/book-form";
        } else {
            return "redirect:/admin/books";
        }
    }

    // ✅ Xử lý thêm sách
    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        book.setCreatedAt(LocalDateTime.from(Instant.now()));
        bookRepository.save(book);
        return "redirect:/admin/books";
    }

    // ✅ Xử lý cập nhật sách
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookRepository.save(book); // Nếu book có id thì sẽ là update
        return "redirect:/admin/books";
    }

    // ✅ Xoá sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/admin/books";
    }

}
