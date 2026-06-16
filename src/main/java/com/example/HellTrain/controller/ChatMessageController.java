package com.example.HellTrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.response.ChatMessageRes;
import com.example.HellTrain.service.ChatMessageService;
import com.example.HellTrain.vo.ChatMessageVo;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {
	@Autowired
	private ChatMessageService chatMessageService;

	@GetMapping(value = "/history")
	public ChatMessageRes history(@RequestParam int roomId) {
		return chatMessageService.history(roomId);
	}

}
