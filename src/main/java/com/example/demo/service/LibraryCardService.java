package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.LibraryCard;
import com.example.demo.entity.User;
import com.example.demo.repository.LibraryCardRepository;

@Service
@Transactional
public class LibraryCardService {

    @Autowired
    private LibraryCardRepository libraryCardRepository;

    public List<LibraryCard> findAll() {
        return libraryCardRepository.findAll();
    }

    public Optional<LibraryCard> findById(Long id) {
        return libraryCardRepository.findById(id);
    }

    public Optional<LibraryCard> findByUser(User user) {
        return libraryCardRepository.findByUser(user);
    }

    public Optional<LibraryCard> findByCardNumber(String cardNumber) {
        return libraryCardRepository.findByCardNumber(cardNumber);
    }

    public LibraryCard save(LibraryCard libraryCard) {
        return libraryCardRepository.save(libraryCard);
    }

    public void deleteById(Long id) {
        libraryCardRepository.deleteById(id);
    }

    public LibraryCard generateLibraryCard(User user) {
        if (libraryCardRepository.existsByUser(user)) {
            throw new RuntimeException("User already has a library card");
        }

        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setUser(user);
        libraryCard.setCardNumber(generateUniqueCardNumber());
        libraryCard.setBarcode(generateUniqueBarcode());
        libraryCard.setIssuedAt(LocalDate.now());
        libraryCard.setExpiredAt(LocalDate.now().plusYears(5));

        return libraryCardRepository.save(libraryCard);
    }

    private String generateUniqueCardNumber() {
        String cardNumber;
        do {
            cardNumber = "LC" + String.format("%08d", new Random().nextInt(100000000));
        } while (libraryCardRepository.existsByCardNumber(cardNumber));
        return cardNumber;
    }

    private String generateUniqueBarcode() {
        String barcode;
        do {
            barcode = String.format("%013d", new Random().nextLong() % 10000000000000L);
            if (barcode.startsWith("-")) {
                barcode = barcode.substring(1);
            }
        } while (libraryCardRepository.existsByBarcode(barcode));
        return barcode;
    }

    public boolean isCardExpired(LibraryCard card) {
        return card.getExpiredAt().isBefore(LocalDate.now());
    }

    public LibraryCard renewCard(LibraryCard card) {
        if (!isCardExpired(card)) {
            throw new RuntimeException("Card is still valid. No need to renew.");
        }
        card.setExpiredAt(LocalDate.now().plusYears(5));
        return libraryCardRepository.save(card);
    }

    public List<LibraryCard> findAllCards() {
        return libraryCardRepository.findAll();
    }

    public void deleteCard(Long cardId) {
        libraryCardRepository.deleteById(cardId);
    }

    public LibraryCard updateCard(LibraryCard card) {
        return libraryCardRepository.save(card);
    }

    public List<LibraryCard> findExpiredCards() {
        return libraryCardRepository.findAll().stream()
                .filter(this::isCardExpired)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<LibraryCard> findCardsExpiringWithinDays(int days) {
        LocalDate cutoffDate = LocalDate.now().plusDays(days);
        return libraryCardRepository.findAll().stream()
                .filter(card -> card.getExpiredAt().isBefore(cutoffDate) && card.getExpiredAt().isAfter(LocalDate.now()))
                .collect(java.util.stream.Collectors.toList());
    }

    public LibraryCard generateLibraryCardForUser(User user) {
        // Check if user already has a library card
        Optional<LibraryCard> existingCard = findByUser(user);
        if (existingCard.isPresent()) {
            throw new RuntimeException("User already has a library card");
        }

        return generateLibraryCard(user);
    }

    public boolean userHasLibraryCard(User user) {
        return findByUser(user).isPresent();
    }
}
