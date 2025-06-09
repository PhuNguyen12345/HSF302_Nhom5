package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class FineService {
	@Autowired
    private FineRepository fineRepository;

    public List<Fine> findAll() {
        return fineRepository.findAll();
    }

    public Optional<Fine> findById(Long id) {
        return fineRepository.findById(id);
    }

    public Fine save(Fine fine) {
        return fineRepository.save(fine);
    }

    public void deleteById(Long id) {
        fineRepository.deleteById(id);
    }
}
