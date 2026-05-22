package com.example.HellTrain.entity;
import java.time.LocalDate;
import jakarta.persistence.*;

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
	
	@Column(name = "department_id")
	private int departmentId;//校系
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "student_verified_at")
	private LocalDate studentVerifiedAt;
	
	@Column(name = "good_level")
	private int goodLevel;
	
	@Column(name = "msg")
	private String msg;
	
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getStudentVerifiedAt() {
		return studentVerifiedAt;
	}
	public void setStudentVerifiedAt(LocalDate studentVerifiedAt) {
		this.studentVerifiedAt = studentVerifiedAt;
	}
	public int getGoodLevel() {
		return goodLevel;
	}
	public void setGoodLevel(int goodLevel) {
		this.goodLevel = goodLevel;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
