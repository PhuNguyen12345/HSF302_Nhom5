package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrowing;
import com.example.demo.enums.BorrowingStatus;
import com.example.demo.enums.MembershipRole;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

@Service
public class StatisticsService {
    
    @Autowired
    private BorrowingRepository borrowingRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Map<String, Object>> getMostBorrowedBooks(int limit) {
        List<Object[]> results = borrowingRepository.findMostBorrowedBooks();
        return results.stream()
                .limit(limit)
                .map(result -> {
                    Map<String, Object> bookStats = new HashMap<>();
                    Book book = (Book) result[0];
                    Long count = (Long) result[1];
                    bookStats.put("book", book);
                    bookStats.put("borrowCount", count);
                    return bookStats;
                })
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getMostBorrowedBooksByCategory(Long categoryId, int limit) {
        List<Object[]> results = borrowingRepository.findMostBorrowedBooksByCategory(categoryId);
        return results.stream()
                .limit(limit)
                .map(result -> {
                    Map<String, Object> bookStats = new HashMap<>();
                    Book book = (Book) result[0];
                    Long count = (Long) result[1];
                    bookStats.put("book", book);
                    bookStats.put("borrowCount", count);
                    return bookStats;
                })
                .collect(Collectors.toList());
    }
    
    public Map<String, Long> getUserStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("members", userRepository.countByMembershipRole(MembershipRole.MEMBER));
        stats.put("premiumMembers", userRepository.countByMembershipRole(MembershipRole.PREMIUM));
        stats.put("admins", userRepository.countByMembershipRole(MembershipRole.ADMIN));
        stats.put("librarians", userRepository.countByMembershipRole(MembershipRole.LIBRARIAN));
        stats.put("visitors", userRepository.countByMembershipRole(MembershipRole.VISITOR));
        return stats;
    }
    
    public Map<String, Long> getBookStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalBooks", bookRepository.count());
        stats.put("totalCategories", categoryRepository.count());
        return stats;
    }
    
    public Map<String, Long> getBorrowingStatistics() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalBorrowings", borrowingRepository.count());
        stats.put("activeBorrowings", borrowingRepository.countByStatus(BorrowingStatus.BORROWED));
        stats.put("returnedBorrowings", borrowingRepository.countByStatus(BorrowingStatus.RETURNED));
        stats.put("overdueBorrowings", (long) borrowingRepository.findOverdueBorrowings(BorrowingStatus.BORROWED).size());

        // Borrowings in the last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        stats.put("recentBorrowings", borrowingRepository.countBorrowingsSince(thirtyDaysAgo));

        return stats;
    }
    
    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> dashboard = new HashMap<>();
        List<Map<String, Object>> mostBorrowedBooks = getMostBorrowedBooks(10);
        List<Borrowing> overdueBorrowings = getOverdueBorrowings();

        dashboard.put("userStats", getUserStatistics());
        dashboard.put("bookStats", getBookStatistics());
        dashboard.put("borrowingStats", getBorrowingStatistics());
        dashboard.put("mostBorrowedBooks", mostBorrowedBooks);
        dashboard.put("overdueBorrowings", overdueBorrowings);
        dashboard.put("hasMostBorrowedBooks", mostBorrowedBooks != null && !mostBorrowedBooks.isEmpty());
        dashboard.put("hasOverdueBorrowings", overdueBorrowings != null && !overdueBorrowings.isEmpty());

        return dashboard;
    }
    
    public List<Borrowing> getOverdueBorrowings() {
        return borrowingRepository.findOverdueBorrowings(BorrowingStatus.BORROWED);
    }
}
