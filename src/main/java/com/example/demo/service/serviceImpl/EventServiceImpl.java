package com.example.demo.service.serviceImpl;

import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.enums.EventType;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public Event updateEvent(Long eventId, String title, String description, EventType type) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setTitle(title);
        event.setContent(description);
        event.setType(type);
        return eventRepository.save(event);
    }

    @Override
    public Event createEventForUser(Long userId, String title, String description, EventType type) {
        User user = userRepository.findById(userId).orElseThrow();

        Event event = new Event();
        event.setUser(user);
        event.setTitle(title);
        event.setContent(description);
        event.setType(type);
        event.setDateCreated(LocalDateTime.now());
        //event.setRead(false);
        return eventRepository.save(event);
    }

    @Override
    public Event createGlobalEvent(String title, String description, EventType type) {
        Event event = new Event();
        event.setUser(null); // global event
        event.setTitle(title);
        event.setContent(description);
        event.setType(type);
        event.setDateCreated(LocalDateTime.now());
        //event.setRead(false);

        return eventRepository.save(event);
    }

    @Override
    public List<Event> getUnreadEventsForUser(Long userId) {
        return List.of();
    }

    @Override
    public void markEventAsRead(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setRead(true);
        eventRepository.save(event);
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    @Override
    public List<Event> getGlobalEvents() {
        return eventRepository.findByUserIdIsNull();
    }

}

