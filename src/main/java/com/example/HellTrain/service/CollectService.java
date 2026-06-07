package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ProductStatus;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.UserStatus;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.response.BasicResponse;

@Service
public class CollectService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;

	private BasicResponse checkUser(int userId) {
		User user = userDao.getById(userId);
		if (user == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		if (!user.getStatus().equals(UserStatus.Normal.getMessage())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		return null;
	}

	private BasicResponse checkProduct(int productId) {
		Product product = productDao.findByProductId(productId);
		if (product == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		if (!product.getStatus().equals(ProductStatus.OnSale.getMassage())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		return null;
	}

	public BasicResponse addCollect(int userId, int productId) {
		//檢查使用者是否存在且狀態是否為正常
		if (checkUser(userId) != null) {
			return checkUser(userId);
		}
		//檢查商品是否存在且是否為販售狀態
		if(checkProduct(productId)!=null) {
			return checkProduct(productId);
		}
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}

}
