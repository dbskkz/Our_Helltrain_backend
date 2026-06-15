package com.example.HellTrain.response;

import java.util.List;

import com.example.HellTrain.vo.CollectVoList;

public class CollectRes extends BasicResponse {
	
	List<CollectVoList> collectListVo;

	public List<CollectVoList> getCollectListVo() {
		return collectListVo;
	}

	public void setCollectListVo(List<CollectVoList> collectListVo) {
		this.collectListVo = collectListVo;
	}

	public CollectRes() {
		super();
	}

	public CollectRes(int statusCode, String message) {
		super(statusCode, message);
	}

	public CollectRes(int statusCode, String message, List<CollectVoList> collectListVo) {
		super(statusCode, message);
		this.collectListVo = collectListVo;
	}

}
