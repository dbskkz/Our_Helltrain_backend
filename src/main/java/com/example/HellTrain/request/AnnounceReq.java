package com.example.HellTrain.request;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class AnnounceReq {

	private String title;

	private MultipartFile imgPath;

	private LocalDate shelfDate;

	private LocalDate removalDate;
	
	private boolean publish;

	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MultipartFile getImgPath() {
		return imgPath;
	}

	public void setImgPath(MultipartFile imgPath) {
		this.imgPath = imgPath;
	}

	public LocalDate getShelfDate() {
		return shelfDate;
	}

	public void setShelfDate(LocalDate shelfDate) {
		this.shelfDate = shelfDate;
	}

	public LocalDate getRemovalDate() {
		return removalDate;
	}

	public void setRemovalDate(LocalDate removalDate) {
		this.removalDate = removalDate;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
