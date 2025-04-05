package com.demo.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double amount;
	private LocalDate date;
	private String product;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	@JsonIgnoreProperties({ "sales" }) // Prevents infinite recursion
	private Customer customer;

	// Default Constructor
	public Sale() {
	}

	// Constructor matching the fields in the entity
	public Sale(Long id, String product, Double amount, LocalDate date, Customer customer) {
		this.id = id;
		this.product = product;
		this.amount = amount;
		this.date = date;
		this.customer = customer;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}
