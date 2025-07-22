package com.example.demo.service.serviceImpl;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.LibraryCardService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryCardService libraryCardService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        if (existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (!registrationDto.isPasswordMatching()) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = new User();
        user.setName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setPhone(registrationDto.getPhone());
        user.setDob(registrationDto.getDob());
        user.setAddress(registrationDto.getAddress());
        user.setMembershipRole(registrationDto.getMembershipRole());

        User savedUser = userRepository.save(user);

        // Automatically generate library card
        libraryCardService.generateLibraryCard(savedUser);

        return savedUser;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            return passwordEncoder.matches(password, userOpt.get().getPasswordHash());
        }
        return false;
    }

    @Override
    public User updateProfile(UserProfileDto profileDto) {
        Optional<User> userOpt = findById(profileDto.getId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        user.setName(profileDto.getName());
        user.setPhone(profileDto.getPhone());
        user.setDob(profileDto.getDob());
        user.setAddress(profileDto.getAddress());

        // Update password if provided
        if (profileDto.getNewPassword() != null && !profileDto.getNewPassword().isEmpty()) {
            if (!profileDto.isNewPasswordMatching()) {
                throw new RuntimeException("New passwords do not match");
            }
            user.setPasswordHash(passwordEncoder.encode(profileDto.getNewPassword()));
        }

        return userRepository.save(user);
    }


    @Override
    public Page<User> findUsersWithFilters(String name, String email, MembershipRole role, Pageable pageable) {
        return userRepository.findUsersWithFilters(name, email, role, pageable);
    }

    @Override
    public long countByMembershipRole(MembershipRole role) {
        return userRepository.countByMembershipRole(role);
    }
}

