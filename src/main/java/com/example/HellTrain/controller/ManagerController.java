package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.request.ManagerReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.ManagerService;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/add")
	public BasicResponse addManager(@RequestBody ManagerReq req) {
		return managerService.addManager(req);
	}

}
