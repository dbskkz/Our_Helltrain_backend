package com.example.HellTrain.vo;

import java.time.LocalDate;


//用於getAllReport
public class ReportVo {
    private int reportId;
    private String type;
    private int productId;
    private String accusedName;
    private String complainantName;
    private LocalDate reportDate;
    private String status;

    
    public int getReportId() {
		return reportId;
	}


	public void setReportId(int reportId) {
		this.reportId = reportId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
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


	public static ReportVo fromRow(Object[] row) {
    	ReportVo vo = new ReportVo();
        vo.setReportId((int) row[0]);
        vo.setType((String) row[1]);
        vo.setProductId((int) row[2]);
        vo.setAccusedName((String) row[3]);
        vo.setComplainantName((String) row[4]);
        vo.setReportDate(((java.sql.Date) row[5]).toLocalDate());
        vo.setStatus((String) row[6]);
        return vo;
    }
}
