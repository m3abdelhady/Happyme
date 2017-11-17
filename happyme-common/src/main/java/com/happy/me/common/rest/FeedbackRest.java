package com.happy.me.common.rest;

import java.util.List;

import com.happy.me.common.dto.FeedbackDto;

public class FeedbackRest {

	private List<FeedbackDto> list;
	
	public FeedbackRest() {
		super();
	}
	

	public FeedbackRest(List<FeedbackDto> list) {
		super();
		this.list = list;
	}



	public List<FeedbackDto> getList() {
		return list;
	}

	public void setList(List<FeedbackDto> list) {
		this.list = list;
	}

	
}
