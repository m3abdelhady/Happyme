package com.happy.me.dataaccess.extra;

import com.happy.me.common.dto.BaseDto;
import com.happy.me.dataaccess.model.Merchant;

public class MerchantCardReport implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Merchant merchant;

	private Long count;

	public MerchantCardReport() {
		super();
	}
	
	public MerchantCardReport(Merchant merchant, Long count) {
		super();
		this.merchant = merchant;
		this.count = count;
	}



	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
