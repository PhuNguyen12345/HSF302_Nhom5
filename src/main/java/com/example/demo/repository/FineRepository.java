package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Fine;
import com.example.demo.entity.User;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

}
