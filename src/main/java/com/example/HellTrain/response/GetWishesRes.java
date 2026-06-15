package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.WishesVo;

public class GetWishesRes extends BasicResponse{
	private List<WishesVo> wishesList;

	public List<WishesVo> getWishesList() {
		return wishesList;
	}

	public void setWishesList(List<WishesVo> wishesList) {
		this.wishesList = wishesList;
	}

	public GetWishesRes(int statusCode, String message, List<WishesVo> wishesList) {
		super(statusCode, message);
		this.wishesList = wishesList;
	}

	public GetWishesRes(List<WishesVo> wishesList) {
		super();
		this.wishesList = wishesList;
	}

	public GetWishesRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetWishesRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}
}
