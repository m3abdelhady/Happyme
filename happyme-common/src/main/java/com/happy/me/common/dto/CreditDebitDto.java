package com.happy.me.common.dto;

import java.util.Date;

public class CreditDebitDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private MerchantDto merchantDto;

	private double amount;

	private BillHeaderDto billHeaderDto;

	private Date actionDate;

	private String type;

	private String paymentId;

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

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

}
