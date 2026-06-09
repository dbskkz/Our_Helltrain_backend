package com.example.HellTrain.ChatConfig;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

	@Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092); // 聊天室專用的 Port，跟購物網站的 8080 分開

        // 解決前端 Angular (4200) 連過來的跨來源問題 (CORS)
//        config.setOrigin("http://localhost:4200"); 

        return new SocketIOServer(config);
    }
}
