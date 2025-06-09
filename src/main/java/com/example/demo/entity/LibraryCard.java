package com.example.demo.entity;

import java.text.SimpleDateFormat;

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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String cardNumber;
	private String barcode;
	private SimpleDateFormat issuedAt;
	private SimpleDateFormat expiredAt;
}
