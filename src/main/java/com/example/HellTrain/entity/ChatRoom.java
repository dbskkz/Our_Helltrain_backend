package com.example.HellTrain.entity;

import java.time.LocalDateTime;

import com.example.HellTrain.constant.ValidMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "chat_room")
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private int roomId;

	@Column(name = "product_id")
	private int productId;

	@NotNull(message = ValidMessage.INITIATOR_ID_IS_NULL)
	@Column(name = "initiator_id")
	private int initiatorId;

	@NotNull(message = ValidMessage.RECEIVER_ID_IS_NULL)
	@Column(name = "receiver_id")
	private int receiverId;
	
	@Column(name = "created_at", updatable = false) // updatable=false 表示這欄位一旦寫入就不能被修改
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
