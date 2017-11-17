package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.service.exception.ServiceException;

public interface UserService {

	public UserDto registerUser(UserDto userDto, String type) throws ServiceException;
	public UserDto getUser(UserDto userDto, boolean normalUser) throws ServiceException;
	public List<UserDto> getUsers(Long id) throws ServiceException;
	public void addAppFeedback(AppFeedbackDto appFeedbackDto, Long id) throws ServiceException;
	public List<AppFeedbackDto> getAppFeedback(Long id) throws ServiceException;
	

}
