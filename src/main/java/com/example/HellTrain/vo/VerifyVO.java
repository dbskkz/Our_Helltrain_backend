package com.example.HellTrain.vo;

public class VerifyVO {
	private String email; // 要驗證帳號
	private String code; // 使用者輸入的驗證碼

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
