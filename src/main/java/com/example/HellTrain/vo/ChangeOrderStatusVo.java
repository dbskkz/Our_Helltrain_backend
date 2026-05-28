package com.example.HellTrain.vo;

public class ChangeOrderStatusVo {

	private String email;//呼叫方法的使用者的email

	private int orderId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
