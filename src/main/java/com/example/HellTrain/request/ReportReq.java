package com.example.HellTrain.request;

import org.springframework.web.multipart.MultipartFile;

public class ReportReq {
	
	private int orderId;//nn
	
	private int accusedId;//nn
	
	private String description;//nn
	
	private MultipartFile filePath;
	
	private String type;//nn
	
	private String violationType;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getFilePath() {
		return filePath;
	}

	public void setFilePath(MultipartFile filePath) {
		this.filePath = filePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public int getAccusedId() {
		return accusedId;
	}

	public void setAccusedId(int accusedId) {
		this.accusedId = accusedId;
	}

	
}
