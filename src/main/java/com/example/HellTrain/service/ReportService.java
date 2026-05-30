package com.example.HellTrain.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
//	@Autowired
//	private 

	public BasicResponse addReport(String email, ReportReq req) {

		User complainant = userDao.getAccount(email);
		// 以session傳入email資料取得檢舉人資訊
		int complainantId = complainant.getUserId();

		// 驗證被檢舉人id
		if (userDao.getById(req.getAccusedId()) == null) {
			return new BasicResponse(ReplyMessage.REPORTTYPE_IS_NULL.getCode(), //
					ReplyMessage.REPORTTYPE_IS_NULL.getMessage());
		}

		// type is null
		if (StringUtils.hasText(req.getType())) {
			return new BasicResponse(ReplyMessage.REPORTTYPE_IS_NULL.getCode(), //
					ReplyMessage.REPORTTYPE_IS_NULL.getMessage());
		}
		// type is error
		if (!req.getType().equals(ReportType.Product.getMessage())
				|| !req.getType().equals(ReportType.Product.getMessage())) {//
			return new BasicResponse(ReplyMessage.REPORTTYPE_ERROR.getCode(), //
					ReplyMessage.REPORTTYPE_ERROR.getMessage());
		}

		//檢查違規種類
		if (!StringUtils.hasText(req.getViolationType())) {
			return new BasicResponse(ReplyMessage.VIOLATIONTYPE_IS_NULL.getCode(), //
					ReplyMessage.VIOLATIONTYPE_IS_NULL.getMessage());
		}
		
		//檢查違規描述
		if(!(StringUtils.hasText(req.getDescription()))&& req.getDescription().length()>200)
		{
			return new BasicResponse(ReplyMessage.DESCRIPTION_IS_ERROR.getCode(), //
					ReplyMessage.DESCRIPTION_IS_ERROR.getMessage());
		}

		// 圖片檢查
		List<String> filePaths = new ArrayList<>();

		if (req.getFilePath() != null && !req.getFilePath().isEmpty()) {

			for (MultipartFile file : req.getFilePath()) {

				// 跳過空的檔案
				if (file == null || file.isEmpty())
					continue;

				// 1. 檢查檔案類型
				List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp",
						"application/pdf");
				if (!allowedTypes.contains(file.getContentType())) {
					return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(),
							ReplyMessage.FILE_FORMAT_ERROR.getMessage());
				}

				// 2. 檢查副檔名
				String originalFilename = file.getOriginalFilename();
				if (originalFilename == null || !originalFilename.contains(".")) {
					return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(),
							ReplyMessage.FILE_FORMAT_ERROR.getMessage());
				}
				String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
				List<String> allowedExt = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp");
				if (!allowedExt.contains(ext)) {
					return new BasicResponse(ReplyMessage.FILE_NAMEFORMAT_ERROR.getCode(),
							ReplyMessage.FILE_NAMEFORMAT_ERROR.getMessage());
				}

				// 3. 檢查大小（5MB）
				if (file.getSize() > 2 * 1024 * 1024) {
					return new BasicResponse(ReplyMessage.FILE_SIZE_ERROR.getCode(),
							ReplyMessage.FILE_SIZE_ERROR.getMessage());
				}

				// 4. 儲存
				try {
					String newFilename = UUID.randomUUID().toString() + ext;
					File dir = new File(uploadPath);
					if (!dir.exists())
						dir.mkdirs();
					file.transferTo(new File(uploadPath + newFilename));
					filePaths.add(newFilename);
				} catch (IOException e) {
					return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),
							ReplyMessage.PLEASE_TRY_LATE.getMessage());
				}
			}
		}

		// 轉成逗號分隔字串存入DB
		String filePathStr = filePaths.isEmpty() ? null : String.join(",", filePaths);

		LocalDate reportDate = LocalDate.now();

		String status = ReportStatus.Handle.getMessage();

		// reportDao insert資料
		reportDao.addReport(complainantId, req.getAccusedId(), req.getDescription(), filePathStr, reportDate, status,
				req.getType(), req.getViolationType());

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
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

		//檢舉類型為檢舉商品
		if (report.getType().equals(ReportType.Product.getMessage())) {
			// 下架商品
			Product product=productDao.findByProductId(reportId);
			if(product==null) {
				return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
						ReplyMessage.NO_DATA_FOUND.getMessage());
			}
			productDao.changeStatus(product.getProductId(), ProductStatus.Removed.getMassage());
		}
		
		//檢舉類型為使用者
		if(report.getType().equals(ReportType.Sales.getMessage())) {
			//取得被檢舉人資料
			User user=userDao.getById(report.getAccusedId());
			//找不到使用者
			if(user==null)
			{
				return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(), //
						ReplyMessage.NO_DATA_FOUND.getMessage());
			}
			userDao.changeStatus(user.getUserId(), UserStatus.Suspension.getMessage());
		}

		return new BasicResponse(ReplyMessage.SUCCESS.getCode(), //
				ReplyMessage.SUCCESS.getMessage());
	}
}
