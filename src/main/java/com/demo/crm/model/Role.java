package com.demo.crm.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true, length = 50)
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	// Constructors
	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		return name;
	}
}
