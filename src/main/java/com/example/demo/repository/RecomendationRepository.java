package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recomendation;
import com.example.demo.entity.User;

@Repository
public interface RecomendationRepository extends JpaRepository<Recomendation, Long> {

}
