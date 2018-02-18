package com.happy.me.common.rest;

import com.happy.me.common.dto.BaseDto;

public class UserPasswordData implements BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String oldPassword;
	
	private String newPassword;

	private String repeatedPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	
	
	
	
	

}
