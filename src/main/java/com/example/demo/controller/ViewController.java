package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users/books")
public class ViewController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookService bookService;

    @GetMapping("/{bookId}/file")
    @ResponseBody
    public ResponseEntity<Resource> downloadBook(@PathVariable Long bookId) {
        Optional<Book> book = bookService.findById(bookId);
        Path filePath = Paths.get(book.get().getFileUrl());
        Resource resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
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
}
