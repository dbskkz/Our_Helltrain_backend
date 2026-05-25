package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.requeest.UserReq;
import com.example.HellTrain.response.BasicResponse;

@Service
public class UserService {
	
	private 	BCryptPasswordEncoder encode=new BCryptPasswordEncoder();
	
	@Autowired
	private UserDao userdao;
	
	/*insert 先傳入除了id以外的值，再用getMaxId抓id*/
	public BasicResponse addUser(UserReq req) {
		
		/*資料檢查 */
		
		//檢查email是否以.edu.tw結尾(以endwith檢查)
//		if() {
//			
//		}
//		確認email是否被註冊過
//		if (userdao.selectCount(req.getEmail())==1) {
//			return new BasicResponse(ReplyMessage.EMAIL_HAS_FOUND.getCode(), //
//					ReplyMessage.EMAIL_HAS_FOUND.getMessage());
//		}
		
		//抓uaerId
		
		
		/*建立帳號並傳入加密後的密碼*/
		
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(),
				ReplyMessage.SUCCESS.getMessage());
	}

}
