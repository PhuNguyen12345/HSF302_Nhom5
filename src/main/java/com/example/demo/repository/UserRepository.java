package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByMembershipRole(MembershipRole role);

    @Query("SELECT u FROM User u WHERE " +
           "(:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:role IS NULL OR u.membershipRole = :role)")
    Page<User> findUsersWithFilters(@Param("name") String name,
                                   @Param("email") String email,
                                   @Param("role") MembershipRole role,
                                   Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u WHERE u.membershipRole = :role")
    long countByMembershipRole(@Param("role") MembershipRole role);
}
