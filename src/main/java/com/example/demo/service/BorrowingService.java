package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class BorrowingService {
	@Autowired
    private BorrowingRepository borrowingRepository;

    public List<Borrowing> findAll() {
        return borrowingRepository.findAll();
    }

    public Optional<Borrowing> findById(Long id) {
        return borrowingRepository.findById(id);
    }

    public Borrowing save(Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public void deleteById(Long id) {
        borrowingRepository.deleteById(id);
    }
}
