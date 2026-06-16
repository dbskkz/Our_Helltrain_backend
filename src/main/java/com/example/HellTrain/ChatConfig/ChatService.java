package com.example.HellTrain.ChatConfig;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.HellTrain.dao.ChatMessageDao;
import com.example.HellTrain.dao.UserDao;

import com.example.HellTrain.entity.ChatMessage;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.vo.ChatMessageVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Map;

@Service
public class ChatService {
	@Autowired
    private SocketIOServer server;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ChatMessageDao chatMessageDao;

    // 當 Spring Boot 啟動完成後，自動啟動 Socket 伺服器
    @PostConstruct
    public void startServer() {
        //  監聽前端發來的進房間請求
        server.addEventListener("join_room", Map.class, (client, data, ackSender) -> {
            // 1. 從前端傳來的資料中，拿到資料庫的房間流水號 (例如 1)
            String roomId = String.valueOf(data.get("roomId"));
            String userName = (String) data.get("userName");

            // 2. 讓這個連線（Client）加入這個流水號標籤的房間
            // 如果這個房間本來不存在，Netty-Socketio 會自動幫你建立！
            client.joinRoom(roomId);

            System.out.println("🏠 使用者 [" + userName + "] 順利進入了房間流水號: " + roomId);
        });

        // 監聽前端送過來的 'chatevent' 事件
        server.addEventListener("chatevent", Map.class, (client, data, ackSender) -> {
            System.out.println("收到前端訊息: " + data);
            
            try {
                String roomId = String.valueOf(data.get("roomId")); // 確保前端發訊息時有帶房間號
                Integer senderId = ((Number) data.get("senderId")).intValue(); // 預防前端傳JSON解析成Double報錯
                String content = (String) data.get("messageContent");

                // 先存訊息進資料庫，拿到含有流水號的 savedEntity
                ChatMessage messageEntity = new ChatMessage();
                messageEntity.setRoomId(Integer.parseInt(roomId));
                messageEntity.setSenderId(senderId);
                messageEntity.setMessageContent(content);
                messageEntity.setRead(false); // 預設未讀

                // 存檔並收回完整的實體物件
                ChatMessage savedEntity = chatMessageDao.save(messageEntity);

                // 去 user 表查出名字與大頭貼網址
                User user = userDao.findById(senderId).orElse(null);
                String senderName = (user != null) ? user.getUserName() : "未知用戶";
                String senderImg = (user != null) ? user.getImgPath() : "default_avatar.png"; 

                ChatMessageVo vo = new ChatMessageVo();
                vo.setMessageId(savedEntity.getMessageId());      // 訊息流水號
                vo.setRoomId(savedEntity.getRoomId());            // 房間流水號
                
                if (savedEntity.getCreatedAt() != null) {
                    vo.setCreatedAt(savedEntity.getCreatedAt());
                } else {
                    vo.setCreatedAt(java.time.LocalDateTime.now());
                }
                
                vo.setMessageContent(savedEntity.getMessageContent()); //  訊息內容
                vo.setRead(savedEntity.isRead());                 // 是否已讀
                vo.setSenderId(savedEntity.getSenderId());        // 發送者 ID
                vo.setSenderName(senderName);                     // 發送者名字 
                vo.setSenderImg(senderImg);                       // 發送者大頭貼 

                System.out.println("💬 順利裝箱！房間 [" + roomId + "] -> " + senderName + ": " + content);

                // 廣播
                server.getRoomOperations(roomId).sendEvent("chatevent", vo);

            } catch (Exception e) {
                System.err.println("❌ Socket 收發訊息發生慘劇: " + e.getMessage());
                e.printStackTrace();
            }
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
