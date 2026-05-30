package com.example.HellTrain.constant;

public enum ReportStatus {

	/*處理中、已處理*/
	Processed("已處裡"),
	Handle("處理中");
	
	
	private final String message;

	public String getMessage() {
		return message;
	}

	private ReportStatus(String message) {
		this.message = message;
	}

}
