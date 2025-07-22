package com.example.demo.entity;

import com.example.demo.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private EventType type;

    private LocalDateTime dateCreated;

    private boolean isRead;

}

