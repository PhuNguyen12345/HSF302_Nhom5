package com.example.demo.entity;

import java.text.SimpleDateFormat;

import com.example.demo.enums.EventType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String title;
	private String content;

	@Enumerated(EnumType.STRING)
	private EventType type;

	private SimpleDateFormat createdAt;
}
