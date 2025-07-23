package com.example.demo.controller;

import com.example.demo.service.BookService;
import com.example.demo.service.RecommendationService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.http.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Book;
import org.springframework.http.ResponseEntity;
import com.example.demo.entity.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookService bookService;

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
    @GetMapping("/search")
    public String searchBooks(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String author,
                              @RequestParam(required = false) String category,
                              Model model) {
        // Lấy danh sách tất cả các thể loại từ DB
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        Pageable pageable = PageRequest.of(0, 10);  // Số sách mỗi trang là 10
        List<Book> books = bookService.searchBooks(title, author, category, pageable);
        model.addAttribute("books", books);
        return "library/books-media-gird-view-v2";  // Trả về trang hiển thị danh sách sách
    }
    @GetMapping("/read/{id}")
    public String readBook(@PathVariable Long id, Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            model.addAttribute("book", book);
            return "read-book";  // Trả về trang đọc sách, bạn có thể hiển thị PDF tại đây
        } else {
            return "error";  // Nếu không tìm thấy sách, trả về trang lỗi
        }
    }
    @GetMapping("/books/{bookId}/file")
    @ResponseBody
    public ResponseEntity<Resource> downloadBook(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);
        Path filePath = Paths.get(book.get().getFileUrl());  // Đảm bảo bạn có đường dẫn đúng đến file
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)  // hoặc ePub tùy vào loại file
                .body(resource);
    }
    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.findById(id); // Make sure this returns a valid book or null check
        if (book.isPresent()) {
            model.addAttribute("book", book.get()); // Set the book object for Thymeleaf
        } else {
            model.addAttribute("errorMessage", "Book not found");
        }
        // Gợi ý sách cùng thể loại
        List<Book> recommendedBooks = bookRecommendationService.recommendBooks(book.get().getCategory().getName(), book.get().getRating());
        model.addAttribute("recommendedBooks", recommendedBooks);

        return "library/books-media-detail-v2"; // Ensure this matches your Thymeleaf template name
    }
    @Autowired
    private RecommendationService bookRecommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<List<Book>> getBookRecommendations(@RequestParam String genre, @RequestParam Double minRating) {
        List<Book> books = bookRecommendationService.recommendBooks(genre, minRating);
        return ResponseEntity.ok(books);
    }
}
