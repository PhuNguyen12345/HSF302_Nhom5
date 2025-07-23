package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class RecommendationService {

    private RecomendationRepository reccomendationRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Recomendation> findAll() {
        return reccomendationRepository.findAll();
    }

    public Optional<Recomendation> findById(Long id) {
        return reccomendationRepository.findById(id);
    }

    public Recomendation save(Recomendation reccomendation) {
        return reccomendationRepository.save(reccomendation);
    }
    public List<Book> recommendBooksByCategory(String category) {
        return bookRepository.findByCategory_NameContainingIgnoreCase(category);
    }
    public void deleteById(Long id) {
        reccomendationRepository.deleteById(id);
    }
    public List<Book> recommendBooks(String genre, Double minRating) {
        // Gợi ý sách theo thể loại và đánh giá
        return bookRepository.findByCategory_NameContainingIgnoreCaseAndRatingGreaterThan(genre, minRating);
    }

    public List<Book> recommendBooksByAuthor(String author) {
        // Gợi ý sách theo tác giả
        return bookRepository.findByAuthor(author);
    }
}
