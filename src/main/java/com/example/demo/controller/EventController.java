package com.example.demo.controller;

import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
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
import java.util.Optional;

@Controller
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    // ✅ Danh sách sự kiện
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin_test/event-list";
    }

    // ✅ Hiển thị form tạo mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("users", eventService.getAllEvents());
        model.addAttribute("eventTypes", EventType.values());
        return "admin_test/event-form";
    }

    // ✅ Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            model.addAttribute("event", event);
            model.addAttribute("users", userService.getAllUser());
            model.addAttribute("eventTypes", EventType.values());
            return "admin_test/event-form";
        }
        return "redirect:/admin/events";
    }

    // ✅ Lưu sự kiện (tạo hoặc cập nhật)
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        if (event.getId() == null) {
            event.setDateCreated(LocalDateTime.from(Instant.now()));
            event.setRead(false); // mặc định chưa đọc
        }
        eventService.createGlobalEvent(event.getTitle(), event.getContent(), event.getType());
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
    public String markAsRead(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event != null) {
            event.setRead(true);
            eventService.createGlobalEvent(event.getTitle(), event.getContent(), event.getType());
        }
        return "redirect:/admin/events";
    }



}