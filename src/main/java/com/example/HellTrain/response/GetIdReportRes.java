package com.example.HellTrain.response;

import com.example.HellTrain.vo.ReportVo;

public class GetIdReportRes extends BasicResponse {

	private ReportVo report;


	public ReportVo getReport() {
		return report;
	}

	public void setReport(ReportVo report) {
		this.report = report;
	}

	public GetIdReportRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetIdReportRes(int statusCode, String message) {
		super(statusCode, message);
		// TODO Auto-generated constructor stub
	}

	public GetIdReportRes(int statusCode, String message, ReportVo report) {
		super(statusCode, message);
		this.report = report;
	}
	
}
