package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.entity.Report;

public class ReportRes extends BasicResponse {

	private List<Report> reports;

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public ReportRes(int statusCode, String message, List<Report> reports) {
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
