package com.example.HellTrain.vo;

public class ChatRoomVo {

	private int roomId;

	private int productId;

	private int targetUserId;

	private String targetUserName;

	private String targetUserImg;

	private String lastMessage;

	private int unreadCount;

	public ChatRoomVo() {
		super();
	}

	public ChatRoomVo(int roomId, int productId, int targetUserId, String targetUserName, String targetUserImg,
			String lastMessage, int unreadCount) {
		super();
		this.roomId = roomId;
		this.productId = productId;
		this.targetUserId = targetUserId;
		this.targetUserName = targetUserName;
		this.targetUserImg = targetUserImg;
		this.lastMessage = lastMessage;
		this.unreadCount = unreadCount;
	}

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

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getTargetUserName() {
		return targetUserName;
	}

	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}

	public String getTargetUserImg() {
		return targetUserImg;
	}

	public void setTargetUserImg(String targetUserImg) {
		this.targetUserImg = targetUserImg;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

}
