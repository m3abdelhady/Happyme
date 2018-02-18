package com.happy.me.common.rest;

import com.happy.me.common.dto.BaseDto;

public class MerchantPaymentInfoData implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	private double mrc;

    private double outStanding;
    
    private double creditLimit;

	public double getMrc() {
		return mrc;
	}

	public void setMrc(double mrc) {
		this.mrc = mrc;
	}

	public double getOutStanding() {
		return outStanding;
	}

	public void setOutStanding(double outStanding) {
		this.outStanding = outStanding;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	

}
