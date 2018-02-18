package com.happy.me.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.common.rest.UserPasswordData;
import com.happy.me.common.util.Utils;
import com.happy.me.service.UserService;
import com.happy.me.service.exception.EmailFoundException;
import com.happy.me.service.exception.LoginServiceException;
import com.happy.me.service.exception.MerchantAssignException;
import com.happy.me.service.exception.MerchantNotFoundException;
import com.happy.me.service.exception.NewPasswordMatchException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.ResellerNotFoundException;
import com.happy.me.service.exception.ServiceException;
import com.happy.me.service.exception.UserNameFoundException;
import com.happy.me.service.exception.UserNotFoundException;
import com.happy.me.service.exception.WrongPasswordException;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private MerchantBusiness merchantBusiness;
	
	@Override
	public UserDto registerUser(UserDto userDto, String type) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserByEmail(userDto.getEmail());
			if (dto.isPresent()) {
				throw new EmailFoundException("Faild to create user email already exists -> " + userDto.getEmail());
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
					throw new UserNameFoundException("user not found : " + userDto.getEmail());
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

	@Override
	public void updateUserPassword(UserPasswordData userPasswordData, Long userId) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(userId);
			if (!dto.isPresent()) {
				throw new UserNotFoundException("Exception while get feedback");
			}
			if (!dto.get().getPassword().equals(Utils.encryptPassword(userPasswordData.getOldPassword()))) {
				throw new WrongPasswordException("password not match for user ");
			}
			if (!userPasswordData.getNewPassword().equals(userPasswordData.getRepeatedPassword())) {
				throw new NewPasswordMatchException("password not match for user ");
			}
			
			dto.get().setPassword(Utils.encryptPassword(userPasswordData.getNewPassword()));
			userBusiness.updateUserPassword(dto.get());
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
		
	}

	@Override
	public List<UserDto> getListUserByRole(RoleKey key, Long userId) throws ServiceException {
		try {
			Optional<UserDto> optionalUser =userBusiness.getUserById(userId);
			if (!optionalUser.isPresent()) {
				throw new UserNotFoundException("Not Found Exception");
			}
			if (!optionalUser.get().getRoleKey().getValue().equals(RoleKey.ADMIN.getValue())) {
				throw new NotAuthorizedException("Exception");
			}
			return userBusiness.getListUserByRole(key);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
	}

	@Override
	public void deleteReseller(Long userId, Long resellerId) throws ServiceException {
		try {
			Optional<UserDto> optionalUser =userBusiness.getUserById(userId);
			if (!optionalUser.isPresent()) {
				throw new UserNotFoundException("Not Found Exception");
			}
			if (!optionalUser.get().getRoleKey().getValue().equals(RoleKey.ADMIN.getValue())) {
				throw new NotAuthorizedException("Exception");
			}
			Optional<UserDto> optionalReseller =userBusiness.getUserById(resellerId);
			if (!optionalReseller.isPresent()) {
				throw new ResellerNotFoundException("Not Found Exception");
			}
			userBusiness.deleteReseller(resellerId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
		
	}

	@Override
	public List<UserDto> getPendingMerchant(Long userId) throws ServiceException {
		try {
			return userBusiness.getPendingMerchant(userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
	}

	@Override
	public void assignUserToMerchant(Long merchantId, Long userId) throws ServiceException {
		try {
			Optional<UserDto> userDto = userBusiness.getUserById(userId);
			if (!userDto.isPresent()) {
				throw new ResellerNotFoundException("Not Found Exception");
			}else if (!userDto.get().getRoleKey().getValue().equalsIgnoreCase(RoleKey.RESELLER.getValue())) {
				throw new MerchantAssignException("Not Found Exception");
			}
			Optional<MerchantDto> dto = merchantBusiness.getMerchantById(merchantId);
			if (!dto.isPresent()) {
				throw new MerchantNotFoundException("Not Found Exception");
			}
			 userBusiness.assignUserToMerchant(merchantId, userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
		
	}

	@Override
	public void assignUserToPendingMerchant(Long ownerId, Long userId) throws ServiceException {
		try {
			Optional<UserDto> userDto = userBusiness.getUserById(userId);
			if (!userDto.isPresent()) {
				throw new ResellerNotFoundException("Not Found Exception");
			}else if (!userDto.get().getRoleKey().getValue().equalsIgnoreCase(RoleKey.RESELLER.getValue())) {
				throw new MerchantAssignException("Not Found Exception");
			}
			Optional<UserDto> dto = userBusiness.getUserById(ownerId);
			if (!dto.isPresent()) {
				throw new MerchantNotFoundException("Not Found Exception");
			}else if (!dto.get().getRoleKey().getValue().equalsIgnoreCase(RoleKey.MERCHANT.getValue())) {
				throw new MerchantAssignException("Not Found Exception");
			}
			 userBusiness.assignUserToPendingMerchant(ownerId, userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get feedback", e);
		}
		
	}

}
