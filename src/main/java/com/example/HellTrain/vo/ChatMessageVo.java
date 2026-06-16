package com.example.HellTrain.vo;


public class ChatMessageVo {
	private int messageId;
	
	private int roomId;
	
	private String createdAt;
	
	private String messageContent;
	
	private boolean isRead;
	
	private int senderId;
	
	private String senderName;
	
	private String senderImg;

	public ChatMessageVo() {
		super();
	}

	public ChatMessageVo(int messageId, int roomId, String createdAt, String messageContent, boolean isRead,
			int senderId, String senderName, String senderImg) {
		super();
		this.messageId = messageId;
		this.roomId = roomId;
		this.createdAt = createdAt;
		this.messageContent = messageContent;
		this.isRead = isRead;
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderImg = senderImg;
	}

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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
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

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderImg() {
		return senderImg;
	}

	public void setSenderImg(String senderImg) {
		this.senderImg = senderImg;
	}
	
}
