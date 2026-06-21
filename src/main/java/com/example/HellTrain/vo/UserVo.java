package com.example.HellTrain.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class UserVo {
    private int userId;
    private String userEmail;
    private String userName;
    private String phone;
    private List<String> location;  // JSON 字串轉 List
    private String school;
    private String department;
    private String status;
    private LocalDate verified;
    private float goodLevel;
    private String msg;
    private String imgPath;
    private String note;
    private LocalDateTime createDate;
    
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
		this.location = location;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDate getVerified() {
		return verified;
	}
	public void setVerified(LocalDate verified) {
		this.verified = verified;
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
    
}
