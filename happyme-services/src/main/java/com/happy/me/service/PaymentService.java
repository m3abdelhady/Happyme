package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.BillHeaderDto;
import com.happy.me.common.dto.CreditDebitDto;
import com.happy.me.common.dto.PaymentLogDto;
import com.happy.me.service.exception.ServiceException;

public interface PaymentService {

	List<BillHeaderDto> getInvoiceList(Long merchantId) throws ServiceException;

	List<BillHeaderDto> getInvoiceDetails(Long merchantId) throws ServiceException;

	List<PaymentLogDto> getPaymentLogs(Long merchantId) throws ServiceException;

	List<CreditDebitDto> geStatment(Long merchantId) throws ServiceException;

}
