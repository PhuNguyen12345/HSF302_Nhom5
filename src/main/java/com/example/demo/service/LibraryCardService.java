package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
@Service
public class LibraryCardService {
	@Autowired
    private LibraryCardRepository libraryCardRepository;

    public List<LibraryCard> findAll() {
        return libraryCardRepository.findAll();
    }

    public Optional<LibraryCard> findById(Long id) {
        return libraryCardRepository.findById(id);
    }

    public LibraryCard save(LibraryCard libraryCard) {
        return libraryCardRepository.save(libraryCard);
    }

    public void deleteById(Long id) {
        libraryCardRepository.deleteById(id);
    }
}
