package com.example.HellTrain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="announcement")
public class Announcement {
	
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "img_path")
	private String imgPath;

	@Column(name = "shelf_date")
	private LocalDate shelfDate;

	@Column(name = "removal_date")
	private LocalDate removalDate;

//	@Column(name = "status")
//	private String status;
	
	@Column(name = "publish")
	private boolean publish;
	
	@Column(name = "content")
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
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

//	public String getStatus() {
//		return status;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

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
