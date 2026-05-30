package com.example.HellTrain.response;

import com.example.HellTrain.entity.Report;

public class GetIdReportRes extends BasicResponse {

	private Report report;

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
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

	public GetIdReportRes(int statusCode, String message, Report report) {
		super(statusCode, message);
		this.report = report;
	}
	
}
