package com.example.HellTrain.vo;

import java.time.LocalDate;
import java.util.List;

public class ProductVo {
	private int productId;
	private int userId;
	private String productName;
	private String description;
	private int price;
	private List<String> imgPath;
	private List<String>  type;
	private LocalDate shelfDate;
	private String productCondition;
	private String status;
	private String grade;
	private List<String>  location;
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
	public List<String> getImgPath() {
		return imgPath;
	}
	public void setImgPath(List<String> imgPath) {
		this.imgPath = imgPath;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
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
	public ProductVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductVo(int productId, int userId, String productName, String description, int price, List<String> imgPath,
			List<String> type, LocalDate shelfDate, String productCondition, String status, String grade,
			List<String> location, List<String> deptGroup) {
		super();
		this.productId = productId;
		this.userId = userId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.imgPath = imgPath;
		this.type = type;
		this.shelfDate = shelfDate;
		this.productCondition = productCondition;
		this.status = status;
		this.grade = grade;
		this.location = location;
		this.deptGroup = deptGroup;
	}
	
	
	
}
