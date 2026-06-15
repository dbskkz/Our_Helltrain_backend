package com.example.HellTrain.vo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//用於getByReportId
public class ReportListVo {
	private int reportId;
	private int productId;
	private String description;
	private List<String> filePath;
	private LocalDate reportDate;
	private String status;
	private String type;
	private String violationType;
	private String note;
	private String productName;
	private String accusedName;
	private String complainantName;
	private int accusedId;
	
	public int getAccusedId() {
		return accusedId;
	}
	public void setAccusedId(int accusedId) {
		this.accusedId = accusedId;
	}
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAccusedName() {
		return accusedName;
	}
	public void setAccusedName(String accusedName) {
		this.accusedName = accusedName;
	}
	public String getComplainantName() {
		return complainantName;
	}
	public void setComplainantName(String complainantName) {
		this.complainantName = complainantName;
	}

	public static ReportListVo fromRow(Object[] row) {
		ReportListVo vo = new ReportListVo();
	    vo.setReportId((Integer) row[0]);
	    vo.setProductId(row[1] != null ? (Integer) row[1] : 0);
	    vo.setDescription((String) row[2]);
	    
	    // file_path 是逗號分隔或 JSON，轉成 List
	    vo.setFilePath(parseJsonList((String) row[3]));  // ← 轉成 List<String>
	    
	    vo.setReportDate(((java.sql.Date) row[4]).toLocalDate());
	    vo.setStatus((String) row[5]);
	    vo.setType((String) row[6]);
	    vo.setViolationType((String) row[7]);
	    vo.setNote((String) row[8]);
	    vo.setProductName(row[9] != null ? (String) row[9] : null);
	    vo.setAccusedName(row[10] != null ? (String) row[10] : null);
	    vo.setComplainantName((String) row[11]);
	    vo.setAccusedId(row[12] != null ? (Integer) row[12] : 0);
	    
	    return vo;
	}
	private static List<String> parseJsonList(String json) {
		if (json == null || json.isBlank())
			return new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, new TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
