package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favorite, FavoriteId> {

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    List<Book> findBooksByUserId(Long userId);

    @Query("SELECT f.book FROM Favorite f WHERE f.user.id = :userId")
    List<Book> getUserFavourites(@Param("userId") Long userId); // ✅ now it matches

}
