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

import com.happy.me.common.dto.CardDto;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.CardsDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.CardData;
import com.happy.me.common.rest.Response;
import com.happy.me.common.rest.ResponseObject;
import com.happy.me.service.CardsService;
import com.happy.me.service.exception.AgentNotFoundException;
import com.happy.me.service.exception.CardNotBelongToMerchantException;
import com.happy.me.service.exception.CardNotFoundException;
import com.happy.me.service.exception.MerchantMaxPointExceedException;
import com.happy.me.service.exception.MerchantMinPointExceedException;
import com.happy.me.service.exception.MerchantNotFoundException;
import com.happy.me.service.exception.MerchantRuleNotFoundException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.UserMaxPointExceedException;
import com.happy.me.service.exception.UserNotFoundException;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/cards")
@EnableAutoConfiguration
public class CardsController {
	
	@Autowired
	CardsService cardsService;
	
	@Autowired
	Mapper mapper;

	Logger logger = Logger.getLogger(CardsController.class);
	
	@RequestMapping(value = "/{merchantId}/request/{userId}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> requestCard(@PathVariable("merchantId") Long merchantId, @PathVariable("userId") Long userId ) {
		logger.debug("CardsController.requestCard() start to request card for merchant id : " + merchantId + " for user id : " + userId);
		try {
			UserCardDto cardDto = new UserCardDto();
			cardDto.setUserDto(new UserDto(userId));
			cardDto.setMerchantDto(new MerchantDto(merchantId));
			
			cardDto = cardsService.requestCard(cardDto);
			
			return ResponseEntity.ok(cardDto);
		}catch (UserNotFoundException e) {
			logger.debug("CardsController.requestCard() [INVALID_USER] faild to request card for merchant id : " + merchantId + " for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_USER);
		}catch (MerchantNotFoundException e) {
			logger.debug("CardsController.requestCard() [INVALID_MERCHANT] faild to request card for merchant id : " + merchantId + " for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT);
		}catch (Exception e) {
			logger.debug("CardsController.requestCard() [FAILURE] faild to request card for merchant id : " + merchantId + " for user id : " + userId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchantId}/point/{agentId}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addPoint(@PathVariable("merchantId") Long merchantId, @PathVariable("agentId") Long agentId, @RequestBody CardData data ) {
		logger.debug("CardsController.addPoint() start to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber());
		try {
			cardsService.addPoint(merchantId, data, agentId);
			
			return ResponseEntity.ok().build();
		}catch (NotAuthorizedException e) {
			logger.debug("CardsController.addPoint() [NON_AUTHORITATIVE_INFORMATION] faild to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (CardNotFoundException e) {
			logger.debug("CardsController.addPoint() [INVALID_CARD] faild to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_CARD);
		}catch (CardNotBelongToMerchantException e) {
			logger.debug("CardsController.addPoint() [INVALID_MERCHANT_CARD] faild to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_CARD);
		}catch (AgentNotFoundException e) {
			logger.debug("CardsController.addPoint() [INVALID_AGENT] faild to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_AGENT);
		}catch (Exception e) {
			logger.debug("CardsController.addPoint() [FAILURE] faild to add point merchant id " + merchantId + " by agent id " + agentId + " for user card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getUserCards(@PathVariable("userId") Long userId ) {
		logger.debug("CardsController.getUserCards() start to get user card for user id " + userId );
		try {
			CardsDto cardsDto = new CardsDto();
			List<CardDto> cardDto = cardsService.getUserCards(userId);
			cardsDto.setCards(cardDto);
			return ResponseEntity.ok(cardsDto);
		}catch (Exception e) {
			logger.debug("CardsController.getUserCards() [FAILURE] failed to get user card for user id " + userId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{cardId}/transaction", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getCardTransaction(@PathVariable("cardId") Long cardId ) {
		logger.debug("CardsController.getCardTransaction() start to get card transaction for card id " + cardId );
		try {
			List<CardTransactionDto> cardTransactionDtos = cardsService.getCardTransaction(cardId);
			
			return ResponseEntity.ok(new Response(cardTransactionDtos));
		}catch (Exception e) {
			logger.debug("CardsController.getCardTransaction() [FAILURE] faild to get card transaction for card id " + cardId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	
	@RequestMapping(value = "/{merchantId}/calculate/{point}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> calculatePoint(@PathVariable("merchantId") Long merchantId, @PathVariable("point") Long point) {
		logger.debug("CardsController.calculatePoint() start to calculate point for merchant id " + merchantId + " point" + point);
		try {
			Double d = cardsService.calculatePoint(merchantId, point);
			
			return ResponseEntity.ok(new ResponseObject<Double>(d));
		}catch (MerchantRuleNotFoundException e) {
			logger.debug("CardsController.calculatePoint() [INVALID_MERCHANT_RULE] faild to calculate point for merchant id " + merchantId + " point" + point, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_RULE);
		}catch (MerchantMaxPointExceedException e) {
			logger.debug("CardsController.calculatePoint() [MAX_POINT_REDEEM] faild to calculate point for merchant id " + merchantId + " point" + point, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.MAX_POINT_REDEEM);
		}catch (MerchantMinPointExceedException e) {
			logger.debug("CardsController.calculatePoint() [MIN_POINT_REDEEM] faild to calculate point for merchant id " + merchantId + " point" + point, e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.MIN_POINT_REDEEM);
		}catch (Exception e) {
			logger.debug("CardsController.calculatePoint() [FAILURE] faild to calculate point for merchant id " + merchantId + " point" + point, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchantId}/redeem/{agentId}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> redeemPoint(@PathVariable("merchantId") Long merchantId, @PathVariable("agentId") Long agentId, @RequestBody CardData data ) {
		logger.debug("CardsController.redeemPoint() start to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber());
		try {
			Double d = cardsService.redeemPoint(merchantId, agentId, data);
			
			return ResponseEntity.ok(new ResponseObject<Double>(d));
		}catch (NotAuthorizedException e) {
			logger.debug("CardsController.redeemPoint() [NON_AUTHORITATIVE_INFORMATION] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.NON_AUTHORITATIVE_INFORMATION);
		}catch (CardNotFoundException e) {
			logger.debug("CardsController.redeemPoint() [INVALID_CARD] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_CARD);
		}catch (CardNotBelongToMerchantException e) {
			logger.debug("CardsController.redeemPoint() [INVALID_MERCHANT_CARD] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_CARD);
		}catch (AgentNotFoundException e) {
			logger.debug("CardsController.redeemPoint() [INVALID_AGENT] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_AGENT);
		}catch (MerchantRuleNotFoundException e) {
			logger.debug("CardsController.redeemPoint() [INVALID_MERCHANT_RULE] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.INVALID_MERCHANT_RULE);
		}catch (MerchantMaxPointExceedException e) {
			logger.debug("CardsController.redeemPoint() [MAX_POINT_REDEEM] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.MAX_POINT_REDEEM);
		}catch (MerchantMinPointExceedException e) {
			logger.debug("CardsController.redeemPoint() [MIN_POINT_REDEEM] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.MIN_POINT_REDEEM);
		}catch (UserMaxPointExceedException e) {
			logger.debug("CardsController.redeemPoint() [MAX_POINT_REDEEM] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.BAD_REQUEST, AppErrorCode.MAX_POINT_REDEEM);
		}catch (Exception e) {
			logger.debug("CardsController.redeemPoint() [FAILURE] faild to redeem merchant id " + merchantId + " by agent id " + agentId + " card : " + data.getCardNumber(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
}
