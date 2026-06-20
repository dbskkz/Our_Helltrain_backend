package com.example.HellTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ChatMessageDao;
import com.example.HellTrain.dao.ChatRoomDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.ChatRoom;
import com.example.HellTrain.response.ChatRoomRes;
import com.example.HellTrain.vo.ChatRoomVo;

import jakarta.transaction.Transactional;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ChatMessageDao chatMessageDao;

	// 尋找某一房間或建立
	public ChatRoomRes getOrCreateRoom(int initiatorId, int receiverId) {
		ChatRoom room = chatRoomDao.findChatRoom(initiatorId, receiverId);

		if (room == null) {
			ChatRoom newRoom = new ChatRoom();
			newRoom.setInitiatorId(initiatorId);
			newRoom.setReceiverId(receiverId);

			room = chatRoomDao.save(newRoom);
		}

		ChatRoomRes res = new ChatRoomRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());

		res.setRoomId(room.getRoomId());

		return res;
	}

	// 取得單一使用者的所有房間
	public ChatRoomRes getAllRoom(int userId) {
		List<ChatRoom> roomList = chatRoomDao.getAllRoom(userId);
		List<ChatRoomVo> chatRoomVo = roomList.stream() //
				.map((ChatRoom room) -> this.convertToVo(room, userId)).toList();

		ChatRoomRes res = new ChatRoomRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
		res.setChatRoomVo(chatRoomVo);
		return res;
	}

	private ChatRoomVo convertToVo(ChatRoom room, int userId) {
		ChatRoomVo vo = new ChatRoomVo();
		vo.setRoomId(room.getRoomId());

		// 判斷哪一個是對方ID
		int targetUserId;
		if (room.getInitiatorId() == userId) {
			targetUserId = room.getReceiverId();
		} else {
			targetUserId = room.getInitiatorId();
		}
		vo.setTargetUserId(targetUserId);

		// 取得對方姓名及頭貼
		userDao.findById(targetUserId).ifPresent(user -> {
			vo.setTargetUserName(user.getUserName());
			vo.setTargetUserImg(user.getImgPath());
		});

		// 取得最後一則訊息
		chatMessageDao.lastMessage(room.getRoomId()).ifPresent(msg -> {
			vo.setLastMessage(msg.getMessageContent());
		});

		// 取得未讀數量
		int unread = chatMessageDao.countUnreadMsg(room.getRoomId(), targetUserId);
		vo.setUnreadCount(unread);

		return vo;
	}

	// 已讀所有訊息
	@Transactional
	public void readAllRoomMessages(int roomId, int userId) {
		chatRoomDao.findById(roomId).ifPresent(room -> {
			int targetUserId;
			if (room.getInitiatorId() == userId) {
				targetUserId = room.getReceiverId();
			} else {
				targetUserId = room.getInitiatorId();
			}

			chatMessageDao.readAllMessages(roomId, targetUserId);
		});
	}
}
