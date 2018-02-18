package com.happy.me.controller;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happy.me.common.constants.CommonConstants;
import com.happy.me.common.dto.AppFeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.common.rest.AppFeedbackRest;
import com.happy.me.common.rest.Response;
import com.happy.me.common.rest.UserData;
import com.happy.me.common.rest.UserPasswordData;
import com.happy.me.service.UserService;
import com.happy.me.service.exception.EmailFoundException;
import com.happy.me.service.exception.LoginServiceException;
import com.happy.me.service.exception.MerchantAssignException;
import com.happy.me.service.exception.MerchantNotFoundException;
import com.happy.me.service.exception.NewPasswordMatchException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.ResellerNotFoundException;
import com.happy.me.service.exception.UserNameFoundException;
import com.happy.me.service.exception.UserNotFoundException;
import com.happy.me.service.exception.WrongPasswordException;
import com.happy.me.utils.WebUtils;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	Mapper mapper;

	Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<?> login() {
		logger.debug("UserController.login()");
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{type}/register", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> register(@RequestBody UserData data, @PathVariable("type") String type) {
		logger.debug("UserController.register() " + "Starting to register user with type : " + type +" and email : " + data.getEmail());
		try {
			UserDto userDto = mapper.map(data, UserDto.class);
			if (data.getCreator() != null) {
				userDto.setCreatedBy(new UserDto(data.getCreator()));
			}
			if (data.getMerchantId() != null) {
				userDto.setMerchantDto(new MerchantDto(data.getMerchantId()));
			}

			userDto = userService.registerUser(userDto, type);
			userDto.setPassword(null);
			userDto.setCreatedBy(null);
			return ResponseEntity.ok(userDto);
		} catch (EmailFoundException e) {
			logger.debug("UserController.register() failed while register email already found " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.EMAIL_ALREADY_FOUND);
//		} catch (UserNameFoundException e) {
//			logger.debug("UserController.register() failed while register username already found ");
//			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.USERNAME_ALREADY_FOUND);
		} catch (Exception e) {
			logger.debug("UserController.register() [FAILURE] failed while register email " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/{type}/login", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> login(@RequestBody UserData data, @PathVariable("type") String type) {
		logger.debug("UserController.login() for user using " + type  );
		try {
			UserDto userDto = mapper.map(data, UserDto.class);
			if (CommonConstants.FACEBOOK_USER.toUpperCase().equals(type.toUpperCase())) {
				userDto = userService.getUser(userDto, false);
			} else {
				userDto = userService.getUser(userDto, true);
			}
			if (!userDto.getRoleKey().getValue().equals(RoleKey.USER.getValue())) {
				return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.FAILURE);
			}
			return ResponseEntity.ok(userDto);
		} catch (UserNameFoundException e) {
			logger.debug("UserController.login() failed while login email not found " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.EMAIL_NOT_FOUND);
		} catch (LoginServiceException e) {
			logger.debug("UserController.login() failed while login password error " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.PASSWORD_ERROR);
		} catch (Exception e) {
			logger.debug("UserController.login() [FAILURE] failed while login email " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> loginAdmin(@RequestBody UserData data) {
		logger.debug("UserController.loginAdmin() for user " + data.getEmail()  );
		try {
			UserDto userDto = mapper.map(data, UserDto.class);
			userDto = userService.getUser(userDto, true);
			if (userDto.getRoleKey().getValue().equals(RoleKey.USER.getValue())) {
				return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.FAILURE);
			}
			return ResponseEntity.ok(userDto);
		} catch (UserNameFoundException e) {
			logger.debug("UserController.loginAdmin() failed while login email not found " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.EMAIL_NOT_FOUND);
		} catch (LoginServiceException e) {
			logger.debug("UserController.loginAdmin() failed while login password error " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.PASSWORD_ERROR);
		} catch (Exception e) {
			logger.debug("UserController.loginAdmin() [FAILURE] failed while login email " + data.getEmail(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/{id}/feedback", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> addAppFeedback(@RequestBody AppFeedbackDto appFeedbackDto, @PathVariable("id") Long id) {
		logger.debug("UserController.addAppFeedback() add app feedback from user id : " + id);
		try {
			userService.addAppFeedback(appFeedbackDto, id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			logger.debug("UserController.addAppFeedback() [FAILURE] failed to add app feedback from user id : " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/{id}/feedback", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getAppFeedback(@PathVariable("id") Long id) {
		logger.debug("UserController.getAppFeedback() get App feedback for user id " + id );
		try {
			List<AppFeedbackDto> appFeedbackDtos = userService.getAppFeedback(id);
			AppFeedbackRest appFeedbackRest = new AppFeedbackRest();
			appFeedbackRest.setList(appFeedbackDtos);
			return ResponseEntity.ok(appFeedbackRest);
		} catch (NotAuthorizedException e) {
			logger.debug("UserController.getAppFeedback()  [NON_AUTHORITATIVE_INFORMATION] failed while get app feedback from user id : " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		} catch (Exception e) {
			logger.debug("UserController.getAppFeedback() [FAILURE] failed while get app feedback from user id : " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getUsers(@PathVariable("id") Long id) {
		logger.debug("UserController.getUsers() start to get user created by user id : " + id);
		try {
			List<UserDto> userDto = userService.getUsers(id);
			return ResponseEntity.ok(userDto);
		} catch (Exception e) {
			logger.debug("UserController.getUsers() [FAILURE] failed while get user created by user id : " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/{userId}/password", method = RequestMethod.POST, produces = { "application/json" })
	public ResponseEntity<?> updateUserPassword(@RequestBody UserPasswordData userPasswordData,
			@PathVariable("userId") Long userId) {
		logger.debug("UserController.updateUserPassword() start to update password for user id : " + userId);
		try {
			userService.updateUserPassword(userPasswordData, userId);
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			logger.debug("UserController.updateUserPassword() [INVALID_USER] faild while update password for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		} catch (WrongPasswordException e) {
			logger.debug("UserController.updateUserPassword() [PASSWORD_ERROR] faild while update password for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.PASSWORD_ERROR);
		} catch (NewPasswordMatchException e) {
			logger.debug("UserController.updateUserPassword() [PASSWORD_MATCH] faild while update password for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.PASSWORD_MATCH);
		} catch (Exception e) {
			logger.debug("UserController.updateUserPassword() [FAILURE] faild while update password for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/reseller/{userId}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<?> getAllReseller(@PathVariable("userId") Long userId) {
		logger.debug("UserController.getAllReseller() start to get all reseller for user id : " + userId);
		try {
			List<UserDto> userDtos = userService.getListUserByRole(RoleKey.RESELLER, userId);
			return ResponseEntity.ok(new Response(userDtos));
		} catch (NotAuthorizedException e) {
			logger.debug("UserController.getAllReseller() [NON_AUTHORITATIVE_INFORMATION] faild while get all reseller for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		} catch (UserNotFoundException e) {
			logger.debug("UserController.getAllReseller() [INVALID_AGENT] faild while get all reseller for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_AGENT);
		} catch (Exception e) {
			logger.debug("UserController.getAllReseller() [FAILURE] faild while get all reseller for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/reseller/{userId}/{resellerId}", method = RequestMethod.DELETE, produces = {
			"application/json" })
	public ResponseEntity<?> deleteReseller(@PathVariable("userId") Long userId,
			@PathVariable("resellerId") Long resellerId) {
		logger.debug("UserController.deleteReseller() start to delete reseller id " + resellerId + " , by user id " + userId);
		try {
			userService.deleteReseller(userId, resellerId);
			return ResponseEntity.ok().build();
		} catch (NotAuthorizedException e) {
			logger.debug("UserController.getAllReseller() [NON_AUTHORITATIVE_INFORMATION] faild while delete reseller id " + resellerId + " , by user id " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		} catch (UserNotFoundException e) {
			logger.debug("UserController.getAllReseller() [INVALID_USER] faild while delete reseller id " + resellerId + " , by user id " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		} catch (ResellerNotFoundException e) {
			logger.debug("UserController.getAllReseller() [INVALID_RESELLER] faild while delete reseller id " + resellerId + " , by user id " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_RESELLER);
		} catch (Exception e) {
			logger.debug("UserController.getAllReseller() [FAILURE] faild while delete reseller id " + resellerId + " , by user id " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "/pending/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getPendingMerchant(@PathVariable("userId") Long userId) {
		logger.debug("UserController.getPendingMerchant() start to get pending merchant for user id : " + userId);
		try {
			List<UserDto> dtos = userService.getPendingMerchant(userId);
			return ResponseEntity.ok(new Response(dtos));
		} catch (Exception e) {
			logger.debug("UserController.getPendingMerchant() [FAILURE] faild while get pending merchant for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "{userId}/assign/{merchantId}", method = RequestMethod.POST)
	public ResponseEntity<?> assignUserToMerchant(@PathVariable("merchantId") Long merchantId,
			@PathVariable("userId") Long userId) {
		logger.debug("UserController.assignUserToMerchant() start to assign user id : " + userId + " to merchant id : " + merchantId);
		try {
			userService.assignUserToMerchant(merchantId, userId);
			return ResponseEntity.ok().build();
		} catch (NotAuthorizedException e) {
			logger.debug("UserController.assignUserToMerchant() [NON_AUTHORITATIVE_INFORMATION] faild while assign user id : " + userId + " to merchant id : " + merchantId,e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		} catch (MerchantAssignException e) {
			logger.debug("UserController.assignUserToMerchant() [INVALID_MERCHANT_ASSIGN] faild while assign user id : " + userId + " to merchant id : " + merchantId,e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_ASSIGN);
		} catch (MerchantNotFoundException e) {
			logger.debug("UserController.assignUserToMerchant() [INVALID_MERCHANT] faild while assign user id : " + userId + " to merchant id : " + merchantId,e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT);
		} catch (Exception e) {
			logger.debug("UserController.assignUserToMerchant() [FAILURE] faild while assign user id : " + userId + " to merchant id : " + merchantId,e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

	@RequestMapping(value = "{userId}/pending/{ownerId}", method = RequestMethod.POST)
	public ResponseEntity<?> assignUserToPendingMerchant(@PathVariable("ownerId") Long ownerId,
			@PathVariable("userId") Long userId) {
		logger.debug("UserController.assignUserToPendingMerchant() start to assign user id : " + userId + " to pending user id : " + ownerId);
		try {
			userService.assignUserToPendingMerchant(ownerId, userId);
			return ResponseEntity.ok().build();
		} catch (NotAuthorizedException e) {
			logger.debug("UserController.assignUserToPendingMerchant() [NON_AUTHORITATIVE_INFORMATION] faild to assign user id : " + userId + " to pending user id : " + ownerId);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		} catch (MerchantAssignException e) {
			logger.debug("UserController.assignUserToPendingMerchant() [INVALID_MERCHANT_ASSIGN] faild  to assign user id : " + userId + " to pending user id : " + ownerId);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_ASSIGN);
		} catch (MerchantNotFoundException e) {
			logger.debug("UserController.assignUserToPendingMerchant() [INVALID_MERCHANT] faild  to assign user id : " + userId + " to pending user id : " + ownerId);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT);
		} catch (Exception e) {
			logger.debug("UserController.assignUserToPendingMerchant() [FAILURE] faild  to assign user id : " + userId + " to pending user id : " + ownerId);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}

}
