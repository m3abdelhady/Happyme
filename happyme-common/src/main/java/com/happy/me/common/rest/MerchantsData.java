package com.happy.me.common.rest;

import java.util.List;

import com.happy.me.common.dto.MerchantDto;

public class MerchantsData {

	private List<MerchantDto> merchants;	
	
	public void setMerchants(List<MerchantDto> merchants) {
		this.merchants = merchants;
	}

	public MerchantsData(List<MerchantDto> merchants) {
		super();
		this.merchants = merchants;
	}

	public List<MerchantDto> getMerchants() {
		return merchants;
	}
	
	
}
