package com.happy.me.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.me.business.ReportBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.rest.MerchantCardReportData;
import com.happy.me.common.rest.MerchantSummary;
import com.happy.me.service.ReportService;
import com.happy.me.service.exception.ServiceException;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportBusiness reportBusiness;
	
	@Transactional(readOnly = true)
	@Override
	public List<CardTransactionDto> getAgentReport(Long agentId, Date from, Date to) throws ServiceException {
		try {
			return reportBusiness.getAgentReport(agentId, from, to);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get agent report", e);
		}
	}

	@Override
	public List<UserCardDto> getMerchantCard(Long merchantId) throws ServiceException {
		try {
			return reportBusiness.getMerchantCard(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get merchant card", e);
		}
	}

	@Override
	public List<MerchantCardReportData> getAdminMerchantReport(Long adminId) throws ServiceException {
		try {
			return reportBusiness.getAdminMerchantReport();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get merchant card", e);
		}
	}

	@Override
	public MerchantSummary getAdminMerchantSummary(Long merchantId) throws ServiceException {
		try {
			Long cards = reportBusiness.getCardsNumber(merchantId);
			Long transaction = reportBusiness.getTransactionsNumber(merchantId);
			Long points = reportBusiness.getPointsNumber(merchantId);
			Long reedem = reportBusiness.getRedeemNumber(merchantId);
			
			MerchantSummary merchantSummary = new MerchantSummary();
			merchantSummary.setAmounts(reedem);
			merchantSummary.setCards(cards);
			merchantSummary.setPoint(points);
			merchantSummary.setTransactions(transaction);
			
			return merchantSummary;
		} catch (BusinessException e) {
			throw new ServiceException("Exception while get merchant card", e);
		}
	}

	
}
