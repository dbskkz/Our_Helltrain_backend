package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.OrderService;
import com.example.HellTrain.vo.ChangeOrderStatusVo;
import com.example.HellTrain.vo.OrderVo;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "https://lacolhost:4200")
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(value  = "/addOrder")
	public BasicResponse addOrder(HttpSession session, @RequestBody OrderVo vo) {
		String email = (String) session.getAttribute("user_email");
		if (email == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.addOrder(email,vo);
	}

	// 賣家同意請求，請求按鈕點下後同商品的其它等待中會強制轉為取消
	@PostMapping(value = "/acceptOrder")
	public BasicResponse acceptOrder(HttpSession session, @RequestBody ChangeOrderStatusVo vo) {
		String email = (String) session.getAttribute("user_email");
		if (email == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.acceptOrder(email,vo);
	}

	// 買家主動取消訂單(vo.email是買家的)
	@PostMapping(value = "/canaelOrder")
	public BasicResponse canaelOrder(HttpSession session, @RequestBody ChangeOrderStatusVo vo) {
		String email = (String) session.getAttribute("user_email");
		if (email == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		return orderService.canaelOrder(email,vo);
	}

	//確認雙方是否點擊確認
	@PostMapping(value = "/delivery")
	public BasicResponse checkDelivery(HttpSession session, @RequestBody ChangeOrderStatusVo vo) {
		String email = (String) session.getAttribute("user_email");
		if (email == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		
		return orderService.checkDelivery(email,vo);
	}
}
