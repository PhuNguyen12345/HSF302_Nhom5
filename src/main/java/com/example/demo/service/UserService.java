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
public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(Long id);

    boolean existsByEmail(String email);

    User registerUser(UserRegistrationDto registrationDto);

    boolean authenticateUser(String email, String password);

    User updateProfile(UserProfileDto profileDto);

    Page<User> findUsersWithFilters(String name, String email, MembershipRole role, Pageable pageable);

    long countByMembershipRole(MembershipRole role);
}
