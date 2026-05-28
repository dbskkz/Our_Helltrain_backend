package com.example.HellTrain.constant;

public enum OrderStatus {

    PENDING("請求回應中"),
    ACCEPTED("訂單成立"),
    CANCELLED("已取消"),
    COMPLETED("完成交易");

    private final String message;

    OrderStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
