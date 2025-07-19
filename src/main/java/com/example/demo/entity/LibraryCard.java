package com.example.demo.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LibraryCards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibraryCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="library_card_id", nullable=false, unique=true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String cardNumber;
	private String barcode;
	private LocalDateTime issuedAt;
	private LocalDateTime expiredAt;
}
