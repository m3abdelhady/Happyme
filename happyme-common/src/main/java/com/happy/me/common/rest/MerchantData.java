package com.happy.me.common.rest;

import java.util.List;

public class MerchantData  {

	private Long id;

	private String name;
	
	private String description;
	
    private byte[] logo;
	
    private byte[] background;
    
    private String backgroundColor;
	
	private String phone;

    private boolean active;
	
	private MerchantRuleData merchantRuleData;

	private UserData userData;
	
	private List<AddressData> addressDatas;

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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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


	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public List<AddressData> getAddressDatas() {
		return addressDatas;
	}

	public void setAddressDatas(List<AddressData> addressDatas) {
		this.addressDatas = addressDatas;
	}

	public MerchantRuleData getMerchantRuleData() {
		return merchantRuleData;
	}

	public void setMerchantRuleData(MerchantRuleData merchantRuleData) {
		this.merchantRuleData = merchantRuleData;
	}
	
	
	
}
