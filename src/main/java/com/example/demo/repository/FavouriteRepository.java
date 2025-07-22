package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favorite, FavoriteId> {

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    List<Book> findBooksByUserId(Long userId);
}
