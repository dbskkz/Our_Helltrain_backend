package com.example.HellTrain.response;

public class BasicResponse {
	private int statusCode;
	private String message;
	
	// Constructor
	public BasicResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BasicResponse(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	
	// G&N
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
