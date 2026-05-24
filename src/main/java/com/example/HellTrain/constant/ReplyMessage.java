package com.example.HellTrain.constant;

public enum ReplyMessage {
	SUCCESS(200, "Success!!"),
	NO_DATA_FOUND(404, "No data found"),
	USER_ID_ERR(400, "The user id is invalid !"),
	PRODUCT_PARSE_ERROR(400, "Product data parse error !"),//
	//UserService使用
	EMAIL_HAS_FOUND(400,"Email has found"),//
	EMAIL_ISNOT_SCHOOL(400,"Email is not school"),//
	PARAM_NAME_ERROR(400,"Name error"),//
	PARAM_PASSWORD_ERROR(400,"Password error"),//
	EMAIL_OR_PASSWORD_ERROR(400,"Email or password error"),//
	SCHOOL_IS_NULL(400,"School is null"),//
	LOCATION_IS_NULL(400,"Location is null"),//
	PARAM_PHONE_ERROR(400,"Phone error"),//
	;
	
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
