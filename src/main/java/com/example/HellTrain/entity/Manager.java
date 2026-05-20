package com.example.HellTrain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {

	@Id
	@Column(name = "email")
	private String email;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "founding_date")
	private LocalDate foundingDate;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(LocalDate foundingDate) {
		this.foundingDate = foundingDate;
	}

}
