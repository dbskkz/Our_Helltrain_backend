package com.example.HellTrain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ManagerDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.*;
import com.example.HellTrain.requeest.UserReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.LogInRes;

@Service
public class UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private final String accPattern = "^[\\u4e00-\\u9fa5a-zA-Z\\\\s]{2,50}$";// 姓名2-50碼
	private final String padPattern = "^[a-zA-Z0-9!@#$%^&*\\\\-_]+${8,}";// 密碼至少8碼
	private final String phonepattern = "0\\d{1,3}-\\d{7,8}";

	private BasicResponse checkAccount(String email, String password) {
		if (!email.endsWith(".edu.tw")) {
			return new BasicResponse(ReplyMessage.EMAIL_ISNOT_SCHOOL.getCode(), //
					ReplyMessage.EMAIL_ISNOT_SCHOOL.getMessage());
		}

		if (!password.matches(padPattern)) {
			return new BasicResponse(ReplyMessage.PARAM_PASSWORD_ERROR.getCode(), //
					ReplyMessage.PARAM_PASSWORD_ERROR.getMessage());
		}
		return null;
	}

	private BasicResponse checkphone(String phone) {
		if (!phone.matches(phonepattern)) {
			return new BasicResponse(ReplyMessage.PARAM_PHONE_ERROR.getCode(), //
					ReplyMessage.PARAM_PHONE_ERROR.getMessage());
		}
		return null;
	}

	@Autowired
	private UserDao userdao;

	@Autowired
	private ManagerDao managerdao;

	/* insert */
	public BasicResponse addUser(UserReq req) {

		/* 資料檢查 */
		if (!StringUtils.hasText(req.getSchool())) {
			return new BasicResponse(ReplyMessage.SCHOOL_IS_NULL.getCode(), //
					ReplyMessage.SCHOOL_IS_NULL.getMessage());
		}

		if (!StringUtils.hasText(req.getLocation())) {
			return new BasicResponse(ReplyMessage.LOCATION_IS_NULL.getCode(), //
					ReplyMessage.LOCATION_IS_NULL.getMessage());
		}
		// 檢查姓名格式
		if (!req.getName().matches(accPattern)) {
			return new BasicResponse(ReplyMessage.PARAM_NAME_ERROR.getCode(), //
					ReplyMessage.PARAM_NAME_ERROR.getMessage());
		}
		// 檢查email及password
		if (checkAccount(req.getEmail(), req.getPassword()) != null) {
			return checkAccount(req.getEmail(), req.getPassword());
		}

		// 檢查電話號碼格式
		// 當phone有值，且與檢查函式中的格式不相符
		if (req.getPhone() != null && checkphone(req.getPhone()) != null) {
			return checkphone(req.getPhone());
		}

		// 確認email是否被註冊過
		if (userdao.selectCount(req.getEmail()) == 1) {
			return new BasicResponse(ReplyMessage.EMAIL_HAS_FOUND.getCode(), //
					ReplyMessage.EMAIL_HAS_FOUND.getMessage());
		}
		// 設定status，每個使用者一開始都是'未驗證'......用enum?
		String status = "未驗證";
		/* 以school查詢SchoolId，如取消School資料表則不用 */

		/* 建立帳號並傳入加密後的密碼 */
		userdao.addUser(req.getEmail(), req.getName(), encoder.encode(req.getPassword()), req.getPhone(),
				req.getLocation(), req.getSchool(), status);

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}

	/* 登入，默認大部分為一般使用者，故先以使用者資料庫進行比對 */
	public LogInRes Login(String email, String password) {
		String role = "";// 帳號身分

		/* 查詢帳號格式 */
		if (checkAccount(email, password) != null) {
			return (LogInRes) checkAccount(email, password);
		}

		// 比對使用者資料庫，找出email相符的資料
		User user = userdao.getAccount(email);
		//檢查password是否一致
		if (user != null && encoder.matches(password, user.getPassword())) {
			role = "user";
			return new LogInRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage(), role, user);
		}

		// 比對管理員資料庫
		Manager manager = managerdao.getByEmail(email);
		if (manager != null&& encoder.matches(password,manager.getPassword())) {
			role = "manager";
			return new LogInRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage(), role, manager);
		}

		return new LogInRes(ReplyMessage.EMAIL_OR_PASSWORD_ERROR.getCode(), //
				ReplyMessage.EMAIL_OR_PASSWORD_ERROR.getMessage(), role, user);
	}

}
