package com.happy.me.controller;

import java.util.List;
import java.util.Random;

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
import com.happy.me.common.dto.CardDto;
import com.happy.me.common.dto.CardsDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.AppFeedbackRest;
import com.happy.me.common.rest.UserData;
import com.happy.me.service.UserService;
import com.happy.me.service.exception.EmailFoundServiceException;
import com.happy.me.service.exception.LoginServiceException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.UserNameFoundServiceException;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/user")
@EnableAutoConfiguration
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	Mapper mapper;

	@RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> login() {
      return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{type}/register", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> register(@RequestBody UserData data, @PathVariable("type") String type ) {
		try {
			UserDto userDto = mapper.map(data, UserDto.class);
			if (data.getCreator() != null) {
				userDto.setCreatedBy(new UserDto(data.getCreator()));
			}
			userDto = userService.registerUser(userDto, type);
			userDto.setPassword(null);
			userDto.setCreatedBy(null);
			return ResponseEntity.ok(userDto);
		} catch (EmailFoundServiceException e) {
            return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.EMAIL_ALREADY_FOUND);
		}catch (UserNameFoundServiceException e) {
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.USERNAME_ALREADY_FOUND);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/{type}/login", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> login(@RequestBody UserData data, @PathVariable("type") String type ) {
		try {
			UserDto userDto = mapper.map(data, UserDto.class);
			if (CommonConstants.FACEBOOK_USER.toUpperCase().equals(type.toUpperCase())) {
				userDto = userService.getUser(userDto, false);
			}else {
				userDto = userService.getUser(userDto, true);
			}
			return ResponseEntity.ok(userDto);
		} catch (UserNameFoundServiceException e) {
            return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.EMAIL_NOT_FOUND);
		}catch (LoginServiceException e) {
			return WebUtils.prepareErrorResponse(HttpStatus.UNAUTHORIZED, AppErrorCode.PASSWORD_ERROR);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{id}/feedback", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addAppFeedback(@RequestBody AppFeedbackDto appFeedbackDto, @PathVariable("id") Long id ) {
		try {
			userService.addAppFeedback(appFeedbackDto, id);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{id}/feedback", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getAppFeedback(@PathVariable("id") Long id ) {
		try {
			List<AppFeedbackDto> appFeedbackDtos = userService.getAppFeedback(id);
			AppFeedbackRest appFeedbackRest = new AppFeedbackRest();
			appFeedbackRest.setList(appFeedbackDtos);
			return ResponseEntity.ok(appFeedbackRest);
		}catch (NotAuthorizedException e) {
			return WebUtils.prepareErrorResponse(HttpStatus.NON_AUTHORITATIVE_INFORMATION, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getUsers(@PathVariable("id") Long id ) {
		try {
			List<UserDto> userDto = userService.getUsers(id);
			return ResponseEntity.ok(userDto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/cards/{id}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getUserCards(@PathVariable("id") Long id ) {
		try {
			CardsDto cardsDto = new CardsDto();
			cardsDto.setTotal(id);
			long []a= {7,12,1,8,15,9,11,10,13};
			for (int i = 0; i < a.length; i++) {
				CardDto cardDto = new CardDto();
				cardDto.setMerchantId(a[i]);
				Random rand = new Random();
				int  n = rand.nextInt(500) + 1;
				cardDto.setPoint(Long.parseLong(n + ""));
				n = rand.nextInt(5) + 1;
				cardDto.setStar(Long.parseLong(n + ""));
				cardsDto.addCard(cardDto);
			}
			
			return ResponseEntity.ok(cardsDto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	
	
}
