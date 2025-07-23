package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll(); // Lấy tất cả các thể loại

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
