package com.example.HellTrain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.response.BasicResponse;

@Service
public class OrderService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;

	public BasicResponse addOrder(int buyerId, int productId) {// (Get)

		User user = userDao.getById(buyerId);
		// 查無此買家帳號
		if (user == null) {
			return new BasicResponse(ReplyMessage.EMAIL_NOT_FOUND.getCode(), //
					ReplyMessage.EMAIL_NOT_FOUND.getMessage());
		}
		
		Product product=productDao.getById(productId);
		//確定有商品
		if(product==null) {
			return new BasicResponse(ReplyMessage.PRODUCT_is_NOTFOUND.getCode(), //
					ReplyMessage.PRODUCT_is_NOTFOUND.getMessage());
		}
		//檢查商品是否為販售中
		if(product.getStatus()!="販售中")
		{
			return new BasicResponse(ReplyMessage.PRODUCT_is_UNSELL.getCode(), //
					ReplyMessage.PRODUCT_is_UNSELL.getMessage());
		}
		//檢查買賣家是否為同一個帳號
		if(product.getUserId()==user.getUserId()) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		LocalDate createDate=LocalDate.now();
		String status="等待回應中";
		//血寫dao
		//buyerId>productId>createDate>status
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}

}
