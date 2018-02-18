package com.happy.me.common.dto;

public class CardSummaryDto implements BaseDto{


	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	
	private UserCardDto userCardDto;	

	private Long point;
	
	private Double amount;
	
	private Double redeemedAmount;
	
	private Long star;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserCardDto getUserCardDto() {
		return userCardDto;
	}

	public void setUserCardDto(UserCardDto userCardDto) {
		this.userCardDto = userCardDto;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

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

	public Long getStar() {
		return star;
	}

	public void setStar(Long star) {
		this.star = star;
	}

	
}