package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping(value = "/getInfo")
	public BasicResponse getAllProductsInformation() {
		return productService.getAllProductsInformation();
	}
}
