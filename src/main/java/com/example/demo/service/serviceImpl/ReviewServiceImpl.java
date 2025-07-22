package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    @Override
    public List<Review> getReviews(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }
    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow();
    }

    @Override
    public Review addReview(Long userId, Long bookId, float rating, String comment) {
        User user = userRepository.findUserById(userId);
        Book book = bookRepository.findBookById(bookId);
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }
        if(book == null){
            throw new IllegalArgumentException("Book not found");
        }

        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
    @Override
    public Review updateReview(Long reviewId, Integer rating, String comment) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setRating(rating);
        review.setComment(comment);
        return reviewRepository.save(review);
    }

}
