package com.example.demo.repository;

import com.example.demo.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomendationRepository extends JpaRepository<Recommendation, Long> {

}
