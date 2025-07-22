package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Borrowing;
import com.example.demo.entity.LibraryCard;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.LibraryCardService;
import com.example.demo.service.StatisticsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    
    @Autowired
    private StatisticsService statisticsService;
    
    @Autowired
    private LibraryCardService libraryCardService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        // Check user login
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        // Get user's library card
        libraryCardService.findByUser(loggedInUser).ifPresent(card -> {
            model.addAttribute("libraryCard", card);
            model.addAttribute("isCardExpired", libraryCardService.isCardExpired(card));
        });
        
        // Get basic statistics for user dashboard
        Map<String, Object> stats = statisticsService.getDashboardStatistics();
        model.addAttribute("mostBorrowedBooks", stats.get("mostBorrowedBooks"));
        model.addAttribute("hasMostBorrowedBooks", stats.get("hasMostBorrowedBooks"));
        
        model.addAttribute("user", loggedInUser);
        return "dashboard/user-dashboard";
    }
    
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        //Check user role
        if (loggedInUser.getMembershipRole() != MembershipRole.ADMIN && 
            loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN) {
            return "redirect:/dashboard";
        }
        
        // Get comprehensive statistics for admin dashboard
        Map<String, Object> dashboardStats = statisticsService.getDashboardStatistics();
        model.addAttribute("userStats", dashboardStats.get("userStats"));
        model.addAttribute("bookStats", dashboardStats.get("bookStats"));
        model.addAttribute("borrowingStats", dashboardStats.get("borrowingStats"));
        model.addAttribute("mostBorrowedBooks", dashboardStats.get("mostBorrowedBooks"));
        model.addAttribute("overdueBorrowings", dashboardStats.get("overdueBorrowings"));
        model.addAttribute("hasMostBorrowedBooks", dashboardStats.get("hasMostBorrowedBooks"));
        model.addAttribute("hasOverdueBorrowings", dashboardStats.get("hasOverdueBorrowings"));
        
        model.addAttribute("user", loggedInUser);
        return "dashboard/admin-dashboard";
    }
    
    @GetMapping("/statistics")
    public String statistics(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }
        
        if (loggedInUser.getMembershipRole() != MembershipRole.ADMIN && 
            loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN) {
            return "redirect:/dashboard";
        }
        
        // Get detailed statistics
        Map<String, Object> dashboardStats = statisticsService.getDashboardStatistics();
        List<Map<String, Object>> mostBorrowedBooks = statisticsService.getMostBorrowedBooks(20);
        List<Borrowing> overdueBorrowings = statisticsService.getOverdueBorrowings();

        model.addAttribute("userStats", dashboardStats.get("userStats"));
        model.addAttribute("bookStats", dashboardStats.get("bookStats"));
        model.addAttribute("borrowingStats", dashboardStats.get("borrowingStats"));
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
        model.addAttribute("overdueBorrowings", overdueBorrowings);
        model.addAttribute("hasMostBorrowedBooks", mostBorrowedBooks != null && !mostBorrowedBooks.isEmpty());
        model.addAttribute("hasOverdueBorrowings", overdueBorrowings != null && !overdueBorrowings.isEmpty());
        
        model.addAttribute("user", loggedInUser);
        return "dashboard/statistics";
    }
}
