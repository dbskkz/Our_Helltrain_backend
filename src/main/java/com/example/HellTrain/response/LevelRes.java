package com.example.HellTrain.response;

public class LevelRes extends BasicResponse {
	
	private int buyerRank;
	
	private int sellerRank;

	public int getBuyerRank() {
		return buyerRank;
	}

	public void setBuyerRank(int buyerRank) {
		this.buyerRank = buyerRank;
	}

	public int getSellerRank() {
		return sellerRank;
	}

	public void setSellerRank(int sellerRank) {
		this.sellerRank = sellerRank;
	}

	public LevelRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LevelRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public LevelRes(int statusCode, String message, int buyerRank, int sellerRank) {
		super(statusCode, message);
		this.buyerRank = buyerRank;
		this.sellerRank = sellerRank;
	}
	
	
}
