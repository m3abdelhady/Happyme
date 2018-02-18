package com.happy.me.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.me.business.PaymentBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.BillHeaderDto;
import com.happy.me.common.dto.CreditDebitDto;
import com.happy.me.common.dto.PaymentLogDto;
import com.happy.me.service.PaymentService;
import com.happy.me.service.exception.ServiceException;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentBusiness paymentBusiness;

	@Transactional(readOnly = true)
	@Override
	public List<BillHeaderDto> getInvoiceList(Long merchantId) throws ServiceException {
		try {
			return paymentBusiness.getInvoiceList(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating user", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<BillHeaderDto> getInvoiceDetails(Long merchantId) throws ServiceException {
		try {
			return paymentBusiness.getInvoiceDetails(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating user", e);
		}
	}

	@Override
	public List<PaymentLogDto> getPaymentLogs(Long merchantId) throws ServiceException {
		try {
			return paymentBusiness.getPaymentLogs(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating user", e);
		}
	}

	@Override
	public List<CreditDebitDto> geStatment(Long merchantId) throws ServiceException {
		try {
			return paymentBusiness.geStatment(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating user", e);
		}
	}

}
