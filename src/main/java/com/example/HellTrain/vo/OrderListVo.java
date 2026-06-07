package com.example.HellTrain.vo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public class OrderListVo {
	
    private int orderId;
    
    private LocalDate createDate;
    
    private int buyerCheck;
    
    private int sellerCheck;
    
    private String status;
    
    private String productUser;
    
    private String productName;
    
    private int price;
    
    private String buyerName;
    
    private String imgPath;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public int getBuyerCheck() {
		return buyerCheck;
	}

	public void setBuyerCheck(int buyerCheck) {
		this.buyerCheck = buyerCheck;
	}

	public int getSellerCheck() {
		return sellerCheck;
	}

	public void setSellerCheck(int sellerCheck) {
		this.sellerCheck = sellerCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductUser() {
		return productUser;
	}

	public void setProductUser(String productUser) {
		this.productUser = productUser;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
    

	public static OrderListVo fromRow(Object[] row) {
		    
		    	OrderListVo vo = new OrderListVo();//
		        vo.setOrderId((int) row[0]);//
		        vo.setCreateDate((LocalDate) row[1]);//
		        vo.setBuyerCheck((int) row[2]);//
		        vo.setSellerCheck((int) row[3]);//
		        vo.setStatus((String) row[4]);//
		        vo.setProductUser((String) row[5]);//
		        vo.setProductName((String) row[6]);//
		        vo.setPrice((int) row[7]);//
		        vo.setBuyerName((String) row[8]);//
		        // img_path 是 JSON 字串，只取第一張
		        List<String> imgList = parseJsonList((String) row[9]);
		        vo.setImgPath(imgList.isEmpty() ? null : imgList.get(0));
		        return vo;
		    
	}
    
    private static List<String> parseJsonList(String json) {
        if (json == null || json.isBlank()) return new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
