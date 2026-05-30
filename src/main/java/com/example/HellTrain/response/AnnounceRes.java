package com.example.HellTrain.response;

public class AnnounceRes extends BasicResponse {
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public AnnounceRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnnounceRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public AnnounceRes(int statusCode, String message, Object data) {
		super(statusCode, message);
		this.data = data;
	}

}
