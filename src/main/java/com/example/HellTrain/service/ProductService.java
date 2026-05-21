package com.example.HellTrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetProductDataRes;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public GetProductDataRes getAllProductsInformation(){
		// 1. 直接去資料庫撈取全部的商品資料
		List<Product> productList = productDao.getAllData();
		
		// 2. 檢查 list 是否為空
		if (CollectionUtils.isEmpty(productList))
		{
			// 3. 若查無資料，回傳錯誤訊息
			return new GetProductDataRes(ReplyMessage.NO_DATA_FOUND.getCode(), ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		
		// 4. 回傳成功結果
		// 註：Response 需要擴充，多接收一個 data 參數
		return new GetProductDataRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage(), productList);
	}
}
