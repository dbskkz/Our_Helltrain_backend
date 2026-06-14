package com.example.HellTrain.request;

import java.time.LocalDateTime;

public class ChatRoomReq {

	private int roomId;

	private int productId;

	private int initiatorId;

	private int receiverId;

	private LocalDateTime createdAt;

	// getter & setter
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(int initiatorId) {
		this.initiatorId = initiatorId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
