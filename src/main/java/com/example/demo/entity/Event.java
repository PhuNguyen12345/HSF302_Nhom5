package com.example.demo.entity;

import com.example.demo.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	@Enumerated(EnumType.STRING)
	private EventType type;

	private LocalDateTime timestamp;

	private boolean read = false;

	@ManyToOne
	@JoinColumn(name = "user_id") // optional: allow null for system-wide events
	private User user;
}
