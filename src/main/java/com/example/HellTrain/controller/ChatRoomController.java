package com.example.HellTrain.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.entity.ChatRoom;
import com.example.HellTrain.service.ChatRoomService;


@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	@Autowired
    private ChatRoomService chatRoomService;
	
	@PostMapping("/get-or-create")
    public ResponseEntity<ChatRoom> getOrCreateRoom(@RequestBody Map<String, Integer> ChatRoomReq) {
        int initiatorId = ChatRoomReq.get("initiatorId");
        int receiverId = ChatRoomReq.get("receiverId");

        ChatRoom room = chatRoomService.getOrCreateRoom(initiatorId, receiverId);
        return ResponseEntity.ok(room);
    }
}
