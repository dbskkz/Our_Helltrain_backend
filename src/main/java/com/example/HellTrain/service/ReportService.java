package com.example.HellTrain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	public BasicResponse addReport(String email, ReportReq req) {

		//
		User complainant = userDao.getAccount(email);
		// 以session傳入email資料取得檢舉人資訊


		// 驗證被檢舉人id　！！！！！問題最大！！！！！
		if (userDao.getById(req.getAccusedId()) == null) {
			return new BasicResponse(ReplyMessage.TYPE_IS_NULL.getCode(), //
					ReplyMessage.TYPE_IS_NULL.getMessage());
		}

		// type is null
		if (!StringUtils.hasText(req.getType())) {
			return new BasicResponse(ReplyMessage.TYPE_IS_NULL.getCode(), //
					ReplyMessage.TYPE_IS_NULL.getMessage());
		}
		// type is error
		if (!req.getType().equals(ReportType.Product.getMessage())
				&& !req.getType().equals(ReportType.Sales.getMessage())) {//
			return new BasicResponse(ReplyMessage.REPORTTYPE_ERROR.getCode(), //
					ReplyMessage.REPORTTYPE_ERROR.getMessage());
		}

		// 檢查違規種類
		if (!StringUtils.hasText(req.getViolationType())) {
			return new BasicResponse(ReplyMessage.VIOLATIONTYPE_IS_NULL.getCode(), //
					ReplyMessage.VIOLATIONTYPE_IS_NULL.getMessage());
		}

		// 檢查違規描述
		if (!(StringUtils.hasText(req.getDescription())) && req.getDescription().length() > 200) {
			return new BasicResponse(ReplyMessage.DESCRIPTION_IS_NULL.getCode(), //
					ReplyMessage.DESCRIPTION_IS_NULL.getMessage());
		}
		
		//檢查是否有圖片(證據)
		if(req.getFilePath()==null) {
			return new BasicResponse(ReplyMessage.PLEASE_SET_FILE.getCode(), //
					ReplyMessage.PLEASE_SET_FILE.getMessage());
		}

		// 圖片上傳
		List<String> fileUrls = new ArrayList<>();

		if (req.getFilePath() != null && !req.getFilePath().isEmpty()) {
			for (String base64Image : req.getFilePath()) {
				if (base64Image == null || base64Image.isBlank())
					continue;

				try {
					String imageUrl = cloudinaryService.uploadBase64(base64Image);
					fileUrls.add(imageUrl);
				} catch (Exception e) {
					return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),
							ReplyMessage.PLEASE_TRY_LATE.getMessage());
				}
			}
		}

		// 轉成逗號分隔字串存入 DB
		String filePathStr = fileUrls.isEmpty() ? null : String.join(",", fileUrls);

		// 新增檢舉
		reportDao.addReport(complainant.getUserId(), req.getAccusedId(), req.getDescription(), filePathStr, LocalDate.now(),
				ReportStatus.Handle.getMessage(), req.getType(), req.getViolationType());

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), ReplyMessage.SUCCESS.getMessage());
	}

	// 後臺用，抓取所有
	public ReportRes getAllReport() {

		// 血祭DAO
		List<Report> report = reportDao.findAll();
		return new ReportRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), report);
	}

	// 抓單一report
	public GetIdReportRes getReportById(int reportId) {

		// 再血dao
		Report report = reportDao.getReportById(reportId);

		return new GetIdReportRes(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage(), report);
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

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
}
