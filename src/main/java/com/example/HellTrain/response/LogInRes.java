package com.example.HellTrain.response;


public class LogInRes extends BasicResponse {
	
	private String role;
	
	private Object data;//帳號資料，可能是User也可能是Manager

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public LogInRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogInRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public LogInRes(int statusCode, String message, String role, Object data) {
		super(statusCode, message);
		this.role = role;
		this.data = data;
	}


}
