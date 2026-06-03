package com.example.HellTrain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.config.CloudinaryService;
import com.example.HellTrain.constant.ProductStatus;
import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.constant.ReportStatus;
import com.example.HellTrain.constant.ReportType;
import com.example.HellTrain.constant.UserStatus;
import com.example.HellTrain.dao.ProductDao;
import com.example.HellTrain.dao.ReportDao;
import com.example.HellTrain.dao.UserDao;
import com.example.HellTrain.entity.Product;
import com.example.HellTrain.entity.Report;
import com.example.HellTrain.entity.User;
import com.example.HellTrain.request.ReportReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetIdReportRes;
import com.example.HellTrain.response.ReportRes;
import com.example.HellTrain.vo.ReportVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReportService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	private UserDao userDao;
	@Autowired
	private ReportDao reportDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CloudinaryService cloudinaryService;

	private ObjectMapper mapper = new ObjectMapper();

	private List<String> parseJsonList(String json) {
		if (json == null || json.isBlank())
			return new ArrayList<>();
		try {
			return mapper.readValue(json, new TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	private ReportVo toVo(Report report) {
		ReportVo vo = new ReportVo();
		vo.setReportId(report.getReportId());
		vo.setProductId(report.getProductId());
		vo.setComplainantId(report.getComplainantId());
		vo.setAccusedId(report.getAccusedId());
		vo.setDescription(report.getDescription());
		vo.setFilePath(parseJsonList(report.getFilePath())); // 轉 List
		vo.setReportDate(report.getReportDate());
		vo.setStatus(report.getStatus());
		vo.setType(report.getType());
		vo.setViolationType(report.getViolationType());
		return vo;
	}

	public BasicResponse addReport(String email, ReportReq req) {

		// 1. 透過 session 接收 user (檢舉人)
		User complainant = userDao.getAccount(email);
		if (complainant == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(),
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		if (!UserStatus.Normal.getMessage().equals(complainant.getStatus())) {
			return new BasicResponse(ReplyMessage.NO_PERMISSIONS.getCode(), //
					ReplyMessage.NO_PERMISSIONS.getMessage());
		}

		// 2. 驗證檢舉類型 (Type)
//		if (!StringUtils.hasText(req.getType())) {
//			return new BasicResponse(ReplyMessage.TYPE_IS_NULL.getCode(), //
//					ReplyMessage.TYPE_IS_NULL.getMessage());
//		}
//		if (!req.getType().equals(ReportType.Product.getMessage())
//				&& !req.getType().equals(ReportType.Sales.getMessage())) {
//			return new BasicResponse(ReplyMessage.REPORTTYPE_ERROR.getCode(),
//					ReplyMessage.REPORTTYPE_ERROR.getMessage());
//		}

		// 3. 檢查違規種類
		if (!StringUtils.hasText(req.getViolationType())) {
			return new BasicResponse(ReplyMessage.VIOLATIONTYPE_IS_NULL.getCode(),
					ReplyMessage.VIOLATIONTYPE_IS_NULL.getMessage());
		}

		// 4. 檢查違規描述 (修正後的邏輯：沒填 或 超過200字 都報錯)
		if (!StringUtils.hasText(req.getDescription()) || req.getDescription().length() > 200) {
			return new BasicResponse(ReplyMessage.DESCRIPTION_IS_NULL.getCode(),
					ReplyMessage.DESCRIPTION_IS_NULL.getMessage());
		}

		// 5. 檢查是否有圖片 (修正後的邏輯：包括空集合檢查)
		if (req.getFilePath() == null || req.getFilePath().isEmpty()) {
			return new BasicResponse(ReplyMessage.PLEASE_SET_FILE.getCode(), //
					ReplyMessage.PLEASE_SET_FILE.getMessage());
		}

		// 6. 圖片上傳 Cloudinary
		List<String> fileUrls = new ArrayList<>();
		for (String base64Image : req.getFilePath()) {
			if (base64Image == null || base64Image.isBlank()) {
				continue;
			}
			try {
				String imageUrl = cloudinaryService.uploadBase64(base64Image);
				fileUrls.add(imageUrl);
			} catch (Exception e) {
				return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),
						ReplyMessage.PLEASE_TRY_LATE.getMessage());
			}
		}

		// 轉成逗號分隔字串存入 DB
		try {
			String filePathStr = mapper.writeValueAsString(fileUrls);
			LocalDate reportDate = LocalDate.now();
			// 7. 新增檢舉 先判斷檢舉類型 (呼叫您修正過參數順序後的 Repository)
			String type;
			if (productDao.findByProductId(req.getProductId()) != null) {
				type = ReportType.Product.getMessage();
				
			} else if (userDao.getById(req.getAccusedId()) != null) {
				type = ReportType.Sales.getMessage();
				
			} else {
				return new BasicResponse(ReplyMessage.TYPR_IS_ERROR.getCode(), //
						ReplyMessage.TYPR_IS_ERROR.getMessage());
			}
			reportDao.addReport(req.getProductId(), req.getAccusedId(), complainant.getUserId(), req.getDescription(),
					filePathStr, reportDate, ReportStatus.Handle.getMessage(), type, req.getViolationType());

		} catch (Exception e) {
			return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(), //
					ReplyMessage.PLEASE_TRY_LATE.getMessage());
		}
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}

	// 後臺用，抓取所有
	public ReportRes getAllReport() {

		// 血祭DAO
		List<Report> report = reportDao.findAll();
		List<ReportVo> voList = report.stream().map(this::toVo).collect(Collectors.toList());
		return new ReportRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), voList);
	}

	// 抓單一report
	public GetIdReportRes getReportById(int reportId) {

		// 再血dao
		Report report = reportDao.getReportById(reportId);

		if (report == null) {
			return new GetIdReportRes(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}
		return new GetIdReportRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), toVo(report));
	}

	// 動作！！！
	public BasicResponse check(int reportId) {

		Report report = reportDao.getReportById(reportId);

		if (report == null) {
			return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
					ReplyMessage.NO_DATA_FOUND.getMessage());
		}

		// 檢舉類型為檢舉商品
		if (report.getType().equals(ReportType.Product.getMessage())) {
			// 下架商品
			Product product = productDao.findByProductId(report.getProductId());
			if (product == null) {
				return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
						ReplyMessage.NO_DATA_FOUND.getMessage());
			}
			productDao.changeStatus(product.getProductId(), ProductStatus.Removed.getMassage());
		}

		// 檢舉類型為使用者
		if (report.getType().equals(ReportType.Sales.getMessage())) {
			// 取得被檢舉人資料
			User user = userDao.getById(report.getAccusedId());
			// 找不到使用者
			if (user == null) {
				return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
						ReplyMessage.NO_DATA_FOUND.getMessage());
			}
			userDao.changeStatus(user.getUserId(), UserStatus.Suspension.getMessage());
		}

		reportDao.check(reportId, ReportStatus.Processed.getMessage());
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
}
