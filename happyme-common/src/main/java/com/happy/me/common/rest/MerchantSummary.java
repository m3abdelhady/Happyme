package com.happy.me.common.rest;

import com.happy.me.common.dto.BaseDto;

public class MerchantSummary implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	
	private Long cards;
	
	private Long point;
	
	private Long amounts;
	
	private Long transactions;

	public Long getCards() {
		return cards;
	}

	public void setCards(Long cards) {
		this.cards = cards;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Long getAmounts() {
		return amounts;
	}

	public void setAmounts(Long amounts) {
		this.amounts = amounts;
	}

	public Long getTransactions() {
		return transactions;
	}

	public void setTransactions(Long transactions) {
		this.transactions = transactions;
	}
    

}
