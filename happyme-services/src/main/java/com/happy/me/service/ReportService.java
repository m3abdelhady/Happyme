package com.happy.me.service;

import java.util.Date;
import java.util.List;

import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.rest.MerchantCardReportData;
import com.happy.me.common.rest.MerchantSummary;
import com.happy.me.service.exception.ServiceException;

public interface ReportService {

	List<CardTransactionDto> getAgentReport(Long agentId, Date from, Date to) throws ServiceException;

	List<UserCardDto> getMerchantCard(Long merchantId) throws ServiceException;

	List<MerchantCardReportData> getAdminMerchantReport(Long adminId) throws ServiceException;

	MerchantSummary getAdminMerchantSummary(Long merchantId) throws ServiceException;


}
