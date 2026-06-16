package com.example.HellTrain.response;

public class ChatRoomRes extends BasicResponse {
	private int roomId;

	public ChatRoomRes() {
		super();
	}

	public ChatRoomRes(int statusCode, String message) {
		super(statusCode, message);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
