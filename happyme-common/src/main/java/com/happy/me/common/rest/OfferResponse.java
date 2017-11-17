package com.happy.me.common.rest;

import java.util.List;

import com.happy.me.common.dto.OfferDto;

public class OfferResponse {
	
	List<OfferDto> offerDtos;
	
	public OfferResponse() {
		super();
	}

	public OfferResponse(List<OfferDto> offerDtos) {
		super();
		this.offerDtos = offerDtos;
	}

	public List<OfferDto> getOfferDtos() {
		return offerDtos;
	}

	public void setOfferDtos(List<OfferDto> offerDtos) {
		this.offerDtos = offerDtos;
	}
	
	

}
