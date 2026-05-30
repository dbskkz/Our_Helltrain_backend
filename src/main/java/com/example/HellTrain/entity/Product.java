package com.example.HellTrain.entity;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "img_path")
	private String imgPath;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "shelf_date")
	private LocalDate shelfDate;
	
	@Column(name = "product_condition")
	private String productCondition;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "grade")
	private String grade;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "dept_group")
	private String deptGroup;

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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDeptGroup() {
		return deptGroup;
	}

	public void setDeptGroup(String deptGroup) {
		this.deptGroup = deptGroup;
	}

	

}
