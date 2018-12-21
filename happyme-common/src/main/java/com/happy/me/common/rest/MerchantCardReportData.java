package com.happy.me.common.rest;

import com.happy.me.common.dto.BaseDto;
import com.happy.me.common.dto.MerchantDto;

public class MerchantCardReportData implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	private MerchantDto merchantDto;
	
	private Long count;
	
	public MerchantCardReportData() {
		super();
	}
	
	public MerchantCardReportData(MerchantDto merchantDto, Long count) {
		super();
		this.merchantDto = merchantDto;
		this.count = count;
	}

	public MerchantDto getMerchantDto() {
		return merchantDto;
	}

	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
	

}
