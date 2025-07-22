package com.example.demo.service;

import com.example.demo.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews(Long bookId);

    Review getReviewById(Long id);

    Review addReview(Long userId, Long bookId, float rating, String comment);
    void deleteReview(Long reviewId);
    Review updateReview(Long reviewId, Integer rating, String comment);
}
