package com.example.demo.entity;

import java.text.SimpleDateFormat;

import com.example.demo.enums.ReportType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BookReports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookReport {
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Enumerated(EnumType.STRING)
	private ReportType reportType;

	private String description;
	private SimpleDateFormat reportedAt;
}
