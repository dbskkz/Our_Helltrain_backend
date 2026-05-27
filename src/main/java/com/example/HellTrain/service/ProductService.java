package com.example.HellTrain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.request.SearchProductReq;
import com.example.HellTrain.response.GetProductDataRes;
import com.example.HellTrain.vo.ProductVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	private ObjectMapper mapper = new ObjectMapper();

	// ── 私有工具方法 ──────────────────────────────────────────
	
	// JSON 字串轉 List<String>，null 或空字串回傳空 List
    private List<String> parseJsonList(String json) {
        if (json == null || json.isBlank()) return new ArrayList<>();
        try {
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    // 判斷 JSON 字串欄位是否包含 targets 其中任一個值
    // 用於 type（因為 DB 存的是 JSON 字串，無法直接用 SQL WHERE 篩多選）
    private boolean jsonContainsAny(String json, List<String> targets) {
        if (json == null || json.isBlank() || CollectionUtils.isEmpty(targets)) return false;
        List<String> values = parseJsonList(json);
        return values.stream().anyMatch(targets::contains);
    }
    
    // Product 轉 ProductVo，給 convertToFrontEndFormat 用
    private ProductVo toVo(Product item) throws Exception {
        return new ProductVo(
            item.getProductId(),
            item.getUserId(),
            item.getProductName(),
            item.getDescription(),
            item.getPrice(),
            parseJsonList(item.getImgPath()),
            parseJsonList(item.getType()),
            item.getShelfDate(),
            item.getProductCondition(),
            item.getStatus(),
            item.getGrade(),
            parseJsonList(item.getLocation()),
            parseJsonList(item.getDeptGroup())
        );
    }
    
//	// 私有方法 : 將資料抽成前端接受的格式
//	private GetProductDataRes convertToFrontEndFormat(List<Product> productList) {
//		// 檢查 list 是否為空
//		if (CollectionUtils.isEmpty(productList)) {
//
//			return new GetProductDataRes(ReplyMessage.NO_DATA_FOUND.getCode(), ReplyMessage.NO_DATA_FOUND.getMessage());
//		}
//
//		// 資料轉換
//		List<ProductVo> voList = new ArrayList<>();
//
//		for (Product item : productList) {
//			try {
//				List<String> imgPathList = null;
//				List<String> typeList = null;
//				List<String> locationList = null;
//
//				if (item.getImgPath() != null && !item.getImgPath().isEmpty()) {
//					imgPathList = mapper.readValue(item.getImgPath(), new TypeReference<List<String>>() {
//					});
//				} else {
//					imgPathList = new ArrayList<>(); // 避免 mapper.readValue() 收到 null 或空字串時噴錯。
//				}
//
//				if (item.getLocation() != null && !item.getLocation().isEmpty()) {
//					locationList = mapper.readValue(item.getLocation(), new TypeReference<List<String>>() {
//					});
//				} else {
//					locationList = new ArrayList<>();
//				}
//
//				if (item.getType() != null && !item.getType().isEmpty()) {
//					typeList = mapper.readValue(item.getType(), new TypeReference<List<String>>() {
//					});
//				} else {
//					typeList = new ArrayList<>();
//				}
//
//				ProductVo vo = new ProductVo(item.getProductId(), item.getUserId(), item.getProductName(),
//						item.getDescription(), item.getPrice(), imgPathList, typeList, item.getShelfDate(),
//						item.getProductCondition(), item.getStatus(), item.getStock(), item.getGrade(), locationList);
//
//				voList.add(vo);
//
//			} catch (Exception e) {
//				return new GetProductDataRes(ReplyMessage.PRODUCT_PARSE_ERROR.getCode(),
//						ReplyMessage.PRODUCT_PARSE_ERROR.getMessage());
//			}
//		}
//		return new GetProductDataRes(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage(), voList);
//	}
    
 // List<Product> 轉成前端格式
    private GetProductDataRes convertToFrontEndFormat(List<Product> productList) {
        if (CollectionUtils.isEmpty(productList)) {
            return new GetProductDataRes(
                ReplyMessage.NO_DATA_FOUND.getCode(),
                ReplyMessage.NO_DATA_FOUND.getMessage()
            );
        }

        List<ProductVo> voList = new ArrayList<>();
        for (Product item : productList) {
            try {
                voList.add(toVo(item));
            } catch (Exception e) {
                return new GetProductDataRes(
                    ReplyMessage.PRODUCT_PARSE_ERROR.getCode(),
                    ReplyMessage.PRODUCT_PARSE_ERROR.getMessage()
                );
            }
        }
        return new GetProductDataRes(
            ReplyMessage.SUCCESS.getCode(),
            ReplyMessage.SUCCESS.getMessage(),
            voList
        );
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
	public GetProductDataRes searchByType(List<String> types) {
        if (CollectionUtils.isEmpty(types)) {
            return new GetProductDataRes(
                ReplyMessage.INVALID_PARAM.getCode(),
                ReplyMessage.INVALID_PARAM.getMessage()
            );
        }
        List<Product> filtered = productDao.getAllData().stream()
            .filter(p -> jsonContainsAny(p.getType(), types))
            .collect(Collectors.toList());
        return convertToFrontEndFormat(filtered);
    }
	
	// 5. 以年級搜尋
	public GetProductDataRes searchByGrade(String grade) {
        if (grade == null || grade.isBlank()) {
            return new GetProductDataRes(
                ReplyMessage.INVALID_PARAM.getCode(),
                ReplyMessage.INVALID_PARAM.getMessage()
            );
        }
        return convertToFrontEndFormat(productDao.findByGrade(grade));
    }
	
	// ── 6. 關鍵字搜尋 ─────────────────────────────────────────
//    public GetProductDataRes searchByKeyword(String keyword) {
//        if (keyword == null || keyword.isBlank()) {
//            return getAllProductsInformation();
//        }
//        return convertToFrontEndFormat(productDao.findByKeyword(keyword.trim()));
//    }
    
 // ── 7. 複合搜尋（前端主要呼叫這個）──────────────────────
    public GetProductDataRes searchByConditions(SearchProductReq req) {
        if (req == null) {
            return getAllProductsInformation();
        }

        // Step 1：DB 層處理 keyword + price（效率高的先在 DB 過濾）
        String keyword = (req.getKeyword() != null && !req.getKeyword().isBlank())
                         ? req.getKeyword().trim() : null;

        List<Product> result = productDao.findByMultiConditions(
            keyword,
            req.getMinPrice(),
            req.getMaxPrice()
        );

        // Step 2：Java 層過濾 grades（DB 欄位是普通字串，可以直接比對）
        if (!CollectionUtils.isEmpty(req.getGrades())) {
            result = result.stream()
                .filter(p -> req.getGrades().contains(p.getGrade()))
                .collect(Collectors.toList());
        }

        // Step 3：Java 層過濾 conditions（productCondition 是普通字串）
        if (!CollectionUtils.isEmpty(req.getConditions())) {
            result = result.stream()
                .filter(p -> req.getConditions().contains(p.getProductCondition()))
                .collect(Collectors.toList());
        }

        // Step 4：Java 層過濾 types（DB 存 JSON 字串，必須在 Java 解析後比對）
        if (!CollectionUtils.isEmpty(req.getTypes())) {
            result = result.stream()
                .filter(p -> jsonContainsAny(p.getType(), req.getTypes()))
                .collect(Collectors.toList());
        }
        
        // Step 5：Java 層過濾 deptGroups（DB 存 JSON 字串，同 type 處理方式）
        if (!CollectionUtils.isEmpty(req.getDeptGroups())) {
            result = result.stream()
                .filter(p -> jsonContainsAny(p.getDeptGroup(), req.getDeptGroups()))
                .collect(Collectors.toList());
        }

        return convertToFrontEndFormat(result);
    }

}
