package com.example.HellTrain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@Column(name = "report_id")
	private int reportId;

	@Column(name = "product_id")
	private int productId;

	@Column(name = "complainant_id")
	private int complainantId;//檢舉人Id

	@Column(name = "accused_id")
	private int accusedId;//被檢舉人Id

	@Column(name = "description")
	private String description;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "report_date")
	private LocalDate reportDate;

	@Column(name = "status")
	private String status;

	@Column(name = "type")
	private String type;

	@Column(name = "violation_type")
	private String violationType;
	
	@Column(name = "note")
	private String note;

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getComplainantId() {
		return complainantId;
	}

	public void setComplainantId(int complainantId) {
		this.complainantId = complainantId;
	}

	public int getAccusedId() {
		return accusedId;
	}

	public void setAccusedId(int accusedId) {
		this.accusedId = accusedId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LocalDate getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
