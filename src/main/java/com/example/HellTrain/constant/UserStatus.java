package com.example.HellTrain.constant;

public enum UserStatus {
	
	Unverified("未驗證"),
	Normal("正常"),
	Suspension("停權");
	
    private final String message;

	public String getMessage() {
		return message;
	}

	UserStatus(String message) {
		this.message = message;
	}


}
