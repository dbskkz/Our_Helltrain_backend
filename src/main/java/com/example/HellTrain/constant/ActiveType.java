package com.example.HellTrain.constant;

public enum ActiveType {

	Accept("通過"),
	Reject("駁回");
	
	private final String message;

	public String getMessage() {
		return message;
	}

	private ActiveType(String message) {
		this.message = message;
	}
	
}
