package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.dao.ChatRoomDao;
import com.example.HellTrain.entity.ChatRoom;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	public ChatRoom getOrCreateRoom(int initiatorId, int receiverId) {
		// 查「我發起，對方接收」
		ChatRoom room = chatRoomDao.findByInitiatorIdAndReceiverId(initiatorId, receiverId);
		if (room != null) {
			return room;
		}

		// 查「對方發起，我接收」
		ChatRoom roomReverse = chatRoomDao.findByInitiatorIdAndReceiverId(receiverId, initiatorId);
		if (roomReverse != null) {
			return roomReverse;
		}
		
		// 找不到，建新房間
		ChatRoom newRoom = new ChatRoom();
        newRoom.setInitiatorId(initiatorId);
        newRoom.setReceiverId(receiverId);
        
        return chatRoomDao.save(newRoom);
	}
}
