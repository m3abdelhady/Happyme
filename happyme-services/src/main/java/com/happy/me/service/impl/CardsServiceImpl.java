package com.happy.me.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.CardBusiness;
import com.happy.me.business.CouponsBusiness;
import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardDto;
import com.happy.me.common.dto.CardSummaryDto;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.common.rest.CardData;
import com.happy.me.service.CardsService;
import com.happy.me.service.exception.AgentNotFoundException;
import com.happy.me.service.exception.CardNotBelongToMerchantException;
import com.happy.me.service.exception.CardNotFoundException;
import com.happy.me.service.exception.MerchantMaxPointExceedException;
import com.happy.me.service.exception.MerchantMinPointExceedException;
import com.happy.me.service.exception.MerchantNotFoundException;
import com.happy.me.service.exception.MerchantRuleNotFoundException;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.ServiceException;
import com.happy.me.service.exception.UserMaxPointExceedException;
import com.happy.me.service.exception.UserNotFoundException;

@Service("cardsService")
public class CardsServiceImpl implements CardsService {

	@Autowired
	CouponsBusiness couponsBusiness;

	@Autowired
	UserBusiness userBusiness;

	@Autowired
	MerchantBusiness merchantBusiness;

	@Autowired
	CardBusiness cardBusiness;

	@Override
	public UserCardDto requestCard(UserCardDto cardDto) throws ServiceException {

		try {
			Optional<UserDto> userDto = userBusiness.getUserById(cardDto.getUserDto().getId());
			if (!userDto.isPresent()) {
				throw new UserNotFoundException("Exception while request Card");
			}

			if (!merchantBusiness.isPresent(cardDto.getMerchantDto().getId())) {
				throw new MerchantNotFoundException("Exception while request Card");
			}

//			 String cardNumber = cardDto.getUserDto().getId() +
//			 cardDto.getMerchantDto().getId() + Utils.getUniqueNumber();
//			 cardDto.setCardNumber(cardNumber);

			UserCardDto dto = cardBusiness.getCard(cardDto);
			if (dto != null) {
				return dto;
			}

			cardDto = cardBusiness.save(cardDto);
			
			CardSummaryDto cardSummaryDto = new CardSummaryDto();
			cardSummaryDto.setUserCardDto(cardDto);
			cardBusiness.addCardSummary(cardSummaryDto);

			return cardDto;
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}

	}

