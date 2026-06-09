package com.example.HellTrain.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "user_email")
	private String userEmail;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "school")
	private String school;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "verified")
	private LocalDate verified;
	
	@Column(name = "good_level")
	private float goodLevel;
	
	@Column(name = "msg")
	private String msg;
	
	@Column(name = "img_path")
	private String imgPath;
	
	@Column(name = "create_date")
	private LocalDateTime createDate;
	
	@Column(name = "note")
	private String note;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getSchool() {
		return school;
	}
	public void setSchool(String schoolId) {
		this.school = schoolId;
	}
	public LocalDate getVerified() {
		return verified;
	}
	public void setVerified(LocalDate verified) {
		this.verified = verified;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public float getGoodLevel() {
		return goodLevel;
	}
	public void setGoodLevel(float goodLevel) {
		this.goodLevel = goodLevel;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


}
