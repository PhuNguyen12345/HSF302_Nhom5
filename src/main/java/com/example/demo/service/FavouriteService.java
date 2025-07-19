package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favourite;

import java.util.List;

public interface FavouriteService {
    List<Favourite> getAllFavorites();

    Favourite getFavoriteById(Long id);

    Favourite addFavourite(Long userId, Long bookId);

    List<Book> getUserFavourites(Long userId);

    void deleteFavourite(Long userId, Long bookId);

    Favourite updateFavourite(Long userId, Long bookId);
}
