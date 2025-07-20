package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.enums.Role;
import com.example.demo.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // 🟢 Hiển thị danh sách tài khoản
    @GetMapping
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "admin_test/account-list";
    }

    // 🟢 Hiển thị form tạo tài khoản mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("roles", Role.values()); // enum Role
        return "admin_test/account-form";
    }

    // 🟢 Hiển thị form cập nhật tài khoản
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
        model.addAttribute("account", account);
        model.addAttribute("roles", Role.values());
        return "admin_test/account-form";
    }

    // 🟢 Lưu tài khoản (thêm hoặc cập nhật)
    @PostMapping("/save")
    public String saveAccount(@ModelAttribute("account") Account account) {
        accountRepository.save(account); // Spring sẽ update nếu có id, insert nếu chưa
        return "redirect:/admin/accounts";
    }

    // 🔴 Xoá tài khoản
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long id) {
        accountRepository.deleteById(id);
        return "redirect:/admin/accounts";
    }
}
