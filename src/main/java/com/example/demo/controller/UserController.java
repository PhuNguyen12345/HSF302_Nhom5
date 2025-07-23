package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserProfileDto;
import com.example.demo.dto.UserRegistrationDto;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistration", new UserRegistrationDto());
        model.addAttribute("membershipRoles", MembershipRole.values());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegistration") UserRegistrationDto registrationDto,
                              BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("membershipRoles", MembershipRole.values());
            return "users/register";
        }

        try {
            userService.registerUser(registrationDto);
            redirectAttributes.addFlashAttribute("successMessage",
                "Registration successful! You can now login with your credentials.");
            return "redirect:/users/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("membershipRoles", MembershipRole.values());
            return "users/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLogin", new UserLoginDto());
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("userLogin") UserLoginDto loginDto,
                           BindingResult result, HttpSession session, Model model,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "users/login";
        }

        if (userService.authenticateUser(loginDto.getEmail(), loginDto.getPassword())) {
            Optional<User> userOpt = userService.findByEmail(loginDto.getEmail());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("loggedInUser", user);
                session.setAttribute("userRole", user.getMembershipRole());

                redirectAttributes.addFlashAttribute("successMessage",
                    "Welcome back, " + user.getName() + "!");


                if (user.getMembershipRole() == MembershipRole.ADMIN ||
                    user.getMembershipRole() == MembershipRole.LIBRARIAN) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/dashboard";
                }
            }
        }



        model.addAttribute("errorMessage", "Invalid email or password");
        return "users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully.");
        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        // Refresh user data from database
        Optional<User> userOpt = userService.findById(loggedInUser.getId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserProfileDto profileDto = new UserProfileDto();
            profileDto.setId(user.getId());
            profileDto.setName(user.getName());
            profileDto.setEmail(user.getEmail());
            profileDto.setPhone(user.getPhone());
            profileDto.setDob(user.getDob());
            profileDto.setAddress(user.getAddress());
            profileDto.setMembershipRole(user.getMembershipRole());
            profileDto.setCreatedAt(user.getCreatedAt());
            profileDto.setUpdatedAt(user.getUpdatedAt());
            model.addAttribute("userProfile", profileDto);
            return "users/profile";
        }

        return "redirect:/users/login";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("userProfile") UserProfileDto profileDto,
                               BindingResult result, HttpSession session, Model model,
                               RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        if (result.hasErrors()) {
            return "users/profile";
        }

        try {
            profileDto.setId(loggedInUser.getId());
            User updatedUser = userService.updateProfile(profileDto);
            session.setAttribute("loggedInUser", updatedUser);

            redirectAttributes.addFlashAttribute("successMessage",
                "Profile updated successfully!");
            return "redirect:/users/profile";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "users/profile";
        }
    }

    // Admin-only methods
    @GetMapping("/manage")
    public String manageUsers(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "name") String sortBy,
                             @RequestParam(defaultValue = "asc") String sortDir,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) MembershipRole role,
                             HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = userService.findUsersWithFilters(name, email, role, pageable);

        model.addAttribute("size", size);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalElements", users.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("membershipRoles", MembershipRole.values());

        return "users/manage";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserProfileDto profileDto = new UserProfileDto();
            profileDto.setId(user.getId());
            profileDto.setName(user.getName());
            profileDto.setEmail(user.getEmail());
            profileDto.setPhone(user.getPhone());
            profileDto.setDob(user.getDob());
            profileDto.setAddress(user.getAddress());
            profileDto.setMembershipRole(user.getMembershipRole());

            model.addAttribute("userProfile", profileDto);
            model.addAttribute("membershipRoles", MembershipRole.values());
            model.addAttribute("isAdminEdit", true);
            return "users/edit";
        }

        return "redirect:/users/manage";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                            @Valid @ModelAttribute("userProfile") UserProfileDto profileDto,
                            BindingResult result, HttpSession session, Model model,
                            RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null ||
            (loggedInUser.getMembershipRole() != MembershipRole.ADMIN &&
             loggedInUser.getMembershipRole() != MembershipRole.LIBRARIAN)) {
            return "redirect:/dashboard";
        }

        if (result.hasErrors()) {
            model.addAttribute("membershipRoles", MembershipRole.values());
            model.addAttribute("isAdminEdit", true);
            return "users/edit";
        }

        try {
            profileDto.setId(id);
            userService.updateProfile(profileDto);
            redirectAttributes.addFlashAttribute("successMessage",
                "User updated successfully!");
            return "redirect:/users/manage";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("membershipRoles", MembershipRole.values());
            model.addAttribute("isAdminEdit", true);
            return "users/edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            return "redirect:/dashboard";
        }

        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage",
                "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error deleting user: " + e.getMessage());
        }

        return "redirect:/users/manage";
    }
}
