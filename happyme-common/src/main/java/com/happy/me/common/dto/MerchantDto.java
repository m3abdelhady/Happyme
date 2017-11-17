package com.happy.me.common.dto;

import java.util.List;

public class MerchantDto implements BaseDto {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;
	
	private String description;
	
    private byte[] logo;
	
    private byte[] background;
    
    private String backgroundColor;
	
	@SuppressWarnings("unused")
	private Long storeNumber;

	private String phone;

    private boolean active;
	
	private MerchantRuleDto merchantRuleDto;

	private UserDto userDto;
	
	private List<AddressDto> addressDtos;
	

	public MerchantDto(Long id) {
		super();
		this.id = id;
	}

	public MerchantDto() {
		this.active = true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getBackground() {
		return background;
	}

	public void setBackground(byte[] background) {
		this.background = background;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public MerchantRuleDto getMerchantRuleDto() {
		return merchantRuleDto;
	}

	public void setMerchantRuleDto(MerchantRuleDto merchantRuleDto) {
		this.merchantRuleDto = merchantRuleDto;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Long getStoreNumber() {
		if (addressDtos == null) {
			return 0L;
		}
		return (long) addressDtos.size();
	}

	public void setStoreNumber(Long storeNumber) {
		this.storeNumber = storeNumber;
	}

	public List<AddressDto> getAddressDtos() {
		return addressDtos;
	}

	public void setAddressDtos(List<AddressDto> addressDtos) {
		this.addressDtos = addressDtos;
	}

}
