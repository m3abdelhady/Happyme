package com.happy.me.common.dto;

public class CardDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long merchantId;
	
	private Long point;
	
	private Long star;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getStar() {
		return star;
	}

	public void setStar(Long star) {
		this.star = star;
	}
	
	
}
