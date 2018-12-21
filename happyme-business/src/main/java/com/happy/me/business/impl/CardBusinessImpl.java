package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.CardBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardSummaryDto;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.common.util.Utils;
import com.happy.me.dataaccess.model.CardSummary;
import com.happy.me.dataaccess.model.CardTransaction;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.model.UserCard;
import com.happy.me.dataaccess.repository.CardSummaryRepository;
import com.happy.me.dataaccess.repository.CardTransactionRepository;
import com.happy.me.dataaccess.repository.UserCardRepository;
import com.happy.me.dataaccess.repository.UserRepository;

@Service("cardBusiness")
public class CardBusinessImpl implements CardBusiness {

	@Autowired
	private Mapper mapper;

	@Autowired
	private UserCardRepository cardRepository;
	
	@Autowired
	private CardTransactionRepository cardTransactionRepository;
	
	@Autowired
	private CardSummaryRepository cardSummaryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserCardDto save(UserCardDto cardDto) throws BusinessException {
		try {
			UserCard card = mapper.map(cardDto, UserCard.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());

			card = cardRepository.save(card);
			String cardNumber;
			if (card.getId().toString().length() < 8) {
				cardNumber = card.getId() + "" + cardDto.getUserDto().getId() + "" +  cardDto.getMerchantDto().getId() + "" +  Utils.getUniqueNumber();	
				cardNumber = cardNumber.substring(0, 8);
			} else {
				cardNumber = card.getId().toString();
			}
			card.setCardNumber(cardNumber);
			card = cardRepository.save(card);
			return mapper.map(card, UserCardDto.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public UserCardDto getCard(UserCardDto cardDto) throws BusinessException {
		try {
			UserCard card = mapper.map(cardDto, UserCard.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());

			Optional<UserCard> cardO = cardRepository.getUserCard(card.getUser(), card.getMerchant());
			
			if (!cardO.isPresent()) {
				return null;
			}
			return mapper.map(cardO.get(), UserCardDto.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Optional<UserCardDto> getCardByNumber(String cardNumber) throws BusinessException {
		try {
			Optional<UserCard> cardO = cardRepository.findByCardNumber(cardNumber);
			
			UserCardDto cardDto = null;
			if (cardO.isPresent()) {
				cardDto = mapper.map(cardO.get(), UserCardDto.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());
			}
			return Optional.ofNullable(cardDto);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void addPoint(CardTransactionDto cardTransactionDto) throws BusinessException {
		try {
			CardTransaction cardTransaction = mapper.map(cardTransactionDto, CardTransaction.class);
			UserCard card = new UserCard();
			card.setId(cardTransactionDto.getUserCardDto().getId());
			cardTransaction.setUserCard(card);
			cardTransaction.setAgent(userRepository.findOne(cardTransactionDto.getAgentDto().getId()));
			
			cardTransactionRepository.save(cardTransaction);
			
			
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}		
	}

	@Override
	public List<UserCardDto> getUserCards(Long userId) throws BusinessException {
		try {
			User user = new User(userId);
			List<UserCard> card = cardRepository.getUserCards(user);
			List<UserCardDto> cardDto = new ArrayList<>();
			if (card != null) 
				cardDto = DozerHelper.map(mapper, card, UserCardDto.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());
			return cardDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Long getCardPoint(Long id) throws BusinessException {
		try {
			UserCard userCard = new UserCard();
			userCard.setId(id);
			Long income = cardTransactionRepository.getIncomeCardPoint(userCard);
			Long outcome = cardTransactionRepository.getOutcomeCardPoint(userCard);

			return (income == null ? 0 : income) - (outcome == null ? 0 : outcome);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public List<CardTransactionDto> getCardTransaction(Long cardId) throws BusinessException {
		try {
			UserCard userCard = new UserCard();
			userCard.setId(cardId);
			List<CardTransaction> cardTransactions = cardTransactionRepository.findByUserCard(userCard);
			List<CardTransactionDto> cardTransactionDtos = new ArrayList<>();
			if (cardTransactions != null) 
				cardTransactionDtos = DozerHelper.map(mapper, cardTransactions, CardTransactionDto.class);
			
			return cardTransactionDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Optional<CardSummaryDto> getCardSummaryByCard(UserCardDto userCardDto) throws BusinessException {
		try {
			UserCard userCard = new UserCard();
			userCard.setId(userCardDto.getId());
			Optional<CardSummary> optional = cardSummaryRepository.findByUserCard(userCard);
			
			if (!optional.isPresent()) 
				return Optional.ofNullable(null);
			
			CardSummaryDto cardSummaryDto = mapper.map(optional.get(), CardSummaryDto.class, DozerMapping.CARDSUMMARY_VS_CARDSUMMARYDTO.getKey());
	
			return Optional.of(cardSummaryDto);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void addCardSummary(CardSummaryDto cardSummaryDto) throws BusinessException {
		try {		
			CardSummary cardSummary = mapper.map(cardSummaryDto, CardSummary.class, DozerMapping.CARDSUMMARY_VS_CARDSUMMARYDTO.getKey());
			cardSummaryRepository.save(cardSummary);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
		
	}

	@Override
	public List<CardSummaryDto> getCardSummary(Long userId) throws BusinessException {
		try {		
			User user = new User(userId);
			List<UserCard> card = cardRepository.getUserCards(user);
			
			List<CardSummary> cardSummary = cardSummaryRepository.findByUserCardIn(card);
			
			List<CardSummaryDto> cardDto = new ArrayList<>();
			if (card != null) 
				cardDto = DozerHelper.map(mapper, cardSummary, CardSummaryDto.class, DozerMapping.CARDSUMMARY_VS_CARDSUMMARYDTO.getKey());
			return cardDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public List<CardSummaryDto> getCardSummaryByMerchant(Long merchantId) throws BusinessException {
		try {		
			Merchant merchant = new Merchant(merchantId);
			
			List<CardSummary> cardSummary = cardSummaryRepository.findByMerchant(merchant);
			
			List<CardSummaryDto> cardDto = new ArrayList<>();
			if (cardSummary != null) 
				cardDto = DozerHelper.map(mapper, cardSummary, CardSummaryDto.class, DozerMapping.CARDSUMMARY_VS_CARDSUMMARYDTO.getKey());
			return cardDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public Long getTotalPoint(Long id, Date startDate, Date endDate) throws BusinessException {
		try {
			UserCard userCard = new UserCard();
			userCard.setId(id);
			Long income = cardTransactionRepository.getTotalPoint(userCard,  startDate,  endDate);

			return (income == null ? 0 : income);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}
	
	@Override
	public Long getTotalReedemPoint(Long id, Date startDate, Date endDate) throws BusinessException {
		try {
			UserCard userCard = new UserCard();
			userCard.setId(id);
			Long outcome = cardTransactionRepository.getTotalReedemPoint(userCard,  startDate,  endDate);

			return (outcome == null ? 0 : outcome);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void updateCardPoint(Long id, Long points) throws BusinessException {
		try {
			CardSummary cardSummary = cardSummaryRepository.findOne(id);
			cardSummary.setPoint(points);
			cardSummaryRepository.save(cardSummary);

		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
		
	}



}
