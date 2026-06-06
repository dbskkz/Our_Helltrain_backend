package com.example.HellTrain.vo;

/*
 * 這個是專門給 product 用的「小型 userVo」
 * 
 */
public class SellerVo {
	
	private int userId;
    private String userName;
    private String school;
    private String userImgPath;
    
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartment() {
		return school;
	}
	public void setDepartment(String department) {
		this.school = department;
	}
	public String getUserImgPath() {
		return userImgPath;
	}
	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}
	public SellerVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SellerVo(int userId, String userName, String department, String userImgPath) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.school = department;
		this.userImgPath = userImgPath;
	}
    
}
