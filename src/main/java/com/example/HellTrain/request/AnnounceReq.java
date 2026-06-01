package com.example.HellTrain.request;

import java.time.LocalDate;


public class AnnounceReq {
	
	private int id;

	private String title;

	private String imgPath;

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

	public LocalDate getShelfDate() {
		return shelfDate;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
