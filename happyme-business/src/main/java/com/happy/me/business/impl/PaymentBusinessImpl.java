package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.PaymentBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.BillHeaderDto;
import com.happy.me.common.dto.BillLinesDto;
import com.happy.me.common.dto.CreditDebitDto;
import com.happy.me.common.dto.PaymentLogDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.dataaccess.model.BillHeader;
import com.happy.me.dataaccess.model.BillLines;
import com.happy.me.dataaccess.model.CreditDebit;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.PaymentLog;
import com.happy.me.dataaccess.repository.BillHeaderRepository;
import com.happy.me.dataaccess.repository.CreditDebitRepository;
import com.happy.me.dataaccess.repository.PaymentLogRepository;

@Service("paymentBusiness")
public class PaymentBusinessImpl implements PaymentBusiness {
	@Autowired
	BillHeaderRepository billHeaderRepository;
	
	@Autowired
	CreditDebitRepository creditDebitRepository;

	@Autowired
	PaymentLogRepository paymentLogRepository;
	
	@Autowired
	private Mapper mapper;
	
	@Override
	public List<BillHeaderDto> getInvoiceList(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant(merchantId);
			List<BillHeader> billHeaders = billHeaderRepository.findByMerchant(merchant);
			List<BillHeaderDto> dtos = null;
			if (billHeaders != null) {
				dtos = DozerHelper.map(mapper, billHeaders, BillHeaderDto.class);			
			}
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating user", e);
		}
	}

	@Override
	public List<BillHeaderDto> getInvoiceDetails(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant(merchantId);
			List<BillHeader> billHeaders = billHeaderRepository.findByMerchant(merchant);
			List<BillHeaderDto> dtos = new ArrayList<>();
			if (billHeaders != null) {
				//dtos = DozerHelper.map(mapper, billHeaders, BillHeaderDto.class, DozerMapping.BILLHEADER_VS_BILLHEADERDTO.getKey());			
				for (BillHeader billHeader : billHeaders) {
					BillHeaderDto billHeaderDto = new BillHeaderDto();
					billHeaderDto = mapper.map(billHeader, BillHeaderDto.class, DozerMapping.BILLHEADER_VS_BILLHEADERDTO.getKey());
					List<BillLinesDto> linesDtos = new ArrayList<>();
					for (BillLines billLines : billHeader.getBillLines()) {
						BillLinesDto billLinesDto = new BillLinesDto();
						billLinesDto = mapper.map(billLines, BillLinesDto.class);
						linesDtos.add(billLinesDto);
					}
					billHeaderDto.setBillLinesDtos(linesDtos);
					dtos.add(billHeaderDto);
				}
			}
			
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating user", e);
		}
	}

	@Override
	public List<PaymentLogDto> getPaymentLogs(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant(merchantId);
			List<PaymentLog> paymentLogs = paymentLogRepository.findByMerchant(merchant);
			List<PaymentLogDto> dtos = null;
			if (paymentLogs != null) {
				dtos = DozerHelper.map(mapper, paymentLogs, PaymentLogDto.class);			
			}
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating user", e);
		}
	}

	@Override
	public List<CreditDebitDto> geStatment(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant(merchantId);
			List<CreditDebit> creditDebits = creditDebitRepository.findByMerchant(merchant);
			List<CreditDebitDto> dtos = null;
			if (creditDebits != null) {
				dtos = DozerHelper.map(mapper, creditDebits, CreditDebitDto.class, DozerMapping.CREDITDEBIT_VS_CREDITDEBITDTO.getKey());			
			}
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating user", e);
		}
	}

}
