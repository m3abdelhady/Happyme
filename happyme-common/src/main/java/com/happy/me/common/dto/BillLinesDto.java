package com.happy.me.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BillLinesDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;
	private MerchantDto merchantDto;

	private double amount;

	private BillHeaderDto billHeaderDto;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date fromDate;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date toDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MerchantDto getMerchantDto() {
		return merchantDto;
	}

	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public BillHeaderDto getBillHeaderDto() {
		return billHeaderDto;
	}

	public void setBillHeaderDto(BillHeaderDto billHeaderDto) {
		this.billHeaderDto = billHeaderDto;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
