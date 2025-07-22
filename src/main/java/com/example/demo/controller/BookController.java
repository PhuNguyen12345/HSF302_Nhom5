package com.example.demo.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    //  Display book's list
    @GetMapping
    public String listBooks(@RequestParam(required = false) String title,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) Long categoryId,
                            @RequestParam(defaultValue = "0") int page,
                            Model model) {

        Pageable pageable = PageRequest.of(page, 10); // 5 sách mỗi trang
        Page<Book> bookPage;

        if ((title == null || title.isBlank()) &&
                (author == null || author.isBlank()) &&
                categoryId == null) {
            bookPage = bookRepository.findAll(pageable);
        } else {
            bookPage = bookRepository.findByFiltersWithPaging(
                    title != null ? title : "",
                    author != null ? author : "",
                    categoryId,
                    pageable
            );
        }

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("books", bookPage.getContent()); // để hiển thị
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("categories", categoryRepository.findAll());

        return "admin_test/book-list";
    }


    //  Display book's form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin_test/book-form";
    }

    //  Display book's form
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

    // Save book
    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult result,
                           Model model) {

        // Kiểm tra trùng tiêu đề nếu đang tạo mới hoặc sửa thành tên trùng
        if (book.getId() == null) {
            if (bookRepository.existsByTitle(book.getTitle())) {
                result.rejectValue("title", "book.title.duplicate", "Tiêu đề sách đã tồn tại");
            }
            if (bookRepository.existsByIsbn(book.getIsbn())) {
                result.rejectValue("isbn", "book.isbn.duplicate", "ISBN đã tồn tại");
            }
        } else {
            if (bookRepository.existsByTitleAndIdNot(book.getTitle(), book.getId())) {
                result.rejectValue("title", "book.title.duplicate", "Tiêu đề sách đã tồn tại");
            }
            if (bookRepository.existsByIsbnAndIdNot(book.getIsbn(), book.getId())) {
                result.rejectValue("isbn", "book.isbn.duplicate", "ISBN đã tồn tại");
            }
        }

        // Nếu có lỗi thì trả lại form
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin_test/book-form";
        }

        // Lưu nếu không có lỗi
        bookRepository.save(book);
        return "redirect:/admin/books";
    }

    // Update book
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookRepository.save(book); // Nếu book có id thì sẽ là update
        return "redirect:/admin/books";
    }

    //  Delete book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:/admin/books";
    }
}
