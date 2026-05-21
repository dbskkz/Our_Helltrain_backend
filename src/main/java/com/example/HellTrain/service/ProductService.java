package com.example.HellTrain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.response.GetProductDataRes;
import com.example.HellTrain.vo.ProductVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	private ObjectMapper mapper = new ObjectMapper();
	 
	public GetProductDataRes getAllProductsInformation(){
		// 1. 直接去資料庫撈取全部的商品資料
		List<Product> productList = productDao.getAllData();
		
		// 2. 檢查 list 是否為空
		if (CollectionUtils.isEmpty(productList))
		{
			
			return new GetProductDataRes(ReplyMessage.NO_DATA_FOUND.getCode(), ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		
		// 3. 資料轉換
		List<ProductVo> voList = new ArrayList<>();
		
		for(Product item: productList) {
			try {
				List<String> imgPathList = null;
				List<String> typeList = null;
				List<String> locationList = null;
				
				if(item.getImgPath() != null && !item.getImgPath().isEmpty())
				{
					imgPathList = mapper.readValue(item.getImgPath(), new TypeReference<List<String>>() {});
				} else {
                    imgPathList = new ArrayList<>(); //避免 mapper.readValue() 收到 null 或空字串時噴錯。
                }
				
				if(item.getLocation() != null && !item.getLocation().isEmpty())
				{
					locationList = mapper.readValue(item.getLocation(), new TypeReference<List<String>>() {});
				} else {
					locationList = new ArrayList<>();
				}
				
				if(item.getType() != null && !item.getType().isEmpty())
				{
					typeList = mapper.readValue(item.getType(), new TypeReference<List<String>>() {});
				} else {
					typeList = new ArrayList<>();
				}
				
				ProductVo vo = new ProductVo(
	                    item.getProductId(), item.getUserId(),
	                    item.getProductName(), item.getDescription(),
	                    item.getPrice(), imgPathList, typeList, 
	                    item.getShelfDate(), item.getProductCondition(),
	                    item.getStatus(), item.getStock(), 
	                    item.getGrade(),locationList
	                );

	                voList.add(vo);
				
			} catch (Exception e) {
				return new GetProductDataRes(ReplyMessage.PRODUCT_PARSE_ERROR.getCode(), ReplyMessage.PRODUCT_PARSE_ERROR.getMessage());
			}
		}
		
		// 4. 回傳成功結果
		// 註：Response 需要擴充，多接收一個 data 參數
		return new GetProductDataRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage(), voList);
	}
}
