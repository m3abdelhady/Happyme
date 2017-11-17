package com.happy.me.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.common.util.Utils;
import com.happy.me.service.UserService;
import com.happy.me.service.exception.EmailFoundServiceException;
import com.happy.me.service.exception.LoginServiceException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.ServiceException;
import com.happy.me.service.exception.UserNameFoundServiceException;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserBusiness userBusiness;
	
	@Override
	public UserDto registerUser(UserDto userDto, String type) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserByEmail(userDto.getEmail());
			if (dto.isPresent()) {
				throw new EmailFoundServiceException("Faild to create user email already exists -> " + userDto.getEmail());
			}
			userDto.setUid(Utils.getUniqueId());
			userDto.setRoleKey(RoleKey.getEnum(type));
			userDto.setPassword(Utils.encryptPassword(userDto.getPassword()));
			return userBusiness.addUser(userDto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating user", e);
		}
	}

	@Override
	public UserDto getUser(UserDto userDto, boolean normalUser) throws ServiceException {
		try {
			Optional<UserDto> dto ;
			//from FORM
			if (normalUser) {
				dto = userBusiness.getUserByEmail(userDto.getEmail());
				if (!dto.isPresent()) {
					throw new UserNameFoundServiceException("user not found : " + userDto.getEmail());
				}
				if (!dto.get().getPassword().equals(Utils.encryptPassword(userDto.getPassword()))) {
					throw new LoginServiceException("password not match for user " + userDto.getEmail());
				}
			}else {//from FB
				dto = userBusiness.getFacebookUser(userDto.getFacebookId());
				if (!dto.isPresent()) {
					UserDto user = new UserDto();
					user.setFacebookId(userDto.getFacebookId());
					if (null == userDto.getEmail()) 
						user.setEmail(System.currentTimeMillis() + "");
					user.setUid(Utils.getUniqueId());
					user.setRoleKey(RoleKey.USER);
					user = userBusiness.addUser(user);
					user.setEmail("");
					return user;
				}else {
					dto.get().setEmail("");
				}
			}
			
			return dto.get();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while authenticate user", e);

		}
	}

	@Override
	public List<UserDto> getUsers(Long id) throws ServiceException {
		try {
			return userBusiness.getUsers(id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting user", e);
		}
	}

	@Override
	public void addAppFeedback(AppFeedbackDto appFeedbackDto, Long id) throws ServiceException {
		try {
			userBusiness.addAppFeedback(appFeedbackDto, id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while add feedback", e);
		}
		
	}

	@Override
	public List<AppFeedbackDto> getAppFeedback(Long id) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(id);
			if (dto.isPresent()) {
				if (!dto.get().getRoleKey().getValue().equals(RoleKey.ADMIN.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			}else {
				throw new NotAuthorizedException("Exception while get feedback");
			}
			List<AppFeedbackDto> appFeedbackDtos = userBusiness.getAppFeedback(id);
			return appFeedbackDtos;
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
	}

}
