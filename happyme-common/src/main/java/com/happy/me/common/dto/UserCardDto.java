package com.happy.me.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserCardDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String cardNumber;

	private Long point;

	private UserDto userDto;

	private MerchantDto merchantDto;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date modified;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" , timezone="EET")
	private Date created;

	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public MerchantDto getMerchantDto() {
		return merchantDto;
	}

	public void setMerchantDto(MerchantDto merchantDto) {
		this.merchantDto = merchantDto;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

}