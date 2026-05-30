package com.example.HellTrain.response;

public class UserRes extends BasicResponse {
	
	//Object>物件，可以是List也可以是單數
	private Object user;

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public UserRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public UserRes(int statusCode, String message, Object user) {
		super(statusCode, message);
		this.user = user;
	}

}
