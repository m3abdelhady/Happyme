package com.happy.me.business;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardSummaryDto;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;

public interface CardBusiness {

	UserCardDto save(UserCardDto cardDto) throws BusinessException;

	UserCardDto getCard(UserCardDto cardDto) throws BusinessException;

	Optional<UserCardDto> getCardByNumber(String cardNumber) throws BusinessException;

	void addPoint(CardTransactionDto cardTransactionDto) throws BusinessException;

	List<UserCardDto> getUserCards(Long userId) throws BusinessException;

	Long getCardPoint(Long id) throws BusinessException;

	List<CardTransactionDto> getCardTransaction(Long cardId) throws BusinessException;

	Optional<CardSummaryDto> getCardSummaryByCard(UserCardDto userCardDto) throws BusinessException;

	void addCardSummary(CardSummaryDto cardSummaryDto) throws BusinessException;

	List<CardSummaryDto> getCardSummary(Long userId) throws BusinessException;

	List<CardSummaryDto> getCardSummaryByMerchant(Long merchantId) throws BusinessException;

	Long getTotalPoint(Long id, Date startDate, Date endDate) throws BusinessException;

	Long getTotalReedemPoint(Long id, Date startDate, Date endDate) throws BusinessException;

	void updateCardPoint(Long id, Long points) throws BusinessException;



	
}
