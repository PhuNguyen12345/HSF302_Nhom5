package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.enums.MembershipRole;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventCustomerController {

    private final EventService eventService;
    private final UserService userService;

    //get all event
    @GetMapping
    public String listEvents(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Long userId = loggedInUser.getId();
        List<Event> events;

        if (userId != null || loggedInUser.getMembershipRole() != MembershipRole.ADMIN) {
            events = eventService.getEventsByUserId(userId);
        } else {
            events = eventService.getGlobalEvents();
        }

        eventService.markEventAsRead(userId);

        model.addAttribute("events", events);
        return "event";
    }

    @PostMapping("/event-details")
    public String showEventDetails(@ModelAttribute("event-details") Event event, Model model) {
        model.addAttribute("event-details", event);
        return "event-details";
    }
}
