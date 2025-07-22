package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.FavouriteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @GetMapping("/users")
    public String getUserFavorites(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("favorites", favouriteService.getUserFavourites(loggedInUser.getId()));
        return "admin/bookmark";
    }

    @PostMapping("/add")
    public String addFavorite(@RequestParam Long userId,
                              @RequestParam Long bookId) {
        favouriteService.addFavourite(userId, bookId);
        return "redirect:/favorites/users";
    }


    @PostMapping("/remove")
    public String removeFavorite(
            @RequestParam Long bookId,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";


        favouriteService.deleteFavourite(user.getId(), bookId);
        return "redirect:/favorites/users";
    }
}

