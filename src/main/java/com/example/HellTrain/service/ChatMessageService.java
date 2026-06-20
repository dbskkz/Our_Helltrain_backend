package com.example.HellTrain.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.config.CloudinaryService;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ChatMessageDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.ChatMessage;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.ChatMessageRes;
import com.example.HellTrain.vo.ChatMessageVo;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageDao chatMessageDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CloudinaryService cloudinaryService;

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
		vo.setSenderId(entity.getSenderId());

		if (entity.getCreatedAt() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			vo.setCreatedAt(entity.getCreatedAt().format(formatter));
		}

		// ⚠️ 先保留（後面可優化）
		userDao.findById(entity.getSenderId()).ifPresent(user -> {
			vo.setSenderName(user.getUserName());
			vo.setSenderImg(user.getImgPath());
		});

		return vo;
	}

	// 上傳圖片
	public List<String> uploadPicture(List<String> base64Images) {
		if (base64Images == null || base64Images.isEmpty()) {
			return new ArrayList<>();
		}

		List<String> fileUrls = new ArrayList<>();
		int uploadLimit = Math.min(base64Images.size(), 3);
		for (int i = 0; i < uploadLimit; i++) {
	        String base64Image = base64Images.get(i);
	        if (!StringUtils.hasText(base64Image)) {
	            continue;
	        }
			try {
				String imageUrl = cloudinaryService.uploadBase64(base64Image);
	            fileUrls.add(imageUrl);
			} catch (Exception e) {
				System.err.println("❌ 第 " + (i + 1) + " 張圖片上傳失敗: " + e.getMessage());
				continue;
			}
		}

		return fileUrls;
	}

}
