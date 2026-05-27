package com.example.HellTrain.component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component //幫我管理這個 class ，需要的時候自動注入
public class VerificationCodeStore {


    // 同時暫存驗證碼、過期時間、註冊資料
    private final Map<String, CodeEntry> store = new ConcurrentHashMap<>();

    private static class CodeEntry {
        String code;
        LocalDateTime expireAt;

        CodeEntry(String code) {
            this.code = code;
            this.expireAt = LocalDateTime.now().plusMinutes(15);
        }
    }

    // 產生驗證碼
    public String generate(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        store.put(email, new CodeEntry(code));
        return code;
    }

    // 驗證
    public boolean verify(String email, String inputCode) {
        CodeEntry entry = store.get(email);
        
        // 加這幾行 log
        System.out.println("store 裡的驗證碼：" + (entry != null ? entry.code : "null"));
        System.out.println("使用者輸入的驗證碼：" + inputCode);
        System.out.println("過期時間：" + (entry != null ? entry.expireAt : "null"));
        System.out.println("現在時間：" + LocalDateTime.now());
        if (entry == null) return false;
        if (LocalDateTime.now().isAfter(entry.expireAt)) {
            store.remove(email);
            return false;
        }
        return entry.code.equals(inputCode);
    }

    // 銷毀
    public void remove(String email) {
        store.remove(email);
    }

}
