package com.example.HellTrain.constant;

public enum ReplyMessage {
	SUCCESS(200, "Success!!"),
	NO_DATA_FOUND(404, "No data found"),
	USER_ID_ERR(400, "The user id is invalid !"),
<<<<<<< HEAD
=======
	CLASSMATE_NO_FOUND(404, "There is no member in the school. QQ"),
>>>>>>> THE-MR
	PRODUCT_ID_ERR(400, "The product id is invalid !"),
	PRODUCT_PARSE_ERROR(400, "Product data format parse error !"),//
	INVALID_PARAM(400, "The parameter cannot be empty !"),//
	//
	PRODUCT_NAME_NULL(400, "The product name cannot be null !"),//沒有商品名稱
	PRICE_ERROR(400, "Price cannot less then one dollar"),//價格異常
	TYPE_ERROR(400, "Price cannot less then one dollar"),//價格異常
	CONDITION_IS_NULL(400, "Product condition not described"),//未描述商品狀況
	NO_DESIGNTED_LOCATION(400, "No designated trading location"),//未指定交易地點
	NO_DEPTGROUP(400, "No designated trading deptgroup"),//沒有指定學群
	GRADE_IS_NULL(400,"Grade cannot be null"),//
	//管理員
	WELCOME_MANAGER(201,"Manager is login"),//判斷登入者為管理員
	//帳號驗證相關
	VERIFICATION_CODE_IS_SEND(200, "Verification code is send"),//發送新的驗證碼
	VERIFICATION_IS_INVALID(403, "Verification is invalid"),//驗證已過期
	PLEASE_VERIFY(403, "Please verify"),//請先驗證
	ACCOUNT_IS_BANNED(403, "Account is banned"),//帳號被封鎖
	//UserService使用
	PLEASE_LOGIN_FIRST(401,"Please login first"),//要求使用者先登入
	EMAIL_HAS_FOUND(400,"Email has found"),//檢查email是否註冊過
	EMAIL_ISNOT_SCHOOL(400,"Email is not school"),//
	PARAM_NAME_ERROR(400,"Name error"),//
	PARAM_PASSWORD_ERROR(400,"Password error"),//
	EMAIL_OR_PASSWORD_ERROR(400,"Email or password error"),//
	SCHOOL_IS_NULL(400,"School is null"),//
	LOCATION_IS_NULL(400,"Location is null"),//
	PARAM_PHONE_ERROR(400,"Phone error"),//
	MESSAGE_TOO_LONG(400,"Message too long"),//
	DEPARTMENT_IS_NULL(400,"Department is null"),//
	EMAIL_NOT_FOUND(400,"Email not found"),//
	INVALID_VERIFICATION_CODE(400,"Invalid verification code"),//
	ACCOUNT_IS_VERIFICATION(400,"Account is verification"),//帳號已驗證
	PLEASE_SET_FILE(400,"Please set file"),//請設定圖片
	FILE_TOO_MANY(400,"File max seven"),//檔案最多七張
	FILE_SIZE_ERROR(400,"File size error"),//
	PLEASE_TRY_LATE(400,"Please try late"),//
	PASSWORD_NOT_CHANGE(400,"Password not change"),//檢查更改的密碼是否與原密碼一致
	//Order用
	PRODUCT_IS_NOTFOUND(400, "Product is not found"),//找不到商品
	PRODUCT_IS_UNSELL(400, "Product is unsell"),//未販售的商品
	NO_PERMISSIONS(400, "You have not Permissions"),//沒有權限
	ORDER_STATUS_ERROR(400, "Order status error"),//訂單狀態異常(無法進行此操作)
	ALREADY_RANKED(400, "The order is already ranked"),//使用者已給予過評價
	INVALID_ERROR(400, "The parameter is error !"),//輸入參數錯誤(給予評分沒有在放圍內)
	//Report
	DESCRIPTION_IS_NULL(400, "Description is null"),//請輸入描述
	TYPE_IS_NULL(400, "Type is null"),//請輸入類型(可能是report也可能是product的)
	VIOLATIONTYPE_IS_NULL(400, "ViolationType is null"),//請輸入檢舉類型
	REPORTTYPE_ERROR(400, "ReportType error"),//檢舉類型錯誤
	TYPR_IS_ERROR(400, "Type is error"),//檢舉類型錯誤
	//公告
	TITLE_IS_NULL(400, "Title is null"),//標題為空
	DATE_ERROR(400, "Date error"),//日期為空
	CONTENT_TEXT_OVER(400, "Counent text over"),//內文過長
	PRODUCT_IS_COLLECT(400, "The product is alreay collect"),//商品以收藏
	;
	
	private int code;
	private String message;
	
	private ReplyMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
