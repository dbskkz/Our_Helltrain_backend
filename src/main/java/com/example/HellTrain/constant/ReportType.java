package com.example.HellTrain.constant;

public enum ReportType {

	Product("商品"),
	Sales("使用者");
	
	private final String message;

	public String getMessage() {
		return message;
	}

	private ReportType(String message) {
		this.message = message;
	}
	
}
