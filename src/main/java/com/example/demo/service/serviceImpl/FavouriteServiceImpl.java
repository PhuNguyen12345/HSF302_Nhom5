package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.FavoriteId;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.FavouriteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getUserFavourites(Long userId) {
        return favouriteRepository.findBooksByUserId(userId);
    }
    @Override
    public List<Favorite> getAllFavorites() {
        return favouriteRepository.findAll();
    }
    @Override
    public Favorite getFavoriteById(FavoriteId favoriteId) {
        return favouriteRepository.findById(favoriteId).orElseThrow();
    }

    @Override
    public Favorite addFavourite(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);
        favorite.setCreatedAt(LocalDateTime.now());

        return favouriteRepository.save(favorite);
    }

    @Override
    public void deleteFavourite(Long userId, Long bookId) {
        favouriteRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    @Override
    public Favorite updateFavourite(FavoriteId favouriteId) {
        Favorite favorite = favouriteRepository.findById(favouriteId).orElseThrow();
        Book book = bookRepository.findById(favouriteId.getBookId()).orElseThrow();
        favorite.setBook(book);
        return favouriteRepository.save(favorite);
    }
}
