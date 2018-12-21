package com.happy.me.common.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BillHeaderDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private MerchantDto merchantDto;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date billDate;
	private double billAmount;
	private double balanceBroughtForward;
	private double totalAmountDue;
	private String invoiceNumber;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date fromDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date toDate;
	
	List<BillLinesDto> billLinesDtos;

	public List<BillLinesDto> getBillLinesDtos() {
		return billLinesDtos;
	}

	public void setBillLinesDtos(List<BillLinesDto> billLinesDtos) {
		this.billLinesDtos = billLinesDtos;
	}

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

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public double getBalanceBroughtForward() {
		return balanceBroughtForward;
	}

	public void setBalanceBroughtForward(double balanceBroughtForward) {
		this.balanceBroughtForward = balanceBroughtForward;
	}

	public double getTotalAmountDue() {
		return totalAmountDue;
	}

	public void setTotalAmountDue(double totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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
