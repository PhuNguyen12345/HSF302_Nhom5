package com.example.demo.controller;

import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/book")
    public String getBookReviews(@RequestParam Long bookId, Model model) {
        model.addAttribute("reviews", reviewService.getReviews(bookId));
        return "book_reviews";
    }

    @PostMapping("/add")
    public String addReview(@RequestParam Long userId,
                            @RequestParam Long bookId,
                            @RequestParam float rating,
                            @RequestParam String comment) {
        userId = 1l; //hardcode (no login yet)
        reviewService.addReview(userId, bookId, rating, comment);
        return "redirect:/details?id=" + bookId;
    }

}
