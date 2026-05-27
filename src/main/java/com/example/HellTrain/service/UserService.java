package com.example.HellTrain.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.component.VerificationCodeStore;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.ManagerDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.*;
import com.example.HellTrain.requeest.UserReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.LogInRes;
import com.example.HellTrain.vo.SetInfoVo;
import com.example.HellTrain.vo.VerifyVO;

@Service
public class UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Value("${file.upload.path}")
	private String uploadPath;

	private final String namePattern = "^[\\u4e00-\\u9fa5a-zA-Z\\\\s]{2,20}$";// 姓名2-20碼
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
	private VerificationCodeStore codeStore;

	@Autowired
	private JavaMailSender mailSender;
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
		if (!req.getName().matches(namePattern)) {
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
		// 過渡用
		String status = "正常";

		/* 建立帳號並傳入加密後的密碼 */
		userdao.addUser(req.getEmail(), req.getName(), encoder.encode(req.getPassword()), req.getPhone(),
				req.getLocation(), req.getSchool(), status);

		/* 產生驗證碼並寄信 */
		String code = codeStore.generate(req.getEmail());
		sendVerificationEmail(req.getEmail(), code);

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}

	/* 寄送驗證信 */
	private void sendVerificationEmail(String email, String code) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("【HellTrain】Email 驗證碼");
		mail.setText("您的驗證碼為：" + code + "\n10分鐘內有效，請勿告知他人。");
		mailSender.send(mail);
	}

	public BasicResponse verifyAndRegister(VerifyVO vo) {
		// 比對驗證碼
		boolean valid = codeStore.verify(vo.getEmail(), vo.getCode());
		if (!valid) {
			return new BasicResponse(ReplyMessage.INVALID_VERIFICATION_CODE.getCode(), //
					ReplyMessage.INVALID_VERIFICATION_CODE.getMessage());
		}

		// 驗證成功 → 更新 status
		userdao.updateverified(vo.getEmail(), LocalDate.now());

		// 銷毀驗證碼
		codeStore.remove(vo.getEmail());

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	public BasicResponse resendCode(String email) {
		// 確認這個 email 有註冊過
		if (userdao.selectCount(email) == 0) {
			return new BasicResponse(ReplyMessage.EMAIL_NOT_FOUND.getCode(), //
					ReplyMessage.EMAIL_NOT_FOUND.getMessage());
		}

		// 確認目前狀態是未驗證（已驗證的不需要重寄）
		User user = userdao.getAccount(email);
		if (!user.getStatus().equals("未驗證")) {
			return new BasicResponse(ReplyMessage.ACCOUNT_IS_VERIFICATION.getCode(), //
					ReplyMessage.ACCOUNT_IS_VERIFICATION.getMessage());
		}

		// 產生新驗證碼並寄信（舊的會被覆蓋）
		String code = codeStore.generate(email);
		sendVerificationEmail(email, code);
		return new BasicResponse(ReplyMessage.VERIFICATION_CODE_IS_SEND.getCode(), //
				ReplyMessage.VERIFICATION_CODE_IS_SEND.getMessage());
	}

	/* 登入，默認大部分為一般使用者，故先以使用者資料庫進行比對 */
	public LogInRes login(String email, String password) {
		String role = "";// 帳號身分

		/* 查詢帳號格式 */
		if (checkAccount(email, password) != null) {
			return (LogInRes) checkAccount(email, password);
		}

		// 比對使用者資料庫，找出email相符的資料
		User user = userdao.getAccount(email);
		// 檢查password是否一致
		if (user != null && encoder.matches(password, user.getPassword())) {
			role = "user";
			
			if (user.getVerified().plusYears(1).isBefore(LocalDate.now())) {
			    
			    // 這兩行就是全部了，不需要新方法
			    String code = codeStore.generate(user.getUserEmail());
			    sendVerificationEmail(user.getUserEmail(), code);
			    
			    return new LogInRes( ReplyMessage.VERIFICATION_CODE_IS_SEND.getCode(), //
						ReplyMessage.VERIFICATION_CODE_IS_SEND.getMessage());
			    
			}
		    
			return new LogInRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage(), role, user);
		}

		// 比對管理員資料庫
		Manager manager = managerdao.getByEmail(email);
		if (manager != null && encoder.matches(password, manager.getPassword())) {
			role = "manager";
			return new LogInRes(ReplyMessage.SUCCESS.getCode(), //
					ReplyMessage.SUCCESS.getMessage(), role, manager);
		}

		return new LogInRes(ReplyMessage.EMAIL_OR_PASSWORD_ERROR.getCode(), //
				ReplyMessage.EMAIL_OR_PASSWORD_ERROR.getMessage(), role, user);
	}

	/* 資料修改 */
	public BasicResponse setInfo(SetInfoVo vo) {
		
		User user = userdao.getAccount(vo.getEmail());

		if (!vo.getName().matches(namePattern)) {
			return new BasicResponse(ReplyMessage.PARAM_NAME_ERROR.getCode(), //
					ReplyMessage.PARAM_NAME_ERROR.getMessage());
		}

		if (!StringUtils.hasText(vo.getLocation())) {
			return new BasicResponse(ReplyMessage.LOCATION_IS_NULL.getCode(), //
					ReplyMessage.LOCATION_IS_NULL.getMessage());
		}

		if (!StringUtils.hasText(vo.getEmail())) {
			return new BasicResponse(ReplyMessage.SCHOOL_IS_NULL.getCode(), //
					ReplyMessage.SCHOOL_IS_NULL.getMessage());
		}

		if (vo.getDepartment() != null && vo.getDepartment().isBlank()) {
			return new BasicResponse(ReplyMessage.DEPARTMENT_IS_NULL.getCode(), //
					ReplyMessage.DEPARTMENT_IS_NULL.getMessage());
		}

		if (vo.getPhone() != null && checkphone(vo.getPhone()) != null) {
			return checkphone(vo.getPhone());
		}

		if (vo.getMsg() != null && vo.getMsg().length() > 200) {
			return new BasicResponse(ReplyMessage.MESSAGE_TOO_LONG.getCode(), //
					ReplyMessage.MESSAGE_TOO_LONG.getMessage());
		}

		// 頭像圖檔更新
		String imgPath = null;
		
		if(vo.isDeleteImg())//接收前端是否回傳刪除照片的旗標
		{
			imgPath=null;
		}
		// 1.確定有上傳圖片
		if (vo.getImg() != null && !vo.getImg().isEmpty()) {

			// 2. 檢查檔案類型
			List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
			if (!allowedTypes.contains(vo.getImg().getContentType())) {
				return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(), //
						ReplyMessage.FILE_FORMAT_ERROR.getMessage());
			}

			// 3. 檢查副檔名
			String originalFilename = vo.getImg().getOriginalFilename();
			if (originalFilename == null || !originalFilename.contains(".")) {
				return new BasicResponse(ReplyMessage.FILE_NAMEFORMAT_ERROR.getCode(), //
						ReplyMessage.FILE_NAMEFORMAT_ERROR.getMessage());
			}
			String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
			List<String> allowedExt = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp");
			if (!allowedExt.contains(ext)) {
				return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(), //
						ReplyMessage.FILE_FORMAT_ERROR.getMessage());
			}

			// 4. 檢查檔案大小（2MB）
			long maxSize = 2 * 1024 * 1024;
			if (vo.getImg().getSize() > maxSize) {
				return new BasicResponse(ReplyMessage.FILE_SIZE_ERROR.getCode(), //
						ReplyMessage.FILE_SIZE_ERROR.getMessage());
			}

			// 5. 儲存檔案（重新命名避免衝突）
			try {
				// 用 UUID 重新命名
				String newFilename = UUID.randomUUID().toString() + ext;

				// 確認資料夾存在，不存在就建立
				File dir = new File(uploadPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// 儲存
				vo.getImg().transferTo(new File(uploadPath + newFilename));
				imgPath = newFilename;

			} catch (IOException e) {
				return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(), //
						ReplyMessage.PLEASE_TRY_LATE.getMessage());
			}
		} else {
			// 沒上傳圖片 → 保留原本的
			imgPath = user.getImgPath();

		}
		
		userdao.setInfo(vo.getEmail(), vo.getName(), imgPath,
				vo.getLocation(), vo.getSchool(), vo.getDepartment(), vo.getPhone(), vo.getMsg());
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());

	}
	
	//更改密碼
	public BasicResponse changePassword(String email, String nowPad, String newPad) { 
		
		User user = userdao.getAccount(email);

		if(!nowPad.matches(padPattern) || !encoder.matches(nowPad, user.getPassword())) {
			return new BasicResponse(ReplyMessage.PARAM_PASSWORD_ERROR.getCode(), //
					ReplyMessage.PARAM_PASSWORD_ERROR.getMessage());
		}
		
		if (!newPad.matches(padPattern)) {
			return new BasicResponse(ReplyMessage.PARAM_PASSWORD_ERROR.getCode(), //
					ReplyMessage.PARAM_PASSWORD_ERROR.getMessage());
		}
		
		if(newPad.equals(nowPad)) {
			return new BasicResponse(ReplyMessage.PARAM_PASSWORD_ERROR.getCode(), //
					ReplyMessage.PARAM_PASSWORD_ERROR.getMessage());
		}
		
		//寫寫dao
		userdao.updatePad(email, encoder.encode(newPad));
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
	
}
