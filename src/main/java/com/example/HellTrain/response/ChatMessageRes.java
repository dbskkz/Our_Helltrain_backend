package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.ChatMessageVo;

public class ChatMessageRes extends BasicResponse {
	private List<ChatMessageVo> chatMessageVo;

	public ChatMessageRes() {
		super();
	}

	public ChatMessageRes(int statusCode, String message) {
		super(statusCode, message);
	}

	public ChatMessageRes(List<ChatMessageVo> chatMessageVo) {
		super();
		this.chatMessageVo = chatMessageVo;
	}

	public List<ChatMessageVo> getChatMessageVo() {
		return chatMessageVo;
	}

	public void setChatMessageVo(List<ChatMessageVo> chatMessageVo) {
		this.chatMessageVo = chatMessageVo;
	}
	
	
}
