package com.example.HellTrain.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.dao.AnnounceDao;
import com.example.HellTrain.entity.Announcement;
import com.example.HellTrain.request.AnnounceReq;
import com.example.HellTrain.response.AnnounceRes;
import com.example.HellTrain.response.BasicResponse;

@Service
public class AnnounceService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	private AnnounceDao annouDao;

	// 提取個別公告
	public AnnounceRes getByAnnounceId(int announceId) {

		// dao
		Announcement annou = annouDao.getById(announceId);

		return new AnnounceRes(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.NO_DATA_FOUND.getMessage(), annou);
	}

	// 抓取所有公告資料
	public AnnounceRes getAllAnnounce() {

		List<Announcement> annouList = annouDao.getAllAnnouncement();
		return new AnnounceRes(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.SUCCESS.getMessage(),annouList);
	}
	
	//取得架上公告
	public AnnounceRes getOnActive() {
		
		List<Announcement> annouList = annouDao.getOnActive();

		return new AnnounceRes(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.SUCCESS.getMessage(),annouList);
	}

	// 新增公告
	public BasicResponse addAnnounce(AnnounceReq req) {

		// 檢查標題
		if (!StringUtils.hasText(req.getTitle())) {
			return new BasicResponse(ReplyMessage.TITLE_IS_NULL.getCode(),//
					ReplyMessage.TITLE_IS_NULL.getMessage());
		}

		// 檢查時間
		//當開始時間 在 現在時間 之前(isBefore)>開始時間早於現在時間
		if (req.getShelfDate().isBefore(LocalDate.now())) {
			return new BasicResponse(ReplyMessage.DATE_ERROR.getCode(),//
					ReplyMessage.DATE_ERROR.getMessage());
		}
		//當開始時間 在 結束時間 之後(isAfter)>開始時間比結束時間晚
		if (req.getShelfDate().isAfter(req.getRemovalDate())) {
			return new BasicResponse(ReplyMessage.DATE_ERROR.getCode(),//
					ReplyMessage.DATE_ERROR.getMessage());
		}
		
		//檢查內文長度
		if(req.getContent().length()>300)
		{
			return new BasicResponse(ReplyMessage.CONTENT_TEXT_OVER.getCode(),//
					ReplyMessage.CONTENT_TEXT_OVER.getMessage());
		}
		
		// 1. 確認有上傳圖片（必填）
	    if (req.getImgPath() == null || req.getImgPath().isEmpty()) {
	        return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(),//
					ReplyMessage.NO_DATA_FOUND.getMessage());
	    }

	    // 2. 檢查檔案類型
	    List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
	    if (!allowedTypes.contains(req.getImgPath().getContentType())) {
	        return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(),//
					ReplyMessage.FILE_FORMAT_ERROR.getMessage());
	    }

	    // 3. 檢查副檔名
	    String originalFilename = req.getImgPath().getOriginalFilename();
	    if (originalFilename == null || !originalFilename.contains(".")) {
	        return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(),//
					ReplyMessage.NO_DATA_FOUND.getMessage());
	    }
	    String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
	    List<String> allowedExt = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp");
	    if (!allowedExt.contains(ext)) {
	        return new BasicResponse(ReplyMessage.FILE_NAMEFORMAT_ERROR.getCode(),//
					ReplyMessage.FILE_NAMEFORMAT_ERROR.getMessage());
	    }

	    // 4. 檢查大小（2MB）
	    if (req.getImgPath().getSize() > 2 * 1024 * 1024) {
	        return new BasicResponse(ReplyMessage.FILE_SIZE_ERROR.getCode(),//
					ReplyMessage.FILE_SIZE_ERROR.getMessage());
	    }

	    // 5. 儲存
	    try {
	        String newFilename = UUID.randomUUID().toString() + ext;
	        File dir = new File(uploadPath);
	        if (!dir.exists()) dir.mkdirs();
	        req.getImgPath().transferTo(new File(uploadPath + newFilename));

	        // 新增公告dao
	        annouDao.addAnnounce(req.getTitle(), newFilename, req.getShelfDate(), req.getRemovalDate(),//
	        		req.isPublish(), req.getContent());

	    } catch (IOException e) {
	        return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),//
					ReplyMessage.PLEASE_TRY_LATE.getMessage());
	    }
		
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.SUCCESS.getMessage());
	}
	
	public BasicResponse updata(AnnounceReq req) {
		

		// 檢查標題
		if (!StringUtils.hasText(req.getTitle())) {
			return new BasicResponse(ReplyMessage.TITLE_IS_NULL.getCode(),//
					ReplyMessage.TITLE_IS_NULL.getMessage());
		}

		// 檢查時間
		//當開始時間 在 現在時間 之前(isBefore)>開始時間早於現在時間
		if (req.getShelfDate().isBefore(LocalDate.now())) {
			return new BasicResponse(ReplyMessage.DATE_ERROR.getCode(),//
					ReplyMessage.DATE_ERROR.getMessage());
		}
		//當開始時間 在 結束時間 之後(isAfter)>開始時間比結束時間晚
		if (req.getShelfDate().isAfter(req.getRemovalDate())) {
			return new BasicResponse(ReplyMessage.DATE_ERROR.getCode(),//
					ReplyMessage.DATE_ERROR.getMessage());
		}
		
		//檢查內文長度
		if(req.getContent().length()>300)
		{
			return new BasicResponse(ReplyMessage.CONTENT_TEXT_OVER.getCode(),//
					ReplyMessage.CONTENT_TEXT_OVER.getMessage());
		}
		
		// 有上傳新圖片才處理
		String imgPath;

		if (req.getImgPath() != null && !req.getImgPath().isEmpty()) {
		    // 檢查類型、副檔名、大小（跟頭像一樣）
		    // ...
			// 1. 確認有上傳圖片（必填）
		    if (req.getImgPath() == null || req.getImgPath().isEmpty()) {
		        return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(),//
						ReplyMessage.NO_DATA_FOUND.getMessage());
		    }

		    // 2. 檢查檔案類型
		    List<String> allowedTypes = List.of("image/jpeg", "image/png", "image/gif", "image/webp");
		    if (!allowedTypes.contains(req.getImgPath().getContentType())) {
		        return new BasicResponse(ReplyMessage.FILE_FORMAT_ERROR.getCode(),//
						ReplyMessage.FILE_FORMAT_ERROR.getMessage());
		    }

		    // 3. 檢查副檔名
		    String originalFilename = req.getImgPath().getOriginalFilename();
		    if (originalFilename == null || !originalFilename.contains(".")) {
		        return new BasicResponse(ReplyMessage.NO_DATA_FOUND.getCode(),//
						ReplyMessage.NO_DATA_FOUND.getMessage());
		    }
		    String ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		    List<String> allowedExt = List.of(".jpg", ".jpeg", ".png", ".gif", ".webp");
		    if (!allowedExt.contains(ext)) {
		        return new BasicResponse(ReplyMessage.FILE_NAMEFORMAT_ERROR.getCode(),//
						ReplyMessage.FILE_NAMEFORMAT_ERROR.getMessage());
		    }

		    // 4. 檢查大小（2MB）
		    if (req.getImgPath().getSize() > 2 * 1024 * 1024) {
		        return new BasicResponse(ReplyMessage.FILE_SIZE_ERROR.getCode(),//
						ReplyMessage.FILE_SIZE_ERROR.getMessage());
		    }

		    try {
		        String newFilename = UUID.randomUUID().toString() + ext;
		        File dir = new File(uploadPath);
		        if (!dir.exists()) dir.mkdirs();
		        req.getImgPath().transferTo(new File(uploadPath + newFilename));
		        imgPath = newFilename;
		    } catch (IOException e) {
				return new BasicResponse(ReplyMessage.PLEASE_TRY_LATE.getCode(),//
						ReplyMessage.PLEASE_TRY_LATE.getMessage());		    }

		} else {
		    // 沒上傳就保留原本的
//		    Announcement announcement = announcementDao.getById(req.getImgPath());
//		    imgPath = announcement.getImgPath();
		}

//		announcementDao.updateAnnouncement(vo.getId(), vo.getTitle(), vo.getContent(), imgPath);
		return new BasicResponse(ReplyMessage.SUCCESS.getCode(),//
				ReplyMessage.SUCCESS.getMessage());
		
	}

	
	

}
