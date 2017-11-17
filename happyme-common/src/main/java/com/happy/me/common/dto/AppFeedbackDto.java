package com.happy.me.common.dto;

import java.util.Date;

public class AppFeedbackDto implements BaseDto{


	private static final long serialVersionUID = 1L;

	private Long id;

	private String feedback;

	private UserDto userDto;
	
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	
	
}
