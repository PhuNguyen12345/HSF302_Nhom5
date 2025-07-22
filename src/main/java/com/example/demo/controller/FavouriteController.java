package com.example.demo.controller;

import com.example.demo.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @GetMapping("/user")
    public String getUserFavorites(@RequestParam Long userId, Model model) {
//        model.addAttribute("favourites", favouriteService.getUserFavourites(userId));
        model.addAttribute("favourites", favouriteService.getUserFavourites(1l)); //hardcode id
        return "user_favorites";
    }

    @PostMapping("/add")
    public String addFavorite(@RequestParam Long userId,
                              @RequestParam Long bookId) {
//        favouriteService.addFavourite(userId, bookId);
        favouriteService.addFavourite(1l, bookId);
        return "redirect:/add?id=" + bookId;
    }


    @PostMapping("/remove")
    public String removeFavorite(@RequestParam Long userId,
                                 @RequestParam Long bookId) {
//        favouriteService.deleteFavourite(userId, bookId);
        favouriteService.deleteFavourite(1l, bookId);
        return "redirect:/user?userId=" + userId;
    }
}

