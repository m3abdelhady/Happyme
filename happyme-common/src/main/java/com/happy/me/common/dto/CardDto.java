package com.happy.me.common.dto;

public class CardDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long merchantId;
	
	private Long point;
	
	private Long star;
	
	private String cardNumber;

	private Double amount;
	
	private Double redeemedAmount;
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(Double redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	
}
