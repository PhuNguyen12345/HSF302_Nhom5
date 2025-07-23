# E-Library Management System

A comprehensive digital library management system built with Spring Boot MVC, Thymeleaf templates, and MySQL database.

## Features

### 🔐 User Account Management
- **User Registration**: Complete registration with validation (name, email, password, phone, address, date of birth)
- **Session-based Authentication**: Secure login/logout functionality using HTTP sessions
- **Profile Management**: Users can view and edit their personal information
- **Role-based Access Control**: Support for MEMBER, PREMIUM, ADMIN, LIBRARIAN, and VISITOR roles
- **Admin User Management**: CRUD operations, search, filter, and pagination for user management

### 📚 Digital Library Card Management
- **Automatic Card Generation**: Library cards are automatically created upon user registration
- **Unique Card Details**: Each card has a unique card number, barcode, issue date, and expiration date
- **Card Display**: Beautiful digital library card with QR code and barcode
- **Card Renewal**: Users can renew expired cards
- **Card Validation**: System checks for card expiration and alerts users

### 📊 Book Statistics Dashboard
- **Popular Books**: Display most borrowed books with borrowing counts
- **Category Statistics**: Show popular books by category
- **Borrowing Trends**: Generate reports on borrowing patterns
- **User Statistics**: Track user registrations and membership types
- **Admin Analytics**: Comprehensive dashboard for administrators and librarians

## Technical Stack

- **Backend**: Spring Boot 3.5.0 with Java 21
- **Frontend**: Thymeleaf templates with Bootstrap 5.3.0
- **Database**: MySQL with JPA/Hibernate
- **Authentication**: Session-based (no JWT)
- **Validation**: Bean Validation with custom validators
- **Security**: BCrypt password hashing
- **Architecture**: MVC pattern with proper separation of concerns

## Project Structure

```
src/main/java/com/example/demo/
├── controller/          # MVC Controllers
├── service/            # Business Logic Layer
├── repository/         # Data Access Layer
├── entity/            # JPA Entities
├── dto/               # Data Transfer Objects
├── enums/             # Enumeration Classes
├── config/            # Configuration Classes
└── interceptor/       # Authentication Interceptors

src/main/resources/
├── templates/         # Thymeleaf Templates
│   ├── layout/       # Base Layout
│   ├── users/        # User Management Pages
│   ├── dashboard/    # Dashboard Pages
│   └── library-card/ # Library Card Pages
├── static/           # Static Resources
└── application.properties
```

## Getting Started

### Prerequisites
- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd HSF302_Nhom5
   ```

2. **Configure Database**
   - Create a MySQL database named `elibrary_db`
   - Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**
   - Open your browser and navigate to `http://localhost:8080`
   - The application will automatically create sample data on first run

## Demo Accounts

The system comes with pre-configured demo accounts:

| Role | Email | Password | Description |
|------|-------|----------|-------------|
| Admin | admin@elibrary.com | password | Full system access |
| Librarian | librarian@elibrary.com | password | Staff access with management capabilities |
| Premium | premium@elibrary.com | password | Premium member with extended privileges |
| Member | member@elibrary.com | password | Regular library member |
| Visitor | visitor@elibrary.com | password | Limited access for guests |

## Key Features Implemented

### ✅ User Management
- [x] User registration with comprehensive validation
- [x] Session-based login/logout
- [x] Profile management (view/edit)
- [x] Role-based access control
- [x] Admin user management with CRUD operations
- [x] Search and filter users with pagination

### ✅ Library Card System
- [x] Automatic library card generation
- [x] Unique card numbers and barcodes
- [x] Digital card display with QR code
- [x] Card expiration tracking
- [x] Card renewal functionality

### ✅ Dashboard & Statistics
- [x] User dashboard with quick actions
- [x] Admin dashboard with comprehensive statistics
- [x] Most borrowed books tracking
- [x] User role distribution charts
- [x] Borrowing statistics and trends
- [x] Overdue books alerts

### ✅ Technical Implementation
- [x] Spring Boot MVC architecture
- [x] Thymeleaf responsive templates
- [x] MySQL database with JPA/Hibernate
- [x] Session management configuration
- [x] Authentication interceptors
- [x] Proper error handling and validation
- [x] RESTful URL patterns

## Database Schema

The system uses the following main entities:
- **Users**: Store user account information
- **LibraryCards**: Digital library card details
- **Books**: Book catalog information
- **Categories**: Book categorization
- **Borrowings**: Book borrowing records
- **BookReports**: Book issue reporting
- **Events**: System events and notifications

## Security Features

- **Password Hashing**: BCrypt encryption for secure password storage
- **Session Management**: HTTP session-based authentication
- **Role-based Access**: Different access levels for different user roles
- **Input Validation**: Comprehensive validation on all forms
- **CSRF Protection**: Built-in Spring Security features
- **Session Timeout**: Configurable session timeout

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Email: support@elibrary.com
- Phone: (555) 123-4567
- Hours: Monday-Friday 9AM-5PM

---

**E-Library Management System** - Empowering digital libraries with modern technology.
