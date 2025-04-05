package com.demo.crm.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "interactions")
public class Interaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private String interactionType;
	private String details;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // 👈 Add this line
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	// Constructors
	public Interaction() {
	}

	public Interaction(Customer customer, String interactionType, String details, Date date) {
		this.customer = customer;
		this.interactionType = interactionType;
		this.details = details;
		this.date = date;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
