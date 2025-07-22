package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.FavoriteId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteService {
    List<Favorite> getAllFavorites();

    Favorite getFavoriteById(FavoriteId id);

    void addFavourite(Long userId, Long bookId);

    List<Book> getUserFavourites(Long userId);

    void deleteFavourite(Long userId, Long bookId);

    Favorite updateFavourite(FavoriteId favoriteId);
}
