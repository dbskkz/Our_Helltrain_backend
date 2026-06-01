package com.example.HellTrain.config;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.upload-preset}")
    private String uploadPreset;

    public String uploadBase64(String base64Image) throws IOException {
        // 去掉 "data:image/jpeg;base64," 前綴
        String base64Data = base64Image.replaceAll("^data:image/\\w+;base64,", "");

        Map result = cloudinary.uploader().upload(
            Base64.getDecoder().decode(base64Data),
            ObjectUtils.asMap(
                "upload_preset", uploadPreset,
                "folder", "ershougo"  // 雲端的資料夾名稱
            )
        );
        return (String) result.get("secure_url");  // 回傳圖片 URL
    }
    public String resolveImageUrl(String imgBase64) throws IOException {
        if (imgBase64 == null) return null;
        
        // 已經是 URL（舊圖沒換），直接回傳
        if (imgBase64.startsWith("http")) {
            return imgBase64;
        }
        
        // 是 base64（新圖），上傳到 Cloudinary
        return uploadBase64(imgBase64);
    }
}
