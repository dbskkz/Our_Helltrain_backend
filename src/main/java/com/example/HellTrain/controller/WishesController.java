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

import com.example.HellTrain.request.WishReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetWishesRes;
import com.example.HellTrain.service.WishService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/wish")
public class WishesController {

	@Autowired
	private WishService wishService;
	
	@PostMapping(value = "/insert")
	public BasicResponse addWish(@RequestParam("userId") int userId, @RequestBody WishReq req) {
		return wishService.addWish(userId, req);
	}
	
	@GetMapping(value = "/query/all")
	public GetWishesRes getAllWishes() {
		return wishService.getAllWishes();
	}
	
	@GetMapping(value = "/query/school")
	public GetWishesRes getWishesBySchool(@RequestParam("school") String school) {
		return wishService.getWishesBySchool(school);
	}
	
	
}
