package com.example.HellTrain.dao;

import java.time.LocalDate;

public interface OrderListProjection {
    Integer getOrderId();	 // 訂單 ID
    LocalDate getCreateDate(); // 訂單建立時間 
    Byte getBuyerCheck();  // 買家確認完成交易
    Byte getSellerCheck(); // 賣家確認完成交易
    String getStatus();  // 訂單狀態
    String getSellerName();  // 賣家名稱
    String getProductName();  // 商品名稱 
    Integer getPrice();  // 商品價格
    String getBuyerName();  // 買家名稱
    String getImgPath();       // 圖片路徑
    Integer getBuyerId();      // 買家 ID
    Integer getSellerId();     // 賣家 ID
    Integer getBuyerRank();    // 給買家的評分
    Integer getSalesmanRank(); // 給賣家的評分
}
