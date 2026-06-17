package com.example.HellTrain.request;

import java.util.List;

public class WishReq {

	private String title;
    private String description;
    private List<String> type;
    private List<String> location;
    private int budgetMin;
    private int budgetMax;
	public String getTitle() {
		return title;
	}
	public WishReq(String title, String description, List<String> type, List<String> location, int budgetMin,
			int budgetMax) {
		super();
		this.title = title;
		this.description = description;
		this.type = type;
		this.location = location;
		this.budgetMin = budgetMin;
		this.budgetMax = budgetMax;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
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
	public WishReq() {
		super();
		// TODO Auto-generated constructor stub
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
    
    
}
