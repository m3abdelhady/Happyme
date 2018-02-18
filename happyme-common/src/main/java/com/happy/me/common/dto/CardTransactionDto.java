package com.happy.me.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.happy.me.common.enums.TransactionType;

public class CardTransactionDto implements BaseDto{


	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long point;
	
	private String description;
	
	private String amount;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date transactionDate;
	
	private UserCardDto userCardDto;
	
    private TransactionType transactionType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public UserCardDto getUserCardDto() {
		return userCardDto;
	}

	public void setUserCardDto(UserCardDto userCardDto) {
		this.userCardDto = userCardDto;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
}