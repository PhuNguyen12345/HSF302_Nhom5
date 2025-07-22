package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.LibraryCard;
import com.example.demo.entity.User;

@Repository
public interface LibraryCardRepository extends JpaRepository<LibraryCard, Long> {

    Optional<LibraryCard> findByUser(User user);

    Optional<LibraryCard> findByCardNumber(String cardNumber);

    Optional<LibraryCard> findByBarcode(String barcode);

    boolean existsByCardNumber(String cardNumber);

    boolean existsByBarcode(String barcode);

    boolean existsByUser(User user);
}
