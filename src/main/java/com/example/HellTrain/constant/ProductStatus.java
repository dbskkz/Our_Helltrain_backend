package com.example.HellTrain.constant;

public enum ProductStatus {
	NotSale("未上架"),
	OnSale("販售中"),
	Trade("交易中"),
	Removed("已下架");
	
	private final String massage;

	public String getMassage() {
		return massage;
	}

	private ProductStatus(String massage) {
		this.massage = massage;
	}

}
