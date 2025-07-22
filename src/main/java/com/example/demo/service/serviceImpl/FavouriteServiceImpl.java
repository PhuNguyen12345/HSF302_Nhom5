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
        return favouriteRepository.getUserFavourites(userId);
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
    public void addFavourite(Long userId, Long bookId) {
        FavoriteId favId = new FavoriteId();
        favId.setUserId(userId);
        favId.setBookId(bookId);

        Favorite favorite = new Favorite();
        favorite.setId(favId);
        favorite.setCreatedAt(LocalDateTime.now());
        favorite.setUser(userRepository.findById(userId).orElseThrow());
        favorite.setBook(bookRepository.findById(bookId).orElseThrow());

        favouriteRepository.save(favorite);
    }


    @Override
    public void deleteFavourite(Long userId, Long bookId) {
        FavoriteId id = new FavoriteId();
        id.setUserId(userId);
        id.setBookId(bookId);

        favouriteRepository.deleteById(id);
    }

    @Override
    public Favorite updateFavourite(FavoriteId favouriteId) {
        Favorite favorite = favouriteRepository.findById(favouriteId).orElseThrow();
        Book book = bookRepository.findById(favouriteId.getBookId()).orElseThrow();
        favorite.setBook(book);
        return favouriteRepository.save(favorite);
    }
}
