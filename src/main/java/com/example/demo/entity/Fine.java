package com.example.demo.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "borrowing_id")
	private Borrowing borrowing;

	private BigDecimal amount;
	private String reason;
	private SimpleDateFormat paidAt;
}
