package com.example.HellTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ChatMessageDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.ChatMessage;
import com.example.HellTrain.response.ChatMessageRes;
import com.example.HellTrain.vo.ChatMessageVo;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageDao chatMessageDao;
	@Autowired
	private UserDao userDao;

	public ChatMessageRes history(int roomId) {

		List<ChatMessage> entities = chatMessageDao.history(roomId);
		List<ChatMessageVo> voList = entities.stream().map(this::convertToVo).toList();

		ChatMessageRes res = new ChatMessageRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());

		res.setChatMessageVo(voList);

		return res;
	}

	private ChatMessageVo convertToVo(ChatMessage entity) {

		ChatMessageVo vo = new ChatMessageVo();

		vo.setMessageId(entity.getMessageId());
		vo.setRoomId(entity.getRoomId());
		vo.setMessageContent(entity.getMessageContent());
		vo.setCreatedAt(entity.getCreatedAt());
		vo.setSenderId(entity.getSenderId());

		// ⚠️ 先保留（後面可優化）
		userDao.findById(entity.getSenderId()).ifPresent(user -> vo.setSenderName(user.getUserName()));

		return vo;
	}

}
