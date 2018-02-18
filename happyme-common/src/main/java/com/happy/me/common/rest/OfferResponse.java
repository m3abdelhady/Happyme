package com.happy.me.common.rest;

import com.happy.me.common.dto.OfferDto;

public class OfferResponse {
	
	OfferDto offerDto;
	
	public OfferResponse() {
		super();
	}

	public OfferResponse(OfferDto offerDto) {
		super();
		this.offerDto = offerDto;
	}

	public OfferDto getOfferDto() {
		return offerDto;
	}

	public void setOfferDto(OfferDto offerDto) {
		this.offerDto = offerDto;
	}

	
	

}
