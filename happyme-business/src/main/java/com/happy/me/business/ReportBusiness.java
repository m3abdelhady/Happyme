package com.happy.me.business;

import java.util.Date;
import java.util.List;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.rest.MerchantCardReportData;

public interface ReportBusiness {

	List<CardTransactionDto> getAgentReport(Long agentId, Date from, Date to) throws BusinessException;

	List<UserCardDto> getMerchantCard(Long merchantId) throws BusinessException;

	List<MerchantCardReportData> getAdminMerchantReport() throws BusinessException;

	Long getCardsNumber(Long merchantId) throws BusinessException;

	Long getTransactionsNumber(Long merchantId) throws BusinessException;

	Long getPointsNumber(Long merchantId) throws BusinessException;

	Long getRedeemNumber(Long merchantId) throws BusinessException;

	
}
