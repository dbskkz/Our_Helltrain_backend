package com.example.HellTrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.DeptGroupConst;
import com.example.HellTrain.requeest.SearchProductReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetProductDataRes;
import com.example.HellTrain.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/getInfo")
	public GetProductDataRes getAllProductsInformation() {
		return productService.getAllProductsInformation();
	}
	
	@GetMapping(value = "/search/price")
	public GetProductDataRes searchByPrice(@RequestParam("minPrice") int minPrice, @RequestParam("maxPrice") int maxPrice) {
		return productService.searchByPrice(minPrice, maxPrice);
	}
	
	@GetMapping(value = "/search/userId")
	public GetProductDataRes searchBySellerId(@RequestParam("userId") int userId) {
		return productService.searchBySellerId(userId);
	}
	
	@GetMapping(value = "/search/type")
	public GetProductDataRes searchByType(@RequestParam("type") List<String> type) {
		return productService.searchByType(type);
	}
	
	 // 年級
    @GetMapping("/search/grade")
    public GetProductDataRes searchByGrade(@RequestParam("grade") String grade) {
        return productService.searchByGrade(grade);
    }

    // 關鍵字
//    @GetMapping("/search/type")
//    public GetProductDataRes searchByKeyword(@RequestParam String keyword) {
//        return productService.searchByKeyword(keyword);
//    }

    // ★ 複合搜尋（前端主要呼叫這個）
    // POST /product/search
    // Body: { "keyword":"微積分", "minPrice":0, "maxPrice":500, "grades":["大一"], "types":["教科書"] }
    @PostMapping("/search")
    public GetProductDataRes search(@RequestBody SearchProductReq req) {
        return productService.searchByConditions(req);
    }
	
    // 在 Controller 加一個端點讓前端拿清單 ( 但前端已經有清單了QQ
    @GetMapping("/deptGroups")
    public List<String> getDeptGroups() {
        return DeptGroupConst.ALL_GROUPS;
    }
}
