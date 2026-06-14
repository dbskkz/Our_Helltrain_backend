package com.example.HellTrain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "wish")
public class Wish {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
    
    @Column(name = "type")
    private String type;

    @Column(name = "budget_min")
    private int budgetMin;

    @Column(name = "budget_max")
    private int budgetMax;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

	public Wish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wish(int id, int userId, String title, String description, String type, int budgetMin, int budgetMax,
			String status, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.type = type;
		this.budgetMin = budgetMin;
		this.budgetMax = budgetMax;
		this.status = status;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getBudgetMin() {
		return budgetMin;
	}

	public void setBudgetMin(int budgetMin) {
		this.budgetMin = budgetMin;
	}

	public int getBudgetMax() {
		return budgetMax;
	}

	public void setBudgetMax(int budgetMax) {
		this.budgetMax = budgetMax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


}
