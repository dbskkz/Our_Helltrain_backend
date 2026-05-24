package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.requeest.UserReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.LogInRes;
import com.example.HellTrain.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/insert")
	public BasicResponse addUser(@RequestBody UserReq req) {
		return userService.addUser(req);
	}
	
	@GetMapping(value = "/login")
	public LogInRes Login(@RequestParam("email") String email,@RequestParam("password") String password) {
		return userService.Login(email, password);
	}

}
