package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.LibraryCard;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.LibraryCardService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/library-card")
public class LibraryCardController {

    @Autowired
    private LibraryCardService libraryCardService;

    @Autowired
    private UserService userService;

    @GetMapping("/view")
    public String viewLibraryCard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<LibraryCard> cardOpt = libraryCardService.findByUser(loggedInUser);
        if (cardOpt.isPresent()) {
            LibraryCard card = cardOpt.get();
            model.addAttribute("libraryCard", card);
            model.addAttribute("isExpired", libraryCardService.isCardExpired(card));
            return "library-card/view";
        } else {
            model.addAttribute("errorMessage", "No library card found. Please contact the administrator.");
            return "library-card/not-found";
        }
    }

    @PostMapping("/renew")
    public String renewLibraryCard(HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<LibraryCard> cardOpt = libraryCardService.findByUser(loggedInUser);
        if (cardOpt.isPresent()) {
            LibraryCard card = cardOpt.get();
            libraryCardService.renewCard(card);
            redirectAttributes.addFlashAttribute("successMessage",
                "Library card renewed successfully! New expiration date: " + card.getExpiredAt());
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                "No library card found to renew.");
        }

        return "redirect:/library-card/view";
    }

    // Admin-only methods for library card management
    @GetMapping("/admin/manage")
    public String manageLibraryCards(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        List<LibraryCard> allCards = libraryCardService.findAllCards();
        List<LibraryCard> expiredCards = libraryCardService.findExpiredCards();
        List<LibraryCard> expiringCards = libraryCardService.findCardsExpiringWithinDays(30);

        model.addAttribute("allCards", allCards);
        model.addAttribute("expiredCards", expiredCards);
        model.addAttribute("expiringCards", expiringCards);
        model.addAttribute("totalCards", allCards.size());
        model.addAttribute("expiredCount", expiredCards.size());
        model.addAttribute("expiringCount", expiringCards.size());

        return "library-card/admin-manage";
    }

    @PostMapping("/admin/renew/{id}")
    public String adminRenewCard(@PathVariable Long id, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        try {
            Optional<LibraryCard> cardOpt = libraryCardService.findById(id);
            if (cardOpt.isPresent()) {
                LibraryCard card = cardOpt.get();
                libraryCardService.renewCard(card);
                redirectAttributes.addFlashAttribute("successMessage",
                    "Library card for " + card.getUser().getName() + " renewed successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "Library card not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error renewing library card: " + e.getMessage());
        }

        return "redirect:/library-card/admin/manage";
    }

    @PostMapping("/admin/delete/{id}")
    public String adminDeleteCard(@PathVariable Long id, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            return "redirect:/dashboard";
        }

        try {
            Optional<LibraryCard> cardOpt = libraryCardService.findById(id);
            if (cardOpt.isPresent()) {
                LibraryCard card = cardOpt.get();
                String userName = card.getUser().getName();
                libraryCardService.deleteCard(id);
                redirectAttributes.addFlashAttribute("successMessage",
                    "Library card for " + userName + " deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "Library card not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error deleting library card: " + e.getMessage());
        }

        return "redirect:/library-card/admin/manage";
    }

    @GetMapping("/admin/generate/{userId}")
    public String generateCardForUser(@PathVariable Long userId, HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        try {
            Optional<User> userOpt = userService.findById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // Check if user already has a library card
                if (libraryCardService.userHasLibraryCard(user)) {
                    redirectAttributes.addFlashAttribute("errorMessage",
                        "User " + user.getName() + " already has a library card.");
                } else {
                    LibraryCard newCard = libraryCardService.generateLibraryCardForUser(user);
                    redirectAttributes.addFlashAttribute("successMessage",
                        "Library card generated successfully for " + user.getName() +
                        ". Card Number: " + newCard.getCardNumber());
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "User not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error generating library card: " + e.getMessage());
        }

        return "redirect:/library-card/admin/manage";
    }

    @GetMapping("/admin/add")
    public String showAddLibraryCardPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        // Get all users who don't have library cards
        List<User> allUsers = userService.findAll();
        List<User> usersWithoutCards = allUsers.stream()
                .filter(user -> !libraryCardService.userHasLibraryCard(user))
                .collect(Collectors.toList());

        model.addAttribute("usersWithoutCards", usersWithoutCards);
        model.addAttribute("totalUsersWithoutCards", usersWithoutCards.size());

        return "library-card/admin-add";
    }

    @GetMapping("/admin/create/{userId}")
    public String showCreateCardForm(@PathVariable Long userId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
                (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
                        loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/library-card/admin/manage";
        }

        User user = userOpt.get();
        LibraryCard card = new LibraryCard();
        card.setUser(user);
        card.setIssuedAt(LocalDate.now());
        card.setExpiredAt(LocalDate.now().plusYears(5));
        String generatedCardNumber = "LC" + LocalDate.now().getYear() + String.format("%05d", user.getId());
        card.setCardNumber(generatedCardNumber);

        model.addAttribute("card", card);
        return "library-card/admin-create-form";
    }

    @PostMapping("/admin/create")
    public String createLibraryCard(@ModelAttribute("card") LibraryCard card,
                                    RedirectAttributes redirectAttributes) {
        try {
            libraryCardService.save(card);  // lưu thẻ mới
            redirectAttributes.addFlashAttribute("successMessage", "Thẻ thư viện đã được tạo!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tạo thẻ thất bại: " + e.getMessage());
        }
        return "redirect:/library-card/admin/manage";
    }

}
