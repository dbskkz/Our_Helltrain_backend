package com.example.HellTrain.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.response.ChatMessageRes;
import com.example.HellTrain.service.ChatMessageService;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {
	@Autowired
	private ChatMessageService chatMessageService;

	@GetMapping(value = "/history")
	public ChatMessageRes history(@RequestParam("roomId") int roomId) {
		return chatMessageService.history(roomId);
	}

}
