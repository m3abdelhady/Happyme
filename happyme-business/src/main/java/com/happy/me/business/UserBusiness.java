package com.happy.me.business;

import java.util.List;
import java.util.Optional;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.UserDto;

public interface UserBusiness {

	public UserDto addUser(UserDto userDto) throws BusinessException;
	public Optional<UserDto> getFacebookUser(String facebookId) throws BusinessException;
	public Optional<UserDto> getUserByEmail(String email) throws BusinessException;
	public List<UserDto> getUsers(Long id) throws BusinessException;
	public void addAppFeedback(AppFeedbackDto appFeedbackDto, Long id) throws BusinessException;
	public List<AppFeedbackDto> getAppFeedback(Long id) throws BusinessException;
	public Optional<UserDto> getUserById(Long id) throws BusinessException;
	
}
