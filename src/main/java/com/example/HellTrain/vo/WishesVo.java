package com.example.HellTrain.vo;

import java.time.LocalDateTime;
import java.util.List;

public class WishesVo {

	private int id;
	private int userId;
	private String title;
	private String description;
	private List<String> type;
	private int budgetMin;
	private int budgetMax;
	private String status;
	private LocalDateTime createdAt;
	private SimpleUserVo wisher;
	
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
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
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
	public SimpleUserVo getWisher() {
		return wisher;
	}
	public void setWisher(SimpleUserVo wisher) {
		this.wisher = wisher;
	}
	public WishesVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WishesVo(int id, int userId, String title, String description, List<String> type, int budgetMin,
			int budgetMax, String status, LocalDateTime createdAt, SimpleUserVo wisher) {
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
		this.wisher = wisher;
	}
	
	
}
