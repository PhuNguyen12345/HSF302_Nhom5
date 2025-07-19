package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Book;
import com.example.demo.entity.Favourite;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.FavouriteRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<Favourite> getAllFavorites() {
        return favouriteRepository.findAll();
    }
    @Override
    public Favourite getFavoriteById(Long id) {
        return favouriteRepository.findById(id).orElseThrow();
    }

    @Override
    public Favourite addFavourite(Long userId, Long bookId) {
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Favourite favorite = new Favourite();
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
    public Favourite updateFavourite(Long favouriteId, Long bookId) {
        Favourite favorite = favouriteRepository.findById(favouriteId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();
        favorite.setBook(book);
        return favouriteRepository.save(favorite);
    }
}
