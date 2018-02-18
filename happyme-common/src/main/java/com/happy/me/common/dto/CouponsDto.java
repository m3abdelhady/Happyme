package com.happy.me.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class CouponsDto implements BaseDto{


	private static final long serialVersionUID = 1L;


	private Long id;

	private String title;
	
	private String description;
	
	private String amount;

	private Long userCouponId;


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date start;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date end;


    private MerchantDto merchantDto;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStart() {
		return start;
	}


	public void setStart(Date start) {
		this.start = start;
	}


	public Date getEnd() {
		return end;
	}


	public void setEnd(Date end) {
		this.end = end;
	}

	public MerchantDto getMerchantDto() {
		return merchantDto;
	}


	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}
	
	public String getAmount() {
		return amount;
	}
	
	
	public void setAmount(String amount) {
		this.amount = amount;
	}


	public Long getUserCouponId() {
		return userCouponId;
	}


	public void setUserCouponId(Long userCouponId) {
		this.userCouponId = userCouponId;
	}
	
}
