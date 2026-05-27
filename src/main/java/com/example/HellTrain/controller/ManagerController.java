package com.example.HellTrain.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.ManagerService;

@RestController
@CrossOrigin
@RequestMapping("/manager")
public class ManagerController {
	
	private ManagerService managerService;
	
	public BasicResponse addManager(String email, String name, String password) {
		return managerService.addManager(email, name, password);
	}

}
