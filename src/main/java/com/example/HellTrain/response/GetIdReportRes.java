package com.example.HellTrain.response;

import com.example.HellTrain.vo.ReportListVo;

public class GetIdReportRes extends BasicResponse {

	private ReportListVo report;



	public GetIdReportRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetIdReportRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public ReportListVo getReport() {
		return report;
	}

	public void setReport(ReportListVo report) {
		this.report = report;
	}

	public GetIdReportRes(int statusCode, String message, ReportListVo report) {
		super(statusCode, message);
		this.report = report;
	}

	
}
