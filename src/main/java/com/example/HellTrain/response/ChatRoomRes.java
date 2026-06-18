package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.ChatRoomVo;

public class ChatRoomRes extends BasicResponse {
	private int roomId;
	
	private List <ChatRoomVo> chatRoomVo;

	public ChatRoomRes() {
		super();
	}

	public ChatRoomRes(int statusCode, String message) {
		super(statusCode, message);
	}

	public ChatRoomRes(int roomId, List<ChatRoomVo> chatRoomVo) {
		super();
		this.roomId = roomId;
		this.chatRoomVo = chatRoomVo;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public List<ChatRoomVo> getChatRoomVo() {
		return chatRoomVo;
	}

	public void setChatRoomVo(List<ChatRoomVo> chatRoomVo) {
		this.chatRoomVo = chatRoomVo;
	}

	
}
