package com.example.HellTrain.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectVoList {

	private String productName;
	
	private String imgPath;

	private String decription;
	
	private String condition;

	private int price;

	private String sellerName;
	
	private String sellerImg;
	
	private String school;
	
	private List<String> location;
	
	private int collectId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerImg() {
		return sellerImg;
	}

	public void setSellerImg(String sellerImg) {
		this.sellerImg = sellerImg;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}


	public List<String> getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public static CollectVoList from(Object[] row) {
		CollectVoList vo = new CollectVoList();
		vo.setProductName((String) row[0]);
		List<String> imgList = parseJsonList((String) row[1]);
		vo.setImgPath(imgList.isEmpty() ? null : imgList.get(0));
		vo.setDecription((String) row[2]);
		vo.setCondition((String) row[3]);
		vo.setPrice((int) row[4]);
		vo.setSellerName((String) row[5]);
		vo.setSellerImg((String)row[6]);
		vo.setSchool((String) row[7]);
		vo.setLocation(parseJsonList((String) row[8]));	
		vo.setCollectId((int) row[9]);
		return vo;
	}

	private static List<String> parseJsonList(String json) {
		if (json == null || json.isBlank())
			return new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, new TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
