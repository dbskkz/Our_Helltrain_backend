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

	// 私有方法 : 將資料抽成前端接受的格式
	private GetProductDataRes convertToFrontEndFormat(List<Product> productList) {
		// 檢查 list 是否為空
		if (CollectionUtils.isEmpty(productList)) {

			return new GetProductDataRes(ReplyMessage.NO_DATA_FOUND.getCode(), ReplyMessage.NO_DATA_FOUND.getMessage());
		}

		// 資料轉換
		List<ProductVo> voList = new ArrayList<>();

		for (Product item : productList) {
			try {
				List<String> imgPathList = null;
				List<String> typeList = null;
				List<String> locationList = null;

				if (item.getImgPath() != null && !item.getImgPath().isEmpty()) {
					imgPathList = mapper.readValue(item.getImgPath(), new TypeReference<List<String>>() {
					});
				} else {
					imgPathList = new ArrayList<>(); // 避免 mapper.readValue() 收到 null 或空字串時噴錯。
				}

				if (item.getLocation() != null && !item.getLocation().isEmpty()) {
					locationList = mapper.readValue(item.getLocation(), new TypeReference<List<String>>() {
					});
				} else {
					locationList = new ArrayList<>();
				}

				if (item.getType() != null && !item.getType().isEmpty()) {
					typeList = mapper.readValue(item.getType(), new TypeReference<List<String>>() {
					});
				} else {
					typeList = new ArrayList<>();
				}

				ProductVo vo = new ProductVo(item.getProductId(), item.getUserId(), item.getProductName(),
						item.getDescription(), item.getPrice(), imgPathList, typeList, item.getShelfDate(),
						item.getProductCondition(), item.getStatus(), item.getStock(), item.getGrade(), locationList);

				voList.add(vo);

			} catch (Exception e) {
				return new GetProductDataRes(ReplyMessage.PRODUCT_PARSE_ERROR.getCode(),
						ReplyMessage.PRODUCT_PARSE_ERROR.getMessage());
			}
		}
		return new GetProductDataRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage(), voList);
	}

	// 1. 直接去資料庫撈取全部的商品資料
	public GetProductDataRes getAllProductsInformation() {
		List<Product> productList = productDao.getAllData();
		return convertToFrontEndFormat(productList);
	} // GetAllData結束

	// 2. 以價位區間搜尋
	public GetProductDataRes searchByPrice(int minPrice, int maxPrice) {
		List<Product> productList = productDao.findByPriceBetween(minPrice, maxPrice);
		return convertToFrontEndFormat(productList);
	}

	// 3. 以賣家 ID 搜尋
	public GetProductDataRes searchBySellerId(int userId) {
		if (userId < 1) {
			return new GetProductDataRes(ReplyMessage.USER_ID_ERR.getCode(), ReplyMessage.USER_ID_ERR.getMessage());
		}
		List<Product> productList = productDao.findByUserId(userId);
		return convertToFrontEndFormat(productList);
	}

	// 4. 以類型搜尋
	public GetProductDataRes searchByType(List<String> type) {

		List<Product> productList = null;

		for (String types : type) {
			productList = productDao.findByType(types);
		}

		return convertToFrontEndFormat(productList);
	}
	
	// 5. 以年級搜尋
//	public GetProductDataRes searchByGrade(String grade) {
//		
//	
//	}

}
