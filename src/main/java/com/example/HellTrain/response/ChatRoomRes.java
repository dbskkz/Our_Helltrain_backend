package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.ChatRoomVo;

public class ChatRoomRes extends BasicResponse {
	private int roomId;

	private int productId;

	private List<ChatRoomVo> chatRoomVo;

	public ChatRoomRes() {
		super();
	}

	public ChatRoomRes(int statusCode, String message) {
		super(statusCode, message);
	}

	public ChatRoomRes(int roomId, int productId, List<ChatRoomVo> chatRoomVo) {
		super();
		this.roomId = roomId;
		this.productId = productId;
		this.chatRoomVo = chatRoomVo;
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

	public List<ChatRoomVo> getChatRoomVo() {
		return chatRoomVo;
	}

	public void setChatRoomVo(List<ChatRoomVo> chatRoomVo) {
		this.chatRoomVo = chatRoomVo;
	}

}
