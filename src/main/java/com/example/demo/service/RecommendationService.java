package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class RecommendationService {
	@Autowired
    private RecomendationRepository reccomendationRepository;

    public List<Recomendation> findAll() {
        return reccomendationRepository.findAll();
    }

    public Optional<Recomendation> findById(Long id) {
        return reccomendationRepository.findById(id);
    }

    public Recomendation save(Recomendation reccomendation) {
        return reccomendationRepository.save(reccomendation);
    }

    public void deleteById(Long id) {
        reccomendationRepository.deleteById(id);
    }
}
