package com.example.HellTrain.ChatConfig;

import com.corundumstudio.socketio.SocketIOServer;
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
        // 監聽連線事件
        server.addConnectListener(client -> {
            System.out.println("收到新連線，Session ID: " + client.getSessionId());
        });

        // 監聽前端送過來的 'chatevent' 事件
        server.addEventListener("chatevent", Map.class, (client, data, ackSender) -> {
            System.out.println("收到前端訊息: " + data);
            
            // 廣播給所有在線上的使用者 (包含發送者自己)
            server.getBroadcastOperations().sendEvent("chatevent", data);
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
