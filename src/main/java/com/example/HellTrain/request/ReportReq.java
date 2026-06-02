package com.example.HellTrain.request;

import java.util.List;

public class ReportReq {
	
	private int productId;//nn
	
	private int accusedId;//nn
	
	private String description;//nn
	
	private List<String> filePath;
	
	private String type;//nn
	
	private String violationType;


	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public List<String> getFilePath() {
		return filePath;
	}

	public void setFilePath(List<String> filePath) {
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
