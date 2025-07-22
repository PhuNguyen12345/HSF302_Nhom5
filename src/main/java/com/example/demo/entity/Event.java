package com.example.demo.entity;

import com.example.demo.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
    private String title;
    @Column(columnDefinition = "text")
    private String content;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name="date_created")
    private LocalDateTime dateCreated;
    @Column(name="is_read")
    private boolean isRead;

}

