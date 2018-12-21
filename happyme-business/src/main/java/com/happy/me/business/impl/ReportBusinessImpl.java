package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.ReportBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.rest.MerchantCardReportData;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.dataaccess.extra.MerchantCardReport;
import com.happy.me.dataaccess.model.CardTransaction;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.model.UserCard;
import com.happy.me.dataaccess.repository.BillHeaderRepository;
import com.happy.me.dataaccess.repository.CardTransactionRepository;
import com.happy.me.dataaccess.repository.CreditDebitRepository;
import com.happy.me.dataaccess.repository.UserCardRepository;

@Service("reportBusiness")
public class ReportBusinessImpl implements ReportBusiness {
	@Autowired
	BillHeaderRepository billHeaderRepository;
	
	@Autowired
	CreditDebitRepository creditDebitRepository;

	@Autowired
	UserCardRepository userCardRepository;
	
	@Autowired
	CardTransactionRepository cardTransactionRepository;
	
	@Autowired
	private Mapper mapper;

	@Override
	public List<CardTransactionDto> getAgentReport(Long agentId, Date from, Date to) throws BusinessException {
		try {
			User user = new User(agentId);
			List<CardTransaction> cardTransactions = cardTransactionRepository.getAgentReport(user, from, to);
			List<CardTransactionDto> cardTransactionDtos = new ArrayList<>();
			if (cardTransactions != null) 
				cardTransactionDtos = DozerHelper.map(mapper, cardTransactions, CardTransactionDto.class, DozerMapping.CARDTRANSACTION_VS_CARDTRANSACTIONDTO.getKey());
			return cardTransactionDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while get agent report", e);
		}
	}

	@Override
	public List<UserCardDto> getMerchantCard(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant(merchantId);
			List<UserCard> cardTransactions = userCardRepository.getMerchantCard(merchant);
			List<UserCardDto> dtos = new ArrayList<>();
			if (cardTransactions != null) 
				dtos = DozerHelper.map(mapper, cardTransactions, UserCardDto.class, DozerMapping.USERCARD_VS_USERCARDDTO.getKey());
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while get merchant card", e);
		}
	}

	@Override
	public List<MerchantCardReportData> getAdminMerchantReport() throws BusinessException {
		try {
			List<MerchantCardReport> cardReports = userCardRepository.getMerchantReport();
			List<MerchantCardReportData> dtos = new ArrayList<>();
			if (cardReports != null) {
				for (MerchantCardReport cardReport : cardReports) {
					MerchantDto merchantDto = mapper.map(cardReport.getMerchant(), MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO_LOGO.getKey());
					MerchantCardReportData cardReportData = new MerchantCardReportData(merchantDto, cardReport.getCount());
					dtos.add(cardReportData);
				}
			}
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while get admin merchant report", e);
		}
	}

	@Override
	public Long getCardsNumber(Long merchantId) throws BusinessException {
		try {
			Long card = userCardRepository.getCardsNumber(new Merchant(merchantId));
			return card;
		} catch (Exception e) {
			throw new BusinessException("Exception while get card number report", e);
		}
	}

	@Override
	public Long getTransactionsNumber(Long merchantId) throws BusinessException {
		try {
			Long transaction = cardTransactionRepository.getTransactionsNumber(new Merchant(merchantId));
			return transaction;
		} catch (Exception e) {
			throw new BusinessException("Exception while get admin merchant report", e);
		}
	}

	@Override
	public Long getPointsNumber(Long merchantId) throws BusinessException {
		try {
			Long pointd = userCardRepository.getPointsNumber(new Merchant(merchantId));
			return pointd;
		} catch (Exception e) {
			throw new BusinessException("Exception while get admin merchant report", e);
		}
	}

	@Override
	public Long getRedeemNumber(Long merchantId) throws BusinessException {
		try {
			Long transaction = cardTransactionRepository.getRedeemNumber(new Merchant(merchantId));
			return transaction;
		} catch (Exception e) {
			throw new BusinessException("Exception while get admin merchant report", e);
		}
	}
	
	
}
