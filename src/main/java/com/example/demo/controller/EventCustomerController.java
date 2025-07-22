package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventCustomerController {

    private final EventService eventService;
    private final UserService userService;

    //get all event
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "event";
    }

    @PostMapping("/event-details")
    public String showEventDetails(@ModelAttribute("event-details") Event event, Model model) {
        model.addAttribute("event-details", event);
        return "event-details";
    }
}
