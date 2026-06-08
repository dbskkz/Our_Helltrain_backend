package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ProductStatus;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.UserStatus;
import com.example.HellTrain.dao.CollectDao;
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
	@Autowired
	private CollectDao collectDao;

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
		// 檢查使用者是否存在且狀態是否為正常
		if (checkUser(userId) != null) {
			return checkUser(userId);
		}
		// 檢查商品是否存在且是否為販售狀態
		if (checkProduct(productId) != null) {
			return checkProduct(productId);
		}
		// 檢查是否收藏過此商品
		if (collectDao.checkProduct(userId, productId) > 0) {
			return new BasicResponse(ReplyMessage.PRODUCT_IS_COLLECT.getCode(), //
					ReplyMessage.PRODUCT_IS_COLLECT.getMessage());
		}
		// 抓各User的collectId
		// 當使用者沒有收藏時回傳值為0，但此時為第一筆Id為1;第二筆時回傳值會是1，但要寫入DB的Id值是2
		int collectId = collectDao.catchCollectId(userId) + 1;

		// 寫入collect資料表
		collectDao.addCollect(userId, collectId, productId);

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}

	public BasicResponse clearCollect(int id, int collectId) {
		if (checkUser(id) != null) {
			return null;
		}
		//確認是否有這筆收藏
		if (collectDao.checkcollect(collectId) == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		

		/* 點選刪除 */
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

}
