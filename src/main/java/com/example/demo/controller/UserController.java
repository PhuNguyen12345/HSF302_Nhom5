package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Account;
import com.example.demo.enums.MembershipRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    // ✅ Hiển thị danh sách người dùng
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_test/user-list";
    }

    // ✅ Hiển thị form thêm mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("accounts", accountRepository.findAll());
        model.addAttribute("roles", MembershipRole.values());
        return "admin_test/user-form";
    }

    // ✅ Hiển thị form cập nhật
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("user", optional.get());
            model.addAttribute("accounts", accountRepository.findAll());
            model.addAttribute("roles", MembershipRole.values());
            return "admin_test/user-form";
        } else {
            return "redirect:/admin/users";
        }
    }

    // ✅ Lưu thông tin người dùng
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            user.setCreatedAt(Instant.now());
        }
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    // ✅ Xoá người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
