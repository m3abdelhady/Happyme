package com.happy.me.business;

import java.util.List;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.BillHeaderDto;
import com.happy.me.common.dto.CreditDebitDto;
import com.happy.me.common.dto.PaymentLogDto;

public interface PaymentBusiness {

	List<BillHeaderDto> getInvoiceList(Long merchantId) throws BusinessException;

	List<BillHeaderDto> getInvoiceDetails(Long merchantId) throws BusinessException;

	List<PaymentLogDto> getPaymentLogs(Long merchantId) throws BusinessException;

	List<CreditDebitDto> geStatment(Long merchantId) throws BusinessException;

	
}
