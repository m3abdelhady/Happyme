package com.happy.me.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.happy.me.common.dto.BillHeaderDto;
import com.happy.me.common.dto.CreditDebitDto;
import com.happy.me.common.dto.PaymentLogDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.Response;
import com.happy.me.service.PaymentService;
import com.happy.me.service.UserService;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/payment")
@EnableAutoConfiguration
public class PaymentController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	Mapper mapper;
	
	Logger logger = Logger.getLogger(PaymentController.class);


	@RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> login() {
      return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getInvoiceList(@PathVariable("merchantId") Long merchantId ) {
		logger.debug("PaymentController.getInvoiceList() start to get invoice list for merchant id " + merchantId );
		try {
			List<BillHeaderDto> billHeaderDtos = paymentService.getInvoiceList(merchantId);
			return ResponseEntity.ok(new Response(billHeaderDtos));
		}catch (Exception e) {
			logger.debug("PaymentController.getInvoiceList() [FAILURE] faild to get invoice list for merchant id " + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/details/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getInvoiceDetails(@PathVariable("merchantId") Long merchantId ) {
		logger.debug("PaymentController.getInvoiceDetails() start to get invoice details for merchant id " + merchantId );
		try {
			List<BillHeaderDto> billHeaderDtos = paymentService.getInvoiceDetails(merchantId);
			return ResponseEntity.ok(new Response(billHeaderDtos));
		}catch (Exception e) {
			logger.debug("PaymentController.getInvoiceDetails() [FAILURE] faild to get invoice details for merchant id " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/logs/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getPaymentLogs(@PathVariable("merchantId") Long merchantId ) {
		logger.debug("PaymentController.getPaymentLogs() start to get payment logs for merchant id " + merchantId );
		try {
			List<PaymentLogDto> paymentLogDtos = paymentService.getPaymentLogs(merchantId);
			return ResponseEntity.ok(new Response(paymentLogDtos));
		}catch (Exception e) {
			logger.debug("PaymentController.getPaymentLogs() [FAILURE] faild to get payment logs for merchant id " + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/statment/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getStatment(@PathVariable("merchantId") Long merchantId ) {
		logger.debug("PaymentController.getStatment() start to get statment for merchant id " + merchantId );
		try {
			List<CreditDebitDto> creditDebitDtos = paymentService.geStatment(merchantId);
			return ResponseEntity.ok(new Response(creditDebitDtos));
		}catch (Exception e) {
			logger.debug("PaymentController.getStatment() [FAILURE] failed to get statment for merchant id " + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }

}
