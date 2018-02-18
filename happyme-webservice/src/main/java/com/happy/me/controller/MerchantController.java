package com.happy.me.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.rest.AddressData;
import com.happy.me.common.rest.FeedbackRest;
import com.happy.me.common.rest.MerchantData;
import com.happy.me.common.rest.MerchantPaymentInfoData;
import com.happy.me.common.rest.MerchantRuleData;
import com.happy.me.common.rest.MerchantsData;
import com.happy.me.common.rest.Response;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.service.MerchantService;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.UserNotFoundException;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/merchant")
@EnableAutoConfiguration
public class MerchantController {
	
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	Mapper mapper;

	Logger logger = Logger.getLogger(MerchantController.class);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addMerchant(@RequestBody MerchantData data, @PathVariable("id") Long id) {
		logger.debug("MerchantController.addMerchant() start to add merchant for user " + id);
		try {
			MerchantDto dto = mapper.map(data, MerchantDto.class, DozerMapping.MERCHANTDATA_VS_MERCHANTDTO.getKey());
			dto.setUserDto(new UserDto(id));
			if (data.getAddressDatas() != null) {
				List<AddressDto> addressDtos = DozerHelper.map(mapper, data.getAddressDatas(), AddressDto.class);
				dto.setAddressDtos(addressDtos);
			}
			dto = merchantService.addMerchant(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("MerchantController.addMerchant() [FAILURE] failed to add merchant for user " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantData data, @PathVariable("id") Long merchantId) {
		logger.debug("MerchantController.updateMerchant() start to update merchant for user " + merchantId);
		try {
			MerchantDto dto = mapper.map(data, MerchantDto.class, DozerMapping.MERCHANTDATA_VS_MERCHANTDTO.getKey());
			dto.setId(merchantId);
			dto = merchantService.updateMerchant(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("MerchantController.updateMerchant() [FAILURE] failed to update merchant for user " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addAddress(@RequestBody AddressData data, @PathVariable("id") Long id) {
		logger.debug("MerchantController.addAddress() start to add address for merchant " + id);
		try {
			AddressDto dto = mapper.map(data, AddressDto.class);
			dto.setMerchantDto(new MerchantDto(id));
			
			dto = merchantService.addAddress(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("MerchantController.addAddress() [FAILURE] failed to add address for merchant " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id) {
		logger.debug("MerchantController.deleteAddress() start to delete address id " + id);
		try {
			merchantService.deleteAddress(id);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("MerchantController.deleteAddress() [FAILURE] failed to delete address id " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	@RequestMapping(value = "/rule/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addMerchantRule(@RequestBody MerchantRuleData data, @PathVariable("id") Long id) {
		logger.debug("MerchantController.addMerchantRule() start to merchant rule for merchant " + id);
		try {
			MerchantRuleDto dto = mapper.map(data, MerchantRuleDto.class);	
			dto = merchantService.addMerchantRule(dto, id);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("MerchantController.addMerchantRule() [FAILURE] failed to merchant rule for merchant " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/rule/{id}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateMerchantRule(@RequestBody MerchantRuleData data, @PathVariable("id") Long id) {
		logger.debug("MerchantController.updateMerchantRule() start to update merchant rule " + id);
		try {
			MerchantRuleDto dto = mapper.map(data, MerchantRuleDto.class);	
			dto.setId(id);
			dto = merchantService.updateMerchantRule(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("MerchantController.updateMerchantRule() [FAILURE] failed to update merchant rule " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchant() {
		logger.debug("MerchantController.getMerchant() start to get list of active merchants");
		try {
			List<MerchantDto> merchantDtos = merchantService.getActiveMerchant();
			return ResponseEntity.ok(new MerchantsData(merchantDtos));
		}catch (Exception e) {
			logger.debug("MerchantController.getMerchant() [FAILURE] failed to get list of active merchants");
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchant(@PathVariable("id") Long id) {
		logger.debug("MerchantController.getMerchant() start to get merchant id " + id);
		try {
			MerchantDto merchantDto = merchantService.getMerchant(id);
			return ResponseEntity.ok(merchantDto);
		}catch (Exception e) {
			logger.debug("MerchantController.getMerchant() [FAILURE] failed to get merchant id " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMerchant() {
		logger.debug("MerchantController.getAllMerchant() start to get all merchant ");
		try {
			List<MerchantDto> merchantDtos = merchantService.getAllMerchant();
			return ResponseEntity.ok(new MerchantsData(merchantDtos));
		}catch (Exception e) {
			logger.debug("MerchantController.getAllMerchant() [FAILURE] failed to get all merchant ", e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	@RequestMapping(value = "/logo/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadLogo(@RequestParam("logo") MultipartFile logo, @PathVariable("id") Long id) {
		logger.debug("MerchantController.uploadLogo() start to upload logo for merchant id " + id);
		try {
			byte[] logos = merchantService.updateMerchantLogo(logo.getBytes(), id); 
			return ResponseEntity.ok(logos);
		}catch (Exception e) {
			logger.debug("MerchantController.uploadLogo() [FAILURE] failed to upload logo for merchant id " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/background/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadBackground(@RequestParam("background") MultipartFile background, @PathVariable("id") Long id) {
		logger.debug("MerchantController.uploadBackground() start to upload background for merchant id " + id);
		try {
			merchantService.updateMerchantBackground(background.getBytes(), id); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("MerchantController.uploadBackground() [FAILURE] failed to upload background for merchant id " + id, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchant}/feedback/{user}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addFeedback(@RequestBody  FeedbackDto feedbackDto, @PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		logger.debug("MerchantController.addFeedback() start to add feedback for merchant " + merchantId + " by user " + userId);
		try {
			merchantService.addFeedback(feedbackDto, merchantId, userId);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("MerchantController.addFeedback() [FAILURE] failed to add feedback for merchant " + merchantId + " by user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchant}/feedback/{user}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getFeedback(@PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		logger.debug("MerchantController.getFeedback() start to get feedback for merchant " + merchantId + " request by user " + userId );
		try {
			List<FeedbackDto> feedbackDtos = merchantService.getFeedbacks(merchantId, userId);
			
			return ResponseEntity.ok(new FeedbackRest(feedbackDtos));
		}catch (NotAuthorizedException e) {
			logger.debug("MerchantController.getFeedback() [NON_AUTHORITATIVE_INFORMATION] failed to get feedback for merchant " + merchantId + " request by user " + userId , e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (Exception e) {
			logger.debug("MerchantController.getFeedback() [FAILURE] failed to get feedback for merchant " + merchantId + " request by user " + userId , e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchant}/status/{user}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> activeMerchant(@PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		logger.debug("MerchantController.activeMerchant() start to change status for merchant " + merchantId + " by user id " + userId );
		try {
			merchantService.changeMerchantFlag(userId, merchantId);
			return ResponseEntity.ok().build();
		}catch (NotAuthorizedException e) {
			logger.debug("MerchantController.activeMerchant() [NON_AUTHORITATIVE_INFORMATION] failed to change status for merchant " + merchantId + " by user id " + userId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (UserNotFoundException e) {
			logger.debug("MerchantController.activeMerchant() [INVALID_USER] failed to change status for merchant " + merchantId + " by user id " + userId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		}catch (Exception e) {
			logger.debug("MerchantController.activeMerchant() [FAILURE] failed to change status for merchant " + merchantId + " by user id " + userId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/pending", method = RequestMethod.GET)
    public ResponseEntity<?> getPendingMerchant() {
		logger.debug("MerchantController.getPendingMerchant() start to get pending merchant");
		try {
			List<UserDto> dtos = merchantService.getPendingMerchant();
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("MerchantController.getPendingMerchant() [FAILURE] failed to get pending merchant");
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/by/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchantCreatedByUser(@PathVariable("userId") Long userId) {
		logger.debug("MerchantController.getMerchantCreatedByUser() start to get merchant created by " + userId);
		try {
			List<MerchantDto> dtos = merchantService.getMerchantCreatedByUser(userId);
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("MerchantController.getMerchantCreatedByUser() [FAILURE] failed to get merchant created by " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{userId}/agent/{agentId}", method = RequestMethod.DELETE, produces = { "application/json" })
    public ResponseEntity<?> deleteAgent(@PathVariable("userId") Long userId , @PathVariable("agentId") Long agentId ) {
		logger.debug("MerchantController.deleteAgent() start to delete agent " + agentId + " by user " + userId);
		try {
			merchantService.deleteAgent(userId, agentId);
			return ResponseEntity.ok().build();
		}catch (NotAuthorizedException e) {
			logger.debug("MerchantController.deleteAgent() [NON_AUTHORITATIVE_INFORMATION] failed to delete agent " + agentId + " by user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (UserNotFoundException e) {
			logger.debug("MerchantController.deleteAgent() [INVALID_USER] failed to delete agent " + agentId + " by user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		}catch (Exception e) {
			logger.debug("MerchantController.deleteAgent() [FAILURE] failed to delete agent " + agentId + " by user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchant}/payment/{user}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> addMerchantPaymetInfo(@RequestBody MerchantPaymentInfoData paymentInfoData,@PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		logger.debug("MerchantController.addMerchantPaymetInfo() start to add merchant payment info merchant " + merchantId + " by user " + userId );
		try {
			merchantService.addMerchantPaymetInfo(userId, merchantId, paymentInfoData);
			return ResponseEntity.ok().build();
		}catch (NotAuthorizedException e) {
			logger.debug("MerchantController.addMerchantPaymetInfo() [NON_AUTHORITATIVE_INFORMATION] failed to add merchant payment info merchant " + merchantId + " by user " + userId );
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (UserNotFoundException e) {
			logger.debug("MerchantController.addMerchantPaymetInfo() [INVALID_USER] failed to add merchant payment info merchant " + merchantId + " by user " + userId );
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		}catch (Exception e) {
			logger.debug("MerchantController.addMerchantPaymetInfo() [FAILURE] failed to add merchant payment info merchant " + merchantId + " by user " + userId );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
}
