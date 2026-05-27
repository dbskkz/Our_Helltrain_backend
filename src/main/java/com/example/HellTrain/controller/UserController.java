package com.example.HellTrain.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.requeest.UserReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.LogInRes;
import com.example.HellTrain.service.UserService;
import com.example.HellTrain.vo.ChangePasswordVo;
import com.example.HellTrain.vo.SetInfoVo;
import com.example.HellTrain.vo.VerifyVO;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/insert")
	public BasicResponse addUser(@RequestBody UserReq req) {
		return userService.addUser(req);
	}
	
	@GetMapping(value = "/login")
	public LogInRes login(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session) {
		
		LogInRes res = userService.login(email,password);
		//確認登入成功後才將資料(email)儲存
		if (res.getStatusCode() == 200) {
			//設定session的K,V
			session.setAttribute("user_email", email);
			//統一設定session的存活時間，預設為30分鐘，如user跟server還有在互動，則session效期延長(從新計算存活時間)
			//設定效期30天
			session.setMaxInactiveInterval(2592000);// 單位是秒，0或負數表session永不失效
		}
		return userService.login(email, password);
	}
	
	//使用者輸入驗證碼後按下發送所需要街的資料回傳API(初次及後續驗證皆是這個)
	@PostMapping(value = "/verify")
	public BasicResponse verifyAndRegister(@RequestBody VerifyVO vo) {
		return userService.verifyAndRegister(vo);
	}
	
	//重新發送驗證碼
	@PostMapping(value = "/resend")
	public BasicResponse resendCode(@RequestBody  Map<String, String> body) {
		String email=body.get("email");
		return userService.resendCode(email);
	}
	
	//修改密碼以外的個人資訊
	@PostMapping(value = "/setInfo")
	public BasicResponse setInfo(HttpSession session, @RequestBody SetInfoVo vo) {
		String email=(String)session.getAttribute("user_email");
		if(email==null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(),//
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return userService.setInfo(vo);
	}
	
	//修改密碼
	@PostMapping(value = "/changePassword")
	public BasicResponse changePassword(HttpSession session, @RequestBody ChangePasswordVo vo) { 
		
//		String email=(String)session.getAttribute("user_email");
//		if(email==null) {
//			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(),//
//					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
//		}
		return userService.changePassword(vo.getEmail(),vo.getNowPad(),vo.getNewPad());
	}

}
