package com.example.HellTrain.request;

import java.util.List;

public class WishReq {

	private String title;
    private String description;
    private List<String> type;
    private List<String> location;
    private int budgetMin;
    private int budgetMax;
    private String status;
	public WishReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WishReq(String title, String description, List<String> type, List<String> location, int budgetMin,
			int budgetMax, String status) {
		super();
		this.title = title;
		this.description = description;
		this.type = type;
		this.location = location;
		this.budgetMin = budgetMin;
		this.budgetMax = budgetMax;
		this.status = status;
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
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
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
	
    
    
    
}
