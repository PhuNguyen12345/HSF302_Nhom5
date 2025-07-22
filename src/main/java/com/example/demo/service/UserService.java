package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserProfileDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LibraryCardService libraryCardService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

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

        libraryCardService.generateLibraryCard(savedUser);

        return savedUser;
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            return passwordEncoder.matches(password, userOpt.get().getPasswordHash());
        }
        return false;
    }

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

        if (profileDto.getNewPassword() != null && !profileDto.getNewPassword().isEmpty()) {
            if (!profileDto.isNewPasswordMatching()) {
                throw new RuntimeException("New passwords do not match");
            }
            user.setPasswordHash(passwordEncoder.encode(profileDto.getNewPassword()));
        }

        return userRepository.save(user);
    }

    public Page<User> findUsersWithFilters(String name, String email, MembershipRole role, Pageable pageable) {
        return userRepository.findUsersWithFilters(name, email, role, pageable);
    }

    public long countByMembershipRole(MembershipRole role) {
        return userRepository.countByMembershipRole(role);
    }

}
