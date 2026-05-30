package com.example.HellTrain.entity;

import java.time.LocalDate;


import jakarta.persistence.*;

@Entity
@Table(name = "violation")
@IdClass(value = ViolationId.class)
public class Violation {
	
	@Id
	@Column(name = "user_email")
	private String userEmail;
	
	@Id
	@Column(name = "violation_id")
	private int violationId;
	
	@Column(name = "violation_type")
	private String violationType;
	
	@Column(name = "violation_at")
	private LocalDate violationAt;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getViolationId() {
		return violationId;
	}

	public void setViolationId(int violationId) {
		this.violationId = violationId;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public LocalDate getViolationAt() {
		return violationAt;
	}

	public void setViolationAt(LocalDate violationAt) {
		this.violationAt = violationAt;
	}

}
