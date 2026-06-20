package com.example.HellTrain.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/upload-img")
	public ResponseEntity<Map<String, Object>> uploadPicture(@RequestBody Map<String, List<String>> pictures) {
	    
	    List<String> base64Images = pictures.get("pictures");
	    List<String> imageUrls = chatMessageService.uploadPicture(base64Images); 
	    
	    //給前端
	    Map<String, Object> response = new HashMap<>();
	    response.put("imageUrls", imageUrls); 
	    
	    return ResponseEntity.ok(response);
	}

}
