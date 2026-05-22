package com.example.HellTrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
}
