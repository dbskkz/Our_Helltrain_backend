package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.WishDao;
import com.example.HellTrain.response.BasicResponse;

@Service
public class WishService {
	@Autowired
	private WishDao wishDao;
	

	// POST : Insert a new wishes
	public BasicResponse addWishes() {
		
		// 參數檢查
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.SUCCESS.getMessage());
	}
	
	// QUERY 1 : Query all wishes
	
	// QUERY 2 : Query wishes of the target school
}
