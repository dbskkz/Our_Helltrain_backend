package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.OrderListVo;

public class OrderRes extends BasicResponse {
	
	private List<OrderListVo> orderList;

	public List<OrderListVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderListVo> orderList) {
		this.orderList = orderList;
	}

	public OrderRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public OrderRes(int statusCode, String message, List<OrderListVo> orderList) {
		super(statusCode, message);
		this.orderList = orderList;
	}



}
