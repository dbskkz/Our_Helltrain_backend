package com.example.HellTrain.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectVoList {

	private String productName;

	private String location;

	private String imgPath;

	private int price;

	private String sellerName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

	public static CollectVoList from(Object[] row) {
		CollectVoList vo = new CollectVoList();
		vo.setProductName((String) row[0]);
		vo.setLocation((String) row[1]);
		List<String> imgList = parseJsonList((String) row[2]);
		vo.setImgPath(imgList.isEmpty() ? null : imgList.get(0));
		vo.setPrice((int) row[3]);
		vo.setSellerName((String) row[4]);
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
