package com.example.HellTrain.request;

import java.time.LocalDate;
import java.util.List;

public class ProductReq {
    private int productId;
    private int userId;
    private String productName;
    private String description;
    private int price;
    private List<String> imgList;  // 前端傳來的 可能是base64也可能是file，最多7張
    private List<String> type;
    private LocalDate shelfDate;
    private String productCondition;
    private List<String> grade;
    private List<String> location;
    private List<String> deptGroup;
    
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public LocalDate getShelfDate() {
		return shelfDate;
	}
	public void setShelfDate(LocalDate shelfDate) {
		this.shelfDate = shelfDate;
	}
	public String getProductCondition() {
		return productCondition;
	}
	public void setProductCondition(String productCondition) {
		this.productCondition = productCondition;
	}
	public List<String> getGrade() {
		return grade;
	}
	public void setGrade(List<String> grade) {
		this.grade = grade;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	public List<String> getDeptGroup() {
		return deptGroup;
	}
	public void setDeptGroup(List<String> deptGroup) {
		this.deptGroup = deptGroup;
	}

}
