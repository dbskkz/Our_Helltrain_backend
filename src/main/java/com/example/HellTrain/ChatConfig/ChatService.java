package com.example.HellTrain.ChatConfig;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.HellTrain.entity.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Map;

@Service
public class ChatService {
	@Autowired
    private SocketIOServer server;

    // 當 Spring Boot 啟動完成後，自動啟動 Socket 伺服器
    @PostConstruct
    public void startServer() {
        //  監聽前端發來的進房間請求
        server.addEventListener("join_room", Map.class, (client, data, ackSender) -> {
            // 1. 從前端傳來的資料中，拿到資料庫的房間流水號 (例如 1)
            String roomId = String.valueOf(data.get("roomId"));
            String userId = String.valueOf(data.get("userId"));

            // 2. 讓這個連線（Client）加入這個流水號標籤的房間
            // 如果這個房間本來不存在，Netty-Socketio 會自動幫你建立！
            client.joinRoom(roomId);

            System.out.println("🏠 使用者 [" + userId + "] 順利進入了房間流水號: " + roomId);
        });

        // 監聽前端送過來的 'chatevent' 事件
        server.addEventListener("chatevent", Map.class, (client, data, ackSender) -> {
            System.out.println("收到前端訊息: " + data);
            
            String roomId = String.valueOf(data.get("roomId")); // 確保前端發訊息時有帶房間號
            Integer senderId = ((Number) data.get("senderId")).intValue(); // 預防前端傳JSON解析成Double報錯
            String content = (String) data.get("messageContent");

            System.out.println("💬 收到房間 [" + roomId + "] 的訊息 -> " + senderId + ": " + content);

            // 發送訊息在此房間內
            server.getRoomOperations(roomId).sendEvent("chatevent", data);
            
            // 存訊息進 ChatMessage
            ChatMessage messageEntity = new ChatMessage();
            messageEntity.setRoomId(Integer.parseInt(roomId));
            messageEntity.setSenderId(senderId);
            messageEntity.setMessageContent(content);
            messageEntity.setRead(false); // 預設未讀
            
//            chatMessageDao.save(ChatMessageEntity);
        });

        // 監聽斷線事件
        server.addDisconnectListener(client -> {
            System.out.println("使用者已斷線: " + client.getSessionId());
        });


        
        server.start();
        System.out.println("==== Socket.io 伺服器已在 Port 9092 啟動 ====");
    }

    // 當 Spring Boot 關閉時，優雅地關閉 Socket 伺服器
    @PreDestroy
    public void stopServer() {
        if (server != null) {
            server.stop();
        }
    }
}
