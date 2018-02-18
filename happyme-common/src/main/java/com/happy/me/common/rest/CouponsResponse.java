package com.happy.me.common.rest;

import java.util.List;

import com.happy.me.common.dto.CouponsDto;

public class CouponsResponse {
	
	List<CouponsDto> couponsDtos;
	
	public CouponsResponse() {
		super();
	}

	public CouponsResponse(List<CouponsDto> couponsDtos) {
		super();
		this.couponsDtos = couponsDtos;
	}

	public List<CouponsDto> getCouponsDtos() {
		return couponsDtos;
	}

	public void setCouponsDtos(List<CouponsDto> couponsDtos) {
		this.couponsDtos = couponsDtos;
	}

	
	

}
