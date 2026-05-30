package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.constant.ReplyMessage;
import com.example.HellTrain.request.ReportReq;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.response.GetIdReportRes;
import com.example.HellTrain.response.ReportRes;
import com.example.HellTrain.service.ReportService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@PostMapping(value = "/addReport")
	public BasicResponse addReport(HttpSession session, @RequestBody ReportReq req) {
		String email = (String) session.getAttribute("user_email");
		if (email == null) {
			return new BasicResponse(ReplyMessage.PLEASE_LOGIN_FIRST.getCode(), //
					ReplyMessage.PLEASE_LOGIN_FIRST.getMessage());
		}
		return reportService.addReport(email, req);
	}
	
	@PostMapping(value = "/getAllReport")
	public ReportRes getAllReport() {
		return reportService.getAllReport();
	}
	
	@GetMapping(value = "/getReportById")
	public GetIdReportRes getReportById(@RequestParam("reportId") int reportId) {
		return reportService.getReportById(reportId);
	}
	
	@PostMapping(value = "/check")
	public BasicResponse check(@RequestBody int reportId) {
		return reportService.check(reportId);
	}

}
