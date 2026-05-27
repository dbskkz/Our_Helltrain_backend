package com.example.HellTrain.constant;

public enum ReplyMessage {
	SUCCESS(200, "Success!!"),
	NO_DATA_FOUND(404, "No data found"),
	USER_ID_ERR(400, "The user id is invalid !"),
	PRODUCT_PARSE_ERROR(400, "Product data format parse error !"),//
	INVALID_PARAM(400, "The parameter cannot be empty !"),//
	//UserService使用
	VERIFICATION_CODE_IS_SEND(200, "Verification code is send"),//發送新的驗證碼
	EMAIL_HAS_FOUND(400,"Email has found"),//檢查email是否註冊過
	EMAIL_ISNOT_SCHOOL(400,"Email is not school"),//
	PARAM_NAME_ERROR(400,"Name error"),//
	PARAM_PASSWORD_ERROR(400,"Password error"),//
	EMAIL_OR_PASSWORD_ERROR(400,"Email or password error"),//
	SCHOOL_IS_NULL(400,"School is null"),//
	LOCATION_IS_NULL(400,"Location is null"),//
	PARAM_PHONE_ERROR(400,"Phone error"),//
	MESSAGE_TOO_LONG(400,"Message too long"),//
	PLEASE_LOGIN_FIRST(400,"Please login first"),//
	DEPARTMENT_IS_NULL(400,"Department is null"),//
	EMAIL_NOT_FOUND(400,"Email not found"),//
	INVALID_VERIFICATION_CODE(400,"Invalid verification code"),//
	ACCOUNT_IS_VERIFICATION(400,"Account is verification"),//帳號已驗證
	FILE_FORMAT_ERROR(400,"File format error"),//檔案格式不正確
	FILE_NAMEFORMAT_ERROR(400,"File name error"),//檔案附檔名不正確
	FILE_SIZE_ERROR(400,"File size error"),//
	PLEASE_TRY_LATE(400,"Please try late"),//
	PASSWORD_NOT_CHANGE(400,"Password not change"),//檢查更改的密碼是否與原密碼一致
	//Order用
	PRODUCT_is_NOTFOUND(200, "Product is not found"),//找不到商品
	PRODUCT_is_UNSELL(200, "Product is unsell"),//未販售的商品
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
