package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.ReportVo;

public class ReportRes extends BasicResponse {

	private List<ReportVo> reports;



	public List<ReportVo> getReports() {
		return reports;
	}

	public void setReports(List<ReportVo> reports) {
		this.reports = reports;
	}

	public ReportRes(int statusCode, String message, List<ReportVo> reports) {
		super(statusCode, message);
		this.reports = reports;
	}

	public ReportRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReportRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}
	
}
