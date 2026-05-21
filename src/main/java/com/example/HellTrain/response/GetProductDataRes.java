package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.ProductVo;

public class GetProductDataRes extends BasicResponse{
	private List<ProductVo> productList;

	public List<ProductVo> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductVo> productList) {
		this.productList = productList;
	}

	public GetProductDataRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetProductDataRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public GetProductDataRes(List<ProductVo> productList) {
		super();
		this.productList = productList;
	}

	public GetProductDataRes(int statusCode, String message, List<ProductVo> productList) {
		super(statusCode, message);
		this.productList = productList;
	}
	
}
