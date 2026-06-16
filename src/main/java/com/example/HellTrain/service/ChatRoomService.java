package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ChatRoomDao;
import com.example.HellTrain.entity.ChatRoom;
import com.example.HellTrain.response.ChatRoomRes;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	public ChatRoomRes getOrCreateRoom(int initiatorId, int receiverId) {
		ChatRoom room = chatRoomDao.findChatRoom(initiatorId, receiverId);

		if (room == null) {
			ChatRoom newRoom = new ChatRoom();
			newRoom.setInitiatorId(initiatorId);
			newRoom.setReceiverId(receiverId);

			room = chatRoomDao.save(newRoom);
		}

		 ChatRoomRes res = new ChatRoomRes(
	                ReplyMessage.SUCCESS.getCode(),
	                ReplyMessage.SUCCESS.getMessage()
	        );

	        res.setRoomId(room.getRoomId());

	        return res;
	}
}
