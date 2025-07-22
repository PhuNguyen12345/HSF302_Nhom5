package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import com.example.demo.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Borrowing;
import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.enums.BorrowingStatus;
import com.example.demo.enums.MembershipRole;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;

@Service
public class DataInitializationService implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private LibraryCardService libraryCardService;

    @Autowired
    private BorrowingRepository borrowingRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }
    
    private void initializeData() {
        // Create categories if they don't exist
        if (categoryRepository.count() == 0) {
            createCategories();
        }
        
        // Create sample users if they don't exist
        if (userRepository.count() == 0) {
            createSampleUsers();
        }
        
        // Create sample books if they don't exist
        if (bookRepository.count() == 0) {
            createSampleBooks();
        }

        // Create sample borrowings if they don't exist
        if (borrowingRepository.count() == 0) {
            createSampleBorrowings();
        }
    }
    
    private void createCategories() {
        String[] categoryNames = {
            "Fiction", "Non-Fiction", "Science", "Technology", "History", 
            "Biography", "Mystery", "Romance", "Fantasy", "Self-Help"
        };
        
        for (String name : categoryNames) {
            Category category = new Category();
            category.setName(name);
            category.setDescription("Books in the " + name + " category");
            categoryRepository.save(category);
        }
    }
    
    private void createSampleUsers() {
        // Create Admin user
        User admin = new User();
        admin.setName("System Administrator");
        admin.setEmail("admin@elibrary.com");
        admin.setPasswordHash(passwordEncoder.encode("password"));
        admin.setPhone("+1-555-0001");
        admin.setDob(LocalDate.of(1980, 1, 1));
        admin.setAddress("123 Admin Street, Library City, LC 12345");
        admin.setMembershipRole(MembershipRole.ADMIN);
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUpdatedAt(LocalDateTime.now());
        User savedAdmin = userRepository.save(admin);
        libraryCardService.generateLibraryCard(savedAdmin);
        
        // Create Librarian user
        User librarian = new User();
        librarian.setName("Jane Librarian");
        librarian.setEmail("librarian@elibrary.com");
        librarian.setPasswordHash(passwordEncoder.encode("password"));
        librarian.setPhone("+1-555-0002");
        librarian.setDob(LocalDate.of(1985, 5, 15));
        librarian.setAddress("456 Library Avenue, Book Town, BT 67890");
        librarian.setMembershipRole(MembershipRole.LIBRARIAN);
        librarian.setCreatedAt(LocalDateTime.now());
        librarian.setUpdatedAt(LocalDateTime.now());
        User savedLibrarian = userRepository.save(librarian);
        libraryCardService.generateLibraryCard(savedLibrarian);
        
        // Create Premium Member
        User premium = new User();
        premium.setName("John Premium");
        premium.setEmail("premium@elibrary.com");
        premium.setPasswordHash(passwordEncoder.encode("password"));
        premium.setPhone("+1-555-0003");
        premium.setDob(LocalDate.of(1990, 8, 20));
        premium.setAddress("789 Premium Lane, Elite City, EC 11111");
        premium.setMembershipRole(MembershipRole.PREMIUM);
        premium.setCreatedAt(LocalDateTime.now());
        premium.setUpdatedAt(LocalDateTime.now());
        User savedPremium = userRepository.save(premium);
        libraryCardService.generateLibraryCard(savedPremium);
        
        // Create Regular Member
        User member = new User();
        member.setName("Alice Member");
        member.setEmail("member@elibrary.com");
        member.setPasswordHash(passwordEncoder.encode("password"));
        member.setPhone("+1-555-0004");
        member.setDob(LocalDate.of(1995, 12, 10));
        member.setAddress("321 Member Road, Regular City, RC 22222");
        member.setMembershipRole(MembershipRole.MEMBER);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        User savedMember = userRepository.save(member);
        libraryCardService.generateLibraryCard(savedMember);
        
        // Create Visitor
        User visitor = new User();
        visitor.setName("Bob Visitor");
        visitor.setEmail("visitor@elibrary.com");
        visitor.setPasswordHash(passwordEncoder.encode("password"));
        visitor.setPhone("+1-555-0005");
        visitor.setDob(LocalDate.of(1988, 3, 25));
        visitor.setAddress("654 Visitor Street, Guest City, GC 33333");
        visitor.setMembershipRole(MembershipRole.VISITOR);
        visitor.setCreatedAt(LocalDateTime.now());
        visitor.setUpdatedAt(LocalDateTime.now());
        User savedVisitor = userRepository.save(visitor);
        libraryCardService.generateLibraryCard(savedVisitor);

        // Create a test user without a library card to demonstrate the add functionality
        User testUser = new User();
        testUser.setName("Test User Without Card");
        testUser.setEmail("testuser@elibrary.com");
        testUser.setPasswordHash(passwordEncoder.encode("password"));
        testUser.setPhone("+1-555-0006");
        testUser.setDob(LocalDate.of(1992, 7, 15));
        testUser.setAddress("999 Test Street, Demo City, DC 44444");
        testUser.setMembershipRole(MembershipRole.MEMBER);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(testUser);
        // Note: No library card generated for this user intentionally
    }
    
    private void createSampleBooks() {
        Category fiction = categoryRepository.findAll().stream()
            .filter(c -> c.getName().equals("Fiction"))
            .findFirst().orElse(null);
        
        Category science = categoryRepository.findAll().stream()
            .filter(c -> c.getName().equals("Science"))
            .findFirst().orElse(null);
        
        Category technology = categoryRepository.findAll().stream()
            .filter(c -> c.getName().equals("Technology"))
            .findFirst().orElse(null);
        
        // Sample books
        String[][] bookData = {
            {"The Great Gatsby", "F. Scott Fitzgerald", "Scribner", "1925", "9780743273565", "English", "180", "A classic American novel"},
            {"To Kill a Mockingbird", "Harper Lee", "J.B. Lippincott & Co.", "1960", "9780061120084", "English", "281", "A gripping tale of racial injustice"},
            {"1984", "George Orwell", "Secker & Warburg", "1949", "9780451524935", "English", "328", "A dystopian social science fiction novel"},
            {"Pride and Prejudice", "Jane Austen", "T. Egerton", "1813", "9780141439518", "English", "432", "A romantic novel of manners"},
            {"The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company", "1951", "9780316769174", "English", "277", "A controversial coming-of-age story"}
        };
        
        for (String[] data : bookData) {
            Book book = new Book();
            book.setTitle(data[0]);
            book.setAuthor(data[1]);
            book.setPublisher(data[2]);
            book.setYearPublished(Integer.parseInt(data[3]));
            book.setIsbn(data[4]);
            book.setLanguage(data[5]);
            book.setPages(Integer.parseInt(data[6]));
            book.setDescription(data[7]);
            book.setCategory(fiction);
            book.setCreatedAt(LocalDateTime.now());
            bookRepository.save(book);
        }
        
        // Add some science and technology books
        Book scienceBook = new Book();
        scienceBook.setTitle("A Brief History of Time");
        scienceBook.setAuthor("Stephen Hawking");
        scienceBook.setPublisher("Bantam Doubleday Dell");
        scienceBook.setYearPublished(1988);
        scienceBook.setIsbn("9780553380163");
        scienceBook.setLanguage("English");
        scienceBook.setPages(256);
        scienceBook.setDescription("A landmark volume in science writing");
        scienceBook.setCategory(science);
        scienceBook.setCreatedAt(LocalDateTime.now());
        bookRepository.save(scienceBook);
        
        Book techBook = new Book();
        techBook.setTitle("Clean Code");
        techBook.setAuthor("Robert C. Martin");
        techBook.setPublisher("Prentice Hall");
        techBook.setYearPublished(2008);
        techBook.setIsbn("9780132350884");
        techBook.setLanguage("English");
        techBook.setPages(464);
        techBook.setDescription("A handbook of agile software craftsmanship");
        techBook.setCategory(technology);
        techBook.setCreatedAt(LocalDateTime.now());
        bookRepository.save(techBook);
    }

    private void createSampleBorrowings() {
        // Get all users and books for creating borrowings
        List<User> users = userRepository.findAll();
        List<Book> books = bookRepository.findAll();

        if (users.isEmpty() || books.isEmpty()) {
            return; // Can't create borrowings without users and books
        }

        // Create various borrowing scenarios for statistics
        Random random = new Random();

        // Create some returned borrowings (for statistics)
        for (int i = 0; i < 15; i++) {
            User user = users.get(random.nextInt(users.size()));
            Book book = books.get(random.nextInt(books.size()));

            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setBook(book);
            borrowing.setBorrowedAt(LocalDateTime.now().minusDays(random.nextInt(60) + 15)); // 15-75 days ago
            borrowing.setDueDate(borrowing.getBorrowedAt().toLocalDate().plusDays(14));
            borrowing.setReturnedAt(borrowing.getBorrowedAt().plusDays(random.nextInt(14) + 1)); // Returned within due date
            borrowing.setStatus(BorrowingStatus.RETURNED);

            borrowingRepository.save(borrowing);
        }

        // Create some active borrowings
        for (int i = 0; i < 8; i++) {
            User user = users.get(random.nextInt(users.size()));
            Book book = books.get(random.nextInt(books.size()));

            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setBook(book);
            borrowing.setBorrowedAt(LocalDateTime.now().minusDays(random.nextInt(10) + 1)); // 1-10 days ago
            borrowing.setDueDate(borrowing.getBorrowedAt().toLocalDate().plusDays(14));
            borrowing.setStatus(BorrowingStatus.BORROWED);

            borrowingRepository.save(borrowing);
        }

        // Create some overdue borrowings
        for (int i = 0; i < 3; i++) {
            User user = users.get(random.nextInt(users.size()));
            Book book = books.get(random.nextInt(books.size()));

            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setBook(book);
            borrowing.setBorrowedAt(LocalDateTime.now().minusDays(random.nextInt(10) + 20)); // 20-30 days ago
            borrowing.setDueDate(borrowing.getBorrowedAt().toLocalDate().plusDays(14)); // Due date in the past
            borrowing.setStatus(BorrowingStatus.BORROWED); // Still borrowed but overdue

            borrowingRepository.save(borrowing);
        }

        // Create some popular book borrowings (same books borrowed multiple times)
        Book popularBook1 = books.get(0); // The Great Gatsby
        Book popularBook2 = books.get(1); // To Kill a Mockingbird

        // Make these books more popular by adding more borrowing records
        for (int i = 0; i < 5; i++) {
            User user = users.get(random.nextInt(users.size()));

            // Popular book 1 borrowings
            Borrowing borrowing1 = new Borrowing();
            borrowing1.setUser(user);
            borrowing1.setBook(popularBook1);
            borrowing1.setBorrowedAt(LocalDateTime.now().minusDays(random.nextInt(90) + 30));
            borrowing1.setDueDate(borrowing1.getBorrowedAt().toLocalDate().plusDays(14));
            borrowing1.setReturnedAt(borrowing1.getBorrowedAt().plusDays(random.nextInt(14) + 1));
            borrowing1.setStatus(BorrowingStatus.RETURNED);
            borrowingRepository.save(borrowing1);

            // Popular book 2 borrowings
            if (i < 3) { // Make book 1 more popular than book 2
                Borrowing borrowing2 = new Borrowing();
                borrowing2.setUser(users.get(random.nextInt(users.size())));
                borrowing2.setBook(popularBook2);
                borrowing2.setBorrowedAt(LocalDateTime.now().minusDays(random.nextInt(90) + 30));
                borrowing2.setDueDate(borrowing2.getBorrowedAt().toLocalDate().plusDays(14));
                borrowing2.setReturnedAt(borrowing2.getBorrowedAt().plusDays(random.nextInt(14) + 1));
                borrowing2.setStatus(BorrowingStatus.RETURNED);
                borrowingRepository.save(borrowing2);
            }
        }
    }
}
