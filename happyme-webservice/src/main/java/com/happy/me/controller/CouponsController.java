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
import org.springframework.web.bind.annotation.RestController;

import com.happy.me.common.dto.CouponsDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.CardData;
import com.happy.me.common.rest.CouponsData;
import com.happy.me.common.rest.CouponsResponse;
import com.happy.me.service.CouponsService;
import com.happy.me.service.exception.CardNotBelongToMerchantException;
import com.happy.me.service.exception.CardNotFoundException;
import com.happy.me.service.exception.InvalidAgentException;
import com.happy.me.service.exception.InvalidCouponeException;
import com.happy.me.service.exception.MerchantCouponeInvalidException;
import com.happy.me.service.exception.UserNotFoundException;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/coupons")
@EnableAutoConfiguration
public class CouponsController {
	
	@Autowired
	CouponsService couponsService ;
	
	@Autowired
	Mapper mapper;

	Logger logger = Logger.getLogger(CouponsController.class);
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantCoupons( @PathVariable("merchantId") Long merchantId) {
		logger.debug("CouponsController.getMerchantCoupons() start to get merchant coupons for mrchant id " + merchantId);
		try {
			List<CouponsDto> list = couponsService.getCoupons(merchantId);
			CouponsResponse couponsResponse = new CouponsResponse(list);
			return ResponseEntity.ok(couponsResponse);
		}catch (Exception e) {
			logger.debug("CouponsController.getMerchantCoupons() [FAILURE] failed to get merchant coupons for mrchant id " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addCoupons(@RequestBody CouponsData data, @PathVariable("id") Long merchantId) {
		logger.debug("CouponsController.addCoupons() start to add coupons for mechant id " + merchantId);
		try {
			CouponsDto dto = mapper.map(data, CouponsDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = couponsService.addCoupons(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("CouponsController.addCoupons() [FAILURE] failed to add coupons for mechant id " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateCoupons(@RequestBody CouponsData data, @PathVariable("merchantId") Long merchantId) {
		logger.debug("CouponsController.updateCoupons() start to update coupons for mechant id " + merchantId);
		try {
			CouponsDto dto = mapper.map(data, CouponsDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = couponsService.updateCoupons(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("CouponsController.updateCoupons() [FAILURE] failed to update coupons for mechant id " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{couponsid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCoupons( @PathVariable("couponsid") Long couponsid) {
		logger.debug("CouponsController.deleteCoupons() start to delete coupon id " + couponsid);
		try {
			couponsService.deleteCoupons(couponsid); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("CouponsController.deleteCoupons() [FAILURE] failed to delete coupon id " + couponsid, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	@RequestMapping(value = "/active/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getCoupons( @PathVariable("userid") Long userId) {
		logger.debug("CouponsController.getCoupons() start to get active coupons for user id " + userId);
		try {
			List<CouponsDto> dtos = couponsService.getUserCoupons(userId); 
			CouponsResponse couponsResponse = new CouponsResponse(dtos);
			return ResponseEntity.ok(couponsResponse);
		}catch (Exception e) {
			logger.debug("CouponsController.getCoupons() [FAILURE] failed to get active coupons for user id " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}/active", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantActiveCoupons( @PathVariable("merchantId") Long merchantId) {
		logger.debug("CouponsController.getMerchantActiveCoupons() start to get active coupone for merchant id " + merchantId);
		try {
			List<CouponsDto> list = couponsService.getActiveCoupons(merchantId);
			CouponsResponse couponsResponse = new CouponsResponse(list);
			return ResponseEntity.ok(couponsResponse);
		}catch (Exception e) {
			logger.debug("CouponsController.getMerchantActiveCoupons() [FAILURE] failed to get active coupone for merchant id " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{coponeId}/merchant/{merchantId}/user/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addCoponeToUser(@PathVariable("userId") Long userId, @PathVariable("coponeId") Long coponeId, @PathVariable("merchantId") Long merchantId) {
		logger.debug("CouponsController.addCoponeToUser() start to add coupone " + coponeId + " , merchant id " + merchantId + " to user " + userId);
		try {
			couponsService.addCoponeToUser(userId, coponeId, merchantId);
			return ResponseEntity.ok().build();
		}catch (InvalidCouponeException e) {
			logger.debug("CouponsController.addCoponeToUser() [INVALID_COUPONE] failed to add coupone " + coponeId + " , merchant id " + merchantId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_COUPONE);
		}catch (MerchantCouponeInvalidException e) {
			logger.debug("CouponsController.addCoponeToUser() [MERCHANT_COUPONE_INVALID] failed to add coupone " + coponeId + " , merchant id " + merchantId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.MERCHANT_COUPONE_INVALID);
		}catch (Exception e) {
			logger.debug("CouponsController.addCoponeToUser() [FAILURE] failed to add coupone " + coponeId + " , merchant id " + merchantId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}
	
	@RequestMapping(value = "/{coponeId}/agent/{agentId}/user/{userId}", method = RequestMethod.POST)
	public ResponseEntity<?> addCoponeToUserFromAgent(@PathVariable("userId") Long userId, @PathVariable("coponeId") Long coponeId, @PathVariable("agentId") Long agentId) {
		logger.debug("CouponsController.addCoponeToUserFromAgent() start to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId);
		try {
			couponsService.addCoponeToUserByAgent(userId, coponeId, agentId);
			return ResponseEntity.ok().build();
		}catch (InvalidCouponeException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent() [INVALID_COUPONE] failed to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_COUPONE);
		}catch (MerchantCouponeInvalidException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent() [MERCHANT_COUPONE_INVALID] failed to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.MERCHANT_COUPONE_INVALID);
		}catch (UserNotFoundException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent() [INVALID_AGENT] failed to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_AGENT);
		}catch (InvalidAgentException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent() [INVALID_USER] failed to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_USER);
		}catch (Exception e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent() [FAILURE] failed to add coupone " + coponeId + " , agent id " + agentId + " to user " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}
	
	@RequestMapping(value = "/agent/{agentId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantActiveCouponsByAgent( @PathVariable("agentId") Long agentId) {
		logger.debug("CouponsController.getMerchantActiveCouponsByAgent() start to get active coupone for merchant by agent id " + agentId);
		try {
			List<CouponsDto> list = couponsService.getActiveCouponsByAgent(agentId);
			CouponsResponse couponsResponse = new CouponsResponse(list);
			return ResponseEntity.ok(couponsResponse);
		}catch (UserNotFoundException e) {
			logger.debug("CouponsController.getMerchantActiveCouponsByAgent() [INVALID_AGENT] failed to get active coupone for merchant by agent id " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_AGENT);
		}catch (Exception e) {
			logger.debug("CouponsController.getMerchantActiveCouponsByAgent() [FAILURE] failed to get active coupone for merchant by agent id " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }

	
	@RequestMapping(value = "/{coponeId}/agent/{agentId}", method = RequestMethod.POST)
	public ResponseEntity<?> addCoponeToUserFromAgent2(@RequestBody CardData data, @PathVariable("coponeId") Long coponeId, @PathVariable("agentId") Long agentId) {
		logger.debug("CouponsController.addCoponeToUserFromAgent2() start to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber() );
		try {
			couponsService.addCoponeToUserByCardNumber(data.getCardNumber(), coponeId, agentId);
			return ResponseEntity.ok().build();
		}catch (InvalidCouponeException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [INVALID_COUPONE] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_COUPONE);
		}catch (MerchantCouponeInvalidException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [MERCHANT_COUPONE_INVALID] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.MERCHANT_COUPONE_INVALID);
		}catch (UserNotFoundException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [INVALID_AGENT] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_AGENT);
		}catch (InvalidAgentException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [INVALID_USER] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_USER);
		}catch (CardNotFoundException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [INVALID_CARD] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_CARD);
		}catch (CardNotBelongToMerchantException e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [INVALID_MERCHANT_CARD] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_MERCHANT_CARD);
		}catch (Exception e) {
			logger.debug("CouponsController.addCoponeToUserFromAgent2() [FAILURE] failed to add coupon " + coponeId + " by agent " + agentId + " for user card number : " +  data.getCardNumber(), e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}
	
	@RequestMapping(value = "/card/{card}/agent/{agentId}", method = RequestMethod.GET)
    public ResponseEntity<?> getCouponsForUser(@PathVariable("card") String card, @PathVariable("agentId") Long agentId){
		logger.debug("CouponsController.getCouponsForUser() start to get coupones for user card : " + card + " by agent " + agentId);
		try {
			List<CouponsDto> dtos = couponsService.getCouponsForUser(card, agentId); 
			CouponsResponse couponsResponse = new CouponsResponse(dtos);
			return ResponseEntity.ok(couponsResponse);
		}catch (InvalidCouponeException e) {
			logger.debug("CouponsController.getCouponsForUser() [INVALID_COUPONE] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_COUPONE);
		}catch (MerchantCouponeInvalidException e) {
			logger.debug("CouponsController.getCouponsForUser() [MERCHANT_COUPONE_INVALID] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.MERCHANT_COUPONE_INVALID);
		}catch (UserNotFoundException e) {
			logger.debug("CouponsController.getCouponsForUser() [INVALID_AGENT] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_AGENT);
		}catch (InvalidAgentException e) {
			logger.debug("CouponsController.getCouponsForUser() [INVALID_USER] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_USER);
		}catch (CardNotFoundException e) {
			logger.debug("CouponsController.getCouponsForUser() [INVALID_CARD] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_CARD);
		}catch (CardNotBelongToMerchantException e) {
			logger.debug("CouponsController.getCouponsForUser() [INVALID_MERCHANT_CARD] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_MERCHANT_CARD);
		}catch (Exception e) {
			logger.debug("CouponsController.getCouponsForUser() [FAILURE] failed to get coupones for user card : " + card + " by agent " + agentId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	@RequestMapping(value = "/redeem/{coponeId}/agent/{agentId}", method = RequestMethod.POST)
	public ResponseEntity<?> redeemCopone(@RequestBody CardData data, @PathVariable("coponeId") Long coponeId, @PathVariable("agentId") Long agentId) {
		logger.debug("CouponsController.redeemCopone() start to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber()  );
		try {
			couponsService.redeemCopone(coponeId, agentId);
			return ResponseEntity.ok().build();
		}catch (InvalidCouponeException e) {
			logger.debug("CouponsController.redeemCopone() [INVALID_COUPONE] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_COUPONE);
		}catch (MerchantCouponeInvalidException e) {
			logger.debug("CouponsController.redeemCopone() [MERCHANT_COUPONE_INVALID] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.MERCHANT_COUPONE_INVALID);
		}catch (UserNotFoundException e) {
			logger.debug("CouponsController.redeemCopone() [INVALID_AGENT] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_AGENT);
		}catch (InvalidAgentException e) {
			logger.debug("CouponsController.redeemCopone() [INVALID_USER] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_USER);
		}catch (CardNotFoundException e) {
			logger.debug("CouponsController.redeemCopone() [INVALID_CARD] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_CARD);
		}catch (CardNotBelongToMerchantException e) {
			logger.debug("CouponsController.redeemCopone() [INVALID_MERCHANT_CARD] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.INVALID_MERCHANT_CARD);
		}catch (Exception e) {
			logger.debug("CouponsController.redeemCopone() [FAILURE] failed to redeem coupone id " + coponeId + " by agent " + agentId + " for user card number " + data.getCardNumber(), e  );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
	}
	
	
}
