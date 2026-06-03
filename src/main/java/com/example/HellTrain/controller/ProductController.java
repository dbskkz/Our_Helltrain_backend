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
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.request.ProductReq;
import com.example.HellTrain.request.SearchProductReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetProductDataRes;
import com.example.HellTrain.service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
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
    
    @PostMapping("/addproduct")
    public BasicResponse addProduct(HttpSession session, @RequestBody ProductReq req) {
		//檢查登入session是否過期
    	Integer id=(Integer)session.getAttribute("user_id");
		if(id==null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(),//
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
    	return productService.addProduct(id, req);
    }
    
    @PostMapping("/upprod")
    public BasicResponse upProduct(HttpSession session,@RequestBody ProductReq req) {
		//檢查登入session是否過期
    	Integer id=(Integer)session.getAttribute("user_id");
		if(id==null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(),//
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
    	return productService.upProduct(id, req);
    }
}
