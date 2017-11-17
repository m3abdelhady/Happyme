package com.happy.me.business.impl;

import java.util.List;
import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.dataaccess.model.AppFeedback;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.repository.AppFeedbackRepository;
import com.happy.me.dataaccess.repository.UserRepository;

@Service("userBusiness")
public class UserBusinessImpl implements UserBusiness {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AppFeedbackRepository appFeedbackRepository;

	@Autowired
	private Mapper mapper;
	
	@Override
	public UserDto addUser(UserDto userDto) throws BusinessException {
		try {
			User user = userRepository.save(mapper.map(userDto, User.class, DozerMapping.USER_VS_USERDTO.getKey()));
			UserDto dto = null;
			dto = mapper.map(user, UserDto.class);
			return dto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating user", e);
		}
		
	}


	@Override
	public Optional<UserDto> getFacebookUser(String facebookId) throws BusinessException {
		try {
			Optional<User> user = userRepository.findByFacebookIdIgnoreCase(facebookId);
			UserDto dto = null;
			if (user.isPresent()) {
				dto = mapper.map(user.get(), UserDto.class);
			}
			return Optional.ofNullable(dto);
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user by facebook id : " + facebookId, e);
		}
		
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}


	@Override
	public Optional<UserDto> getUserByEmail(String email) throws BusinessException {
		try {
			Optional<User> user = userRepository.findByEmailIgnoreCase(email);
			UserDto dto = null;
			if (user.isPresent()) {
				dto = mapper.map(user.get(), UserDto.class);
			}
			return Optional.ofNullable(dto);
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user by email: " + email, e);
		}
	}


	@Override
	public List<UserDto> getUsers(Long id) throws BusinessException {
		try {
			User user = new User();
			user.setId(id);
			List<User> users = userRepository.getCreatedUserById(user);
			List<UserDto> dtos = DozerHelper.map(mapper, users, UserDto.class, DozerMapping.USER_VS_USERDTO.getKey());
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user created by id: " + id, e);
		}
	}


	@Override
	public void addAppFeedback(AppFeedbackDto appFeedbackDto, Long id) throws BusinessException {
		try {
			AppFeedback appFeedback = new AppFeedback();
			appFeedback.setFeedback(appFeedbackDto.getFeedback());
			User user = new User();
			user.setId(id);
			appFeedback.setUser(user);
			appFeedbackRepository.save(appFeedback);
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user created by id: " + id, e);
		}
		
	}


	@Override
	public List<AppFeedbackDto> getAppFeedback(Long id) throws BusinessException {
		try {
			List<AppFeedback> appFeedback = (List<AppFeedback>) appFeedbackRepository.findAll();
			List<AppFeedbackDto> dtos = null;
			if (appFeedback != null) 			
				dtos = DozerHelper.map(mapper, appFeedback, AppFeedbackDto.class, DozerMapping.APPFEEDBACK_VS_APPFEEDBACKDTO.getKey());
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user by id : " + id, e);
		}
	}


	@Override
	public Optional<UserDto> getUserById(Long id) throws BusinessException {
		try {
			Optional<User> user = userRepository.findById(id);
			UserDto dto = null;
			if (user.isPresent()) {
				dto = mapper.map(user.get(), UserDto.class);
			}
			return Optional.ofNullable(dto);
		} catch (Exception e) {
			throw new BusinessException("Exception while getting user by id : " + id, e);
		}
	}

	

}
