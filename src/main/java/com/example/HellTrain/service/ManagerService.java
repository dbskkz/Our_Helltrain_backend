package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ManagerDao;
import com.example.HellTrain.request.ManagerReq;
import com.example.HellTrain.response.BasicResponse;

@Service
public class ManagerService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private ManagerDao managerDao;
	
	//新增管理員
	@PostMapping("/addmana")
	public BasicResponse addManager(@RequestBody ManagerReq req) {
		managerDao.addManager(req.getEmail(), req.getName(), encoder.encode(req.getPassword()));
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}
	
	
	
	

}
