package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.enums.EventType;

import java.util.List;

public interface EventService {

    List<Event> getAllEvents();

    Event getEventById(Long id);

    void deleteEvent(Long eventId);

    Event updateEvent(Long id, String title, String description, EventType type);

    // create
    Event createEventForUser(Long userId, String title, String description, EventType type);

    Event createGlobalEvent(String title, String description, EventType type);

    List<Event> getUnreadEventsForUser(Long userId);

    void markEventAsRead(Long eventId);

    Event saveEvent(Event event);
}
