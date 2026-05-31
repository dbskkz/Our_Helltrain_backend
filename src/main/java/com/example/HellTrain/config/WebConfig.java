package com.example.HellTrain.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    	String path = new File(uploadPath).toURI().toString();//將路徑轉換成合理格式
    	
//        System.out.println("uploadPath = " + uploadPath);  //檢查圖片存取路徑

        // 讓前端可以用 /uploads/檔名 存取上傳的圖片
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(path);
        
    }
}
