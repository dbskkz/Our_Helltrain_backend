package com.example.HellTrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HellTrain.request.AnnounceReq;
import com.example.HellTrain.response.AnnounceRes;
import com.example.HellTrain.response.BasicResponse;
import com.example.HellTrain.service.AnnounceService;

@RestController
@CrossOrigin
@RequestMapping("/announce")
public class AnnounceController {
	
	@Autowired
	private AnnounceService announceService;
	
	@GetMapping(value = "/getId")
	public AnnounceRes getByAnnounceId(@RequestParam int announceId) {
		return announceService.getByAnnounceId(announceId);
	}
	
	@GetMapping(value = "/getAll")
	public AnnounceRes getAllAnnounce() {
		return announceService.getAllAnnounce();
	}
	
	//取得架上公告
	@GetMapping(value = "/getActive")
	public AnnounceRes getOnActive() {
		return announceService.getOnActive();
	}

	//新增公告
	@PostMapping(value = "/addAnnounce")
	public BasicResponse addAnnounce(@RequestBody AnnounceReq req) {
		return announceService.addAnnounce(req);
	}
	
	@PostMapping(value = "/updateAnnounce")
	public BasicResponse updata(@RequestBody AnnounceReq req) {
		return announceService.updata(req);
	}

}
