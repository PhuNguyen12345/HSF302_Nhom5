package com.example.demo.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Fines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Fine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="fine_id", nullable=false, unique=true)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "borrowing_id")
	private Borrowing borrowing;

	private BigDecimal amount;
	private String reason;
	private LocalDateTime paidAt;
}
