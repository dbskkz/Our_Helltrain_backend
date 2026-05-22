package com.example.HellTrain.constant;

public enum ReplyMessage {
	SUCCESS(200, "Success!!"),
	NO_DATA_FOUND(404, "No data found"),
	USER_ID_ERR(400, "The user id is invalid !"),
	PRODUCT_PARSE_ERROR(400, "Product data parse error !");
	
	private int code;
	private String message;
	
	private ReplyMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
