package com.example.HellTrain.entity;

import java.time.LocalDateTime;

import com.example.HellTrain.constant.ValidMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "chat_message")
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private int messageId;

	@NotNull(message = ValidMessage.ROOM_ID_IS_NULL)
	@Column(name = "room_id")
	private int roomId;

	@NotNull(message = ValidMessage.SENDER_ID_IS_NULL)
	@Column(name = "sender_id")
	private int senderId;

	@NotEmpty(message = ValidMessage.MESSAGE_CONTENT_IS_EMPTY)
	@Column(name = "message_content")
	private String messageContent;

	@Column(name = "is_read")
	private boolean isRead;

	@Column(name = "created_at", updatable = false) // updatable=false 表示這欄位一旦寫入就不能被修改
	private LocalDateTime createdAt = LocalDateTime.now();

	// getter & setter
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
