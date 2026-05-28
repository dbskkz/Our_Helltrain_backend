package com.example.HellTrain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.ReportStatus;
import com.example.HellTrain.constant.ReportType;
import com.example.HellTrain.dao.OrderDao;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.Order;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.request.ReportReq;
import com.example.HellTrain.response.BasicResponse;

@Service
public class ReportService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDao orderDao;

	public BasicResponse addReport(String email, ReportReq req) {

		User complainant = userDao.getAccount(email);
		int complainantId = complainant.getUserId();// 確認現在登入者的身分(檢舉人)

		Order order = orderDao.getOrderById(req.getOrderId());
		
		if(order==null) {
			return new BasicResponse(ReplyMessage.NOT_FOUND_DATE.getCode(), //
					ReplyMessage.NOT_FOUND_DATE.getMessage());
		}
		// product 抓賣家資料
		Product product = productDao.findByProductId(order.getProductId());

		// 確認檢舉人是否為買家或賣家
		boolean isBuyer = complainantId == order.getBuyerId();
		boolean isSales = complainantId == product.getUserId();

		if (!isBuyer && !isSales) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		
		int realAccuse=isBuyer?product.getUserId() : order.getBuyerId();
		if(req.getAccusedId()!=realAccuse)
		{
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}
		
		if(req.getDescription()==null)
		{
			return new BasicResponse(ReplyMessage.DESCRIPTION_IS_NULL.getCode(), //
					ReplyMessage.DESCRIPTION_IS_NULL.getMessage());
		}
		
		//type is null
		if(StringUtils.hasText(req.getType())) {
			return new BasicResponse(ReplyMessage.REPORTTYPE_IS_NULL.getCode(), //
					ReplyMessage.REPORTTYPE_IS_NULL.getMessage());
		}
		//type is error
		if(!req.getType().equals(ReportType.Product.getMessage())||
				!req.getType().equals(ReportType.Product.getMessage())) {//
			return new BasicResponse(ReplyMessage.REPORTTYPE_ERROR.getCode(), //
					ReplyMessage.REPORTTYPE_ERROR.getMessage());
		}

		LocalDate reportDate = LocalDate.now();

		String status = ReportStatus.Handle.getMessage();
		
		//reportDao insert資料

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

}
