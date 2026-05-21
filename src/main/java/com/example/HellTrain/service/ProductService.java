package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.response.BasicResponse;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	public BasicResponse getAllProductsInformation(){
		
		// 1. 檢查資料庫中是否有商品
		if(productDao.findData() == 0) {
			// 2. 若查無資料，回傳錯誤訊息
//			System.out.println("NNNN"); // 測試用
		}
//		else
//		{
//			System.out.println(productDao.findData()); // 測試，成功撈到資料
//		}
		
		// 3. 建立 response list
		
		// 4. 將每筆 Entity 轉成 Response DTO
		
		// 5. 將 DTO 加入 list
		
		// 6. 回傳成功結果與資料
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}
}