	@Override
	public void addPoint(Long merchantId, CardData data, Long agentId) throws ServiceException {
		try {
			Optional<UserCardDto> cardDto = cardBusiness.getCardByNumber(data.getCardNumber());
			if (!cardDto.isPresent()) {
				throw new CardNotFoundException("Card Not Found Exception");
			}
			if (cardDto.get().getMerchantDto().getId() != merchantId) {
				throw new CardNotBelongToMerchantException("Card Not Belong To Merchant Exception");
			}
			
			Optional<UserDto> optionalUser =userBusiness.getUserById(agentId);
			if (!optionalUser.isPresent()) {
				throw new AgentNotFoundException("Card Not Found Exception");
			}
			if (!optionalUser.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue()) || optionalUser.get().getMerchantDto().getId() != merchantId) {
				throw new NotAuthorizedException("Agent Not assign to merchant");
			}
			
			Double amount = Double.parseDouble(data.getAmount());
			
			MerchantRuleDto merchantRuleDto = merchantBusiness.getMerchantRule(merchantId);
			
			Long pointNumber = (long) (amount / merchantRuleDto.getPoint());
			
			CardTransactionDto cardTransactionDto = new CardTransactionDto(); 
			cardTransactionDto.setPoint(pointNumber);
			cardTransactionDto.setUserCardDto(cardDto.get());
			cardTransactionDto.setAmount(amount.toString());
			cardTransactionDto.setTransactionDate(new Date());
			cardTransactionDto.setTransactionType(data.getTransactionType());
			cardTransactionDto.setAgentDto(new UserDto(agentId));			
			
			CardSummaryDto cardSummaryDto = null;
			Optional<CardSummaryDto> dto = cardBusiness.getCardSummaryByCard(cardDto.get());
			if (dto.isPresent()) {
				cardSummaryDto = dto.get();
				cardSummaryDto.setPoint((cardSummaryDto.getPoint() == null ? 0 : cardSummaryDto.getPoint()) + pointNumber);
				cardSummaryDto.setUserCardDto(cardDto.get());
				cardSummaryDto.setStar(calculateStar(cardSummaryDto.getPoint(), merchantRuleDto));
				cardSummaryDto.setAmount(calculateReedemAmount(cardSummaryDto.getPoint(), merchantRuleDto));
				
			}else{
				cardSummaryDto = new CardSummaryDto();
				cardSummaryDto.setPoint(pointNumber);
				cardSummaryDto.setUserCardDto(cardDto.get());
				cardSummaryDto.setStar(calculateStar(cardSummaryDto.getPoint(), merchantRuleDto));
				cardSummaryDto.setAmount(calculateReedemAmount(cardSummaryDto.getPoint(), merchantRuleDto));
			}
			cardBusiness.addPoint(cardTransactionDto);
			cardBusiness.addCardSummary(cardSummaryDto);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Override
	public List<CardDto> getUserCards(Long userId) throws ServiceException {
		try {
			
			List<CardDto> cardDtos = new ArrayList<>();
//			List<UserCardDto> userCardDtos = cardBusiness.getUserCards(userId);
			List<CardSummaryDto> cardSummaryDtos = cardBusiness.getCardSummary(userId);
			
			for (CardSummaryDto cardSummaryDto : cardSummaryDtos) {
				System.out.println("CardsServiceImpl.getUserCards()");
				CardDto cardDto = new CardDto();
				cardDto.setId(cardSummaryDto.getUserCardDto().getId());
				cardDto.setCardNumber(cardSummaryDto.getUserCardDto().getCardNumber());
				cardDto.setMerchantId(cardSummaryDto.getUserCardDto().getMerchantDto().getId());
				cardDto.setPoint((cardSummaryDto.getPoint() == null ? 0 : cardSummaryDto.getPoint()));
				cardDto.setStar((cardSummaryDto.getStar() == null ? 0 : cardSummaryDto.getStar()));
				cardDto.setAmount((cardSummaryDto.getAmount() == null ? 0 : cardSummaryDto.getAmount()));
				cardDto.setRedeemedAmount((cardSummaryDto.getRedeemedAmount() == null ? 0 : cardSummaryDto.getRedeemedAmount()));
				
				cardDtos.add(cardDto);
			}
			
			
			
//			for (UserCardDto userCardDto : userCardDtos) {
//				CardDto cardDto = new CardDto();
//				cardDto.setCardNumber(userCardDto.getCardNumber());
//				cardDto.setMerchantId(userCardDto.getMerchantDto().getId());
//				MerchantRuleDto merchantRuleDto = merchantBusiness.getMerchantRule(userCardDto.getMerchantDto().getId());
//				Long point = cardBusiness.getCardPoint(userCardDto.getId());
//				cardDto.setPoint((point < 0 ? 0 : point));
//				cardDto.setStar(calculateStar(point, merchantRuleDto));
//				
//				cardDtos.add(cardDto);
//			}
			
			return cardDtos;
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}
	
	public Long calculateStar(Long point, MerchantRuleDto merchantRuleDto) {
		Long star = 0L;
		if (point >= merchantRuleDto.getStar1() && point < merchantRuleDto.getStar2()) {
			star = 1L;
		}else if (point >= merchantRuleDto.getStar2() && point < merchantRuleDto.getStar3()) {
			star = 2L;
		}else if (point >= merchantRuleDto.getStar3() && point < merchantRuleDto.getStar4()) {
			star = 3L;
		}else if (point >= merchantRuleDto.getStar4() && point < merchantRuleDto.getStar5()) {
			star = 4L;
		}else if (point >= merchantRuleDto.getStar5()){
			star = 5L;
		}
		
		return star;
	}
	
	public Double calculateReedemAmount(Long point, MerchantRuleDto merchantRuleDto) {
		return (point/merchantRuleDto.getPound());
	}

	@Override
	public List<CardTransactionDto> getCardTransaction(Long cardId) throws ServiceException {
		try{
			return cardBusiness.getCardTransaction(cardId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Double calculatePoint(Long merchantId, Long point) throws ServiceException {
		try{
			MerchantRuleDto merchantRuleDto = merchantBusiness.getMerchantRule(merchantId);
			if (merchantRuleDto == null) {
				throw new MerchantRuleNotFoundException("Exception while get feedback");
			}
			
			if (point > merchantRuleDto.getMax()) {
				throw new MerchantMaxPointExceedException("Exception while get feedback");
			}
			
			if (point < merchantRuleDto.getMin()) {
				throw new MerchantMinPointExceedException("Exception while get feedback");
			}
			Double reedemedAmount = calculateReedemAmount(point, merchantRuleDto);
			
			return reedemedAmount;
		
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Double redeemPoint(Long merchantId, Long agentId, CardData data) throws ServiceException {
		try{
			Optional<UserCardDto> cardDto = cardBusiness.getCardByNumber(data.getCardNumber());
			if (!cardDto.isPresent()) {
				throw new CardNotFoundException("Card Not Found Exception");
			}
			if (cardDto.get().getMerchantDto().getId() != merchantId) {
				throw new CardNotBelongToMerchantException("Card Not Belong To Merchant Exception");
			}
			
			Optional<UserDto> optionalUser =userBusiness.getUserById(agentId);
			if (!optionalUser.isPresent()) {
				throw new AgentNotFoundException("Card Not Found Exception");
			}
			if (!optionalUser.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue()) || optionalUser.get().getMerchantDto().getId() != merchantId) {
				throw new NotAuthorizedException("Exception while get feedback");
			}
			
			MerchantRuleDto merchantRuleDto = merchantBusiness.getMerchantRule(merchantId);
			if (merchantRuleDto == null) {
				throw new MerchantRuleNotFoundException("Exception while get feedback");
			}		
			if (data.getPoint() > merchantRuleDto.getMax()) {
				throw new MerchantMaxPointExceedException("Exception while get feedback");
			}		
			if (data.getPoint() < merchantRuleDto.getMin()) {
				throw new MerchantMinPointExceedException("Exception while get feedback");
			}
			Double reedemedAmount = calculateReedemAmount(data.getPoint(), merchantRuleDto);

			
			CardTransactionDto cardTransactionDto = new CardTransactionDto(); 
			cardTransactionDto.setPoint(data.getPoint());
			cardTransactionDto.setUserCardDto(cardDto.get());
			cardTransactionDto.setAmount(reedemedAmount.toString());
			cardTransactionDto.setTransactionDate(new Date());
			cardTransactionDto.setTransactionType(data.getTransactionType());
			cardTransactionDto.setAgentDto(new UserDto(agentId));
			
			CardSummaryDto cardSummaryDto = null;
			Optional<CardSummaryDto> dto = cardBusiness.getCardSummaryByCard(cardDto.get());
			if (dto.isPresent()) {
				cardSummaryDto = dto.get();
				if (data.getPoint() > cardSummaryDto.getPoint()) {
					throw new UserMaxPointExceedException("Exception while get feedback");
				}
				cardSummaryDto.setPoint(cardSummaryDto.getPoint() - data.getPoint());
				cardSummaryDto.setUserCardDto(cardDto.get());
				cardSummaryDto.setStar(calculateStar(cardSummaryDto.getPoint(), merchantRuleDto));
				cardSummaryDto.setRedeemedAmount((cardSummaryDto.getRedeemedAmount() == null ? 0 : cardSummaryDto.getRedeemedAmount()) + reedemedAmount);
				cardSummaryDto.setAmount(calculateReedemAmount((cardSummaryDto.getPoint() == null ? 0 : cardSummaryDto.getPoint()), merchantRuleDto));

			}else{
				// should not enter here
				cardSummaryDto = new CardSummaryDto();
				cardSummaryDto.setPoint(data.getPoint());
				cardSummaryDto.setUserCardDto(cardDto.get());
				cardSummaryDto.setStar(calculateStar(cardSummaryDto.getPoint(), merchantRuleDto));
				cardSummaryDto.setAmount(calculateReedemAmount(cardSummaryDto.getPoint(), merchantRuleDto));
			}
			cardBusiness.addPoint(cardTransactionDto);
			cardBusiness.addCardSummary(cardSummaryDto);
			
			return reedemedAmount;
		
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

}
