package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookDetailController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping
    public String listBooks(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String keyword,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "6") int size,
                            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage;

        if (keyword != null && !keyword.isBlank()) {
            bookPage = bookService.searchBooks(keyword, pageable);
        } else if (category != null && !category.isBlank()) {
            bookPage = bookService.getBooksByCategory(category, pageable);
        } else {
            bookPage = bookService.getAllBooks(pageable);
        }

        model.addAttribute("bookPage", bookPage);
        model.addAttribute("books", bookPage.getContent()); // for convenience
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("currentCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", categoryRepository.findAll());

        return "books";
    }

    @GetMapping("/details/{id}")
    public String getBookDetail(@PathVariable Long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("reviews", reviewService.getReviews(id));
        model.addAttribute("user", loggedInUser);
        return "books-details"; // another page for detail view
    }
    @PostMapping("/details/{id}/review")
    public String submitReview(@PathVariable Long id,
                               @RequestParam float rating,
                               @RequestParam String comment,
                               HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        reviewService.addReview(user.getId(), id, rating, comment);
        return "redirect:/books/details/" + id;
    }

}
