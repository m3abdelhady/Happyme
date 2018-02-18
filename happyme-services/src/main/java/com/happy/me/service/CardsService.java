package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.CardDto;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.rest.CardData;
import com.happy.me.service.exception.ServiceException;

public interface CardsService {

	UserCardDto requestCard(UserCardDto cardDto) throws ServiceException;

	void addPoint(Long merchantId, CardData data, Long agentId) throws ServiceException;

	List<CardDto> getUserCards(Long userId) throws ServiceException;

	List<CardTransactionDto> getCardTransaction(Long cardId) throws ServiceException;

	Double calculatePoint(Long merchantId, Long point) throws ServiceException;

	Double redeemPoint(Long merchantId, Long agentId, CardData data) throws ServiceException;

}
