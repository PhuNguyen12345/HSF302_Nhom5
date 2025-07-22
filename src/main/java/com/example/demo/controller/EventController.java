package com.example.demo.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class EventController {

=======
import com.example.demo.entity.Event;
import com.example.demo.enums.EventType;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@Controller
@RequestMapping("/admin/events")
public class EventController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    // ✅ Danh sách sự kiện
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "admin_test/event-list";
    }

    // ✅ Hiển thị form tạo mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("eventTypes", EventType.values());
        return "admin_test/event-form";
    }

    // ✅ Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Event> optional = eventRepository.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("event", optional.get());
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("eventTypes", EventType.values());
            return "admin_test/event-form";
        }
        return "redirect:/admin/events";
    }

    // ✅ Lưu sự kiện (tạo hoặc cập nhật)
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        if (event.getId() == null) {
            event.setCreatedAt(Instant.now());
            event.setIsRead(false); // mặc định chưa đọc
        }
        eventRepository.save(event);
        return "redirect:/admin/events";
    }

    // ✅ Xoá sự kiện
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return "redirect:/admin/events";
    }

    // ✅ Đánh dấu đã đọc
    @GetMapping("/mark-read/{id}")
    public String markAsRead(@PathVariable Long id) {
        Optional<Event> optional = eventRepository.findById(id);
        if (optional.isPresent()) {
            Event event = optional.get();
            event.setIsRead(true);
            eventRepository.save(event);
        }
        return "redirect:/admin/events";
    }
>>>>>>> an_phu
}
