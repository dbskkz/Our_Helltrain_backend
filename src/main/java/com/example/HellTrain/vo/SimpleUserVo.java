package com.example.HellTrain.vo;

/*
 * 這個是專門給 product 和 wish 用的「小型 userVo」
 * 
 */
public class SimpleUserVo {

	private int userId;
	private String userName;
	private String school;
	private String userImgPath;
	private String department;

	public SimpleUserVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimpleUserVo(int userId, String userName, String school, String userImgPath, String department) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.school = school;
		this.userImgPath = userImgPath;
		this.department = department;
	}

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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getUserImgPath() {
		return userImgPath;
	}

	public void setUserImgPath(String userImgPath) {
		this.userImgPath = userImgPath;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
