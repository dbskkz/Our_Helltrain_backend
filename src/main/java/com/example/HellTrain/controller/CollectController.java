package com.example.HellTrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.CollectService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/collect")
public class CollectController {

	@Autowired
	public CollectService collectService;

	private BasicResponse checkSession(Integer id) {
		if (id == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return null;
	}

	@PostMapping(value = "/addcollect")
	public BasicResponse addCollect(HttpSession session,@RequestBody int productId) {
		// 檢查登入session是否過期
		Integer id = (Integer) session.getAttribute("user_id");
		if (checkSession(id) != null) {
			return checkSession(id);
		}

		return collectService.addCollect(id, productId);
	}
	
	@GetMapping(value = "/delete")
	public BasicResponse clearCollect(HttpSession session,//
			@RequestParam("collectId") List<Integer> collectId) {
		// 檢查登入session是否過期
		Integer id = (Integer) session.getAttribute("user_id");
		if (checkSession(id) != null) {
			return checkSession(id);
		}
		return collectService.clearCollect(id, collectId);
	}
}
