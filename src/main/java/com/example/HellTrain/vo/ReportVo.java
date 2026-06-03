package com.example.HellTrain.vo;

import java.time.LocalDate;
import java.util.List;

public class ReportVo {
    private int reportId;
    private int productId;
    private int complainantId;
    private int accusedId;
    private String description;
    private List<String> filePath;  // JSON 字串轉 List
    private LocalDate reportDate;
    private String status;
    private String type;
    private String violationType;
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
	public List<String> getFilePath() {
		return filePath;
	}
	public void setFilePath(List<String> filePath) {
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
    
    
}
