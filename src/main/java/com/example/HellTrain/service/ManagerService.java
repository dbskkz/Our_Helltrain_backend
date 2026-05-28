package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ManagerDao;
import com.example.HellTrain.response.BasicResponse;

@Service
public class ManagerService {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private ManagerDao managerDao;
	
	//新增管理員
	public BasicResponse addManager(String email, String name, String password) {
		managerDao.addManager(email, name, encoder.encode(password));
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}
	
	
	
	

}
