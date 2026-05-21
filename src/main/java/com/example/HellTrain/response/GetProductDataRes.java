package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.entity.Product;

public class GetProductDataRes extends BasicResponse{
	private List<Product> productList;

	public GetProductDataRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	// G&N
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	
	// Constructor
	public GetProductDataRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public GetProductDataRes(List<Product> productList) {
		super();
		this.productList = productList;
	}

	public GetProductDataRes(int statusCode, String message, List<Product> productList) {
		super(statusCode, message);
		this.productList = productList;
	}
	
	

}
