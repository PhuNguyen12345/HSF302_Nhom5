package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", unique = true)
	@NotNull(message = "User is required")
	private User user;

	@NotBlank(message = "Card number is required")
	@Column(unique = true, length = 20)
	private String cardNumber;

	@NotBlank(message = "Barcode is required")
	@Column(unique = true, length = 50)
	private String barcode;

	@Column(name = "issued_at")
	private LocalDate issuedAt;

	@Column(name = "expired_at")
	private LocalDate expiredAt;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		issuedAt = LocalDate.now();
		expiredAt = LocalDate.now().plusYears(5); // 5 years validity
	}

	public @NotNull(message = "User is required") User getUser() {
		return user;
	}

	public void setUser(@NotNull(message = "User is required") User user) {
		this.user = user;
	}

	public @NotBlank(message = "Card number is required") String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(@NotBlank(message = "Card number is required") String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public @NotBlank(message = "Barcode is required") String getBarcode() {
		return barcode;
	}

	public void setBarcode(@NotBlank(message = "Barcode is required") String barcode) {
		this.barcode = barcode;
	}

	public LocalDate getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(LocalDate issuedAt) {
		this.issuedAt = issuedAt;
	}

	public LocalDate getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(LocalDate expiredAt) {
		this.expiredAt = expiredAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
