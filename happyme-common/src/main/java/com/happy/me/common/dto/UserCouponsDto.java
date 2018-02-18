package com.happy.me.common.dto;

public class UserCouponsDto implements BaseDto{


	private static final long serialVersionUID = 1L;


	private Long id;

	private UserDto userDto;
	
	private CouponsDto couponsDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public CouponsDto getCouponsDto() {
		return couponsDto;
	}

	public void setCouponsDto(CouponsDto couponsDto) {
		this.couponsDto = couponsDto;
	}
	
	 
}
