package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Event;
import com.example.demo.enums.EventType;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    // ✅ Danh sách sự kiện
    @GetMapping
    public String listEvents(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        //Check user role
        if (loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            return "redirect:/dashboard";
        }
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "admin_test/event-list";
    }


    // ✅ Hiển thị form tạo mới
    @GetMapping("/create")
    public String showCreateForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        //Check user role
        if (loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            return "redirect:/dashboard";
        }
        Event event = new Event();
        event.setUser(new User());

        model.addAttribute("event", event);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("eventTypes", EventType.values());
        return "admin_test/event-form";
    }


    // ✅ Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(HttpSession session, @PathVariable Long id, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        //Check user role
        if (loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            return "redirect:/dashboard";
        }
        Event event = eventService.getEventById(id);
        if (event != null) {
            model.addAttribute("event", event);
            model.addAttribute("users", userService.findAll());
            model.addAttribute("eventTypes", EventType.values());
            return "admin_test/event-form";
        }
        return "redirect:/admin/events";
    }

    // ✅ Lưu sự kiện (tạo hoặc cập nhật)
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        Long userId = event.getUser().getId();
//        System.out.println("Received user ID: " + (event.getUser() != null ? event.getUser().getId() : "null"));
        if (event.getId() == null) {
            //new event

            event.setDateCreated(LocalDateTime.now());
            event.setRead(false); // mặc định chưa đọc

            if (userId == null) {
                eventService.createGlobalEvent(event.getTitle(), event.getContent(), event.getType());
            } else {
                eventService.createEventForUser(userId, event.getTitle(), event.getContent(), event.getType());
            }

        }else {
            // existing event → update
            Event existing = eventService.getEventById(event.getId());
            if (existing != null) {
                existing.setTitle(event.getTitle());
                existing.setContent(event.getContent());
                existing.setType(event.getType());
                existing.setUser(userId == null ? null : userService.findById(userId).orElse(null));
                eventService.saveEvent(existing); // do the update
            }
        }
        return "redirect:/admin/events";
    }

    // ✅ Xoá sự kiện
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }

    // ✅ Đánh dấu đã đọc
    @GetMapping("/mark-read/{id}")
    public String markAsRead(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            event.setRead(true);
            eventService.saveEvent(event);
        }
        return "redirect:/admin/events";
    }



}