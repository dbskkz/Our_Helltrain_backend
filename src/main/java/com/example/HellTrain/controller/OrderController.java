package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.request.GoodLevelReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.OrderRes;
import com.example.HellTrain.service.OrderService;
import com.example.HellTrain.vo.ChangeOrderStatusVo;
import com.example.HellTrain.vo.OrderVo;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(value  = "/addOrder")
	public BasicResponse addOrder(HttpSession session, @RequestBody OrderVo vo) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.addOrder(id,vo);
	}

	// 賣家同意請求，請求按鈕點下後同商品的其它等待中會強制轉為取消
	@PostMapping(value = "/acceptOrder")
	public BasicResponse acceptOrder(HttpSession session, @RequestBody ChangeOrderStatusVo vo) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.acceptOrder(id,vo);
	}

	// 買家主動取消訂單
	@PostMapping(value = "/canaelOrder")
	public BasicResponse canaelOrder(HttpSession session, @RequestBody ChangeOrderStatusVo vo) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}

		return orderService.canaelOrder(id,vo);
	}

	//確認雙方是否點擊確認
	@PostMapping(value = "/delivery")
	public BasicResponse checkDelivery(HttpSession session,//
			@RequestBody ChangeOrderStatusVo vo) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.checkDelivery(id,vo);
	}
	
	//交易雙方給予評價
	@PostMapping(value = "/giveLevel")
	public BasicResponse giveLevel(HttpSession session, @RequestBody GoodLevelReq req) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.giveLevel(id, req);
	}
	
	//取得使用者的所有訂單
	@GetMapping(value = "/getUserOrder")
	public OrderRes getAllOrder(HttpSession session) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new OrderRes(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.getAllOrder(id);
	}
	
	//取得單一商品的所有訂單(賣家檢索同商品的申請用)
	@GetMapping(value = "/getProductOrder")
	public OrderRes getProductAllOrder(HttpSession session ,@RequestParam("productId") int productId) {
		Integer id = (Integer) session.getAttribute("user_id");
		if (id == null) {
			return new OrderRes(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return orderService.getProductAllOrder(id, productId);
	}

}
