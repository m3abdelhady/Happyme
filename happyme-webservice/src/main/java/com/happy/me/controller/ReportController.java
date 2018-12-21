package com.happy.me.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.happy.me.common.dto.CardTransactionDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.MerchantCardReportData;
import com.happy.me.common.rest.MerchantSummary;
import com.happy.me.common.rest.Response;
import com.happy.me.service.ReportService;
import com.happy.me.service.UserService;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/report")
@EnableAutoConfiguration
public class ReportController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	Mapper mapper;
	
	Logger logger = Logger.getLogger(ReportController.class);


	@RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<?> login() {
      return ResponseEntity.ok().build();
    }
	
	@RequestMapping(value = "/agent/{agentId}/{from}/{to}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getAgentReport(@PathVariable("agentId") Long agentId, @PathVariable("from") String from, @PathVariable("to") String to ) {
		logger.debug("ReportController.getAgentReport() start to get report for agent id " + agentId );
		try {
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    Date fromDate = formatter.parse(from);
		    Date toDate = formatter.parse(to);
		    List<CardTransactionDto> dtos = reportService.getAgentReport(agentId, fromDate, toDate);
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("ReportController.getAgentReport() [FAILURE] faild to get report for agent id " + agentId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/merchant/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantCard(@PathVariable("merchantId") Long merchantId) {
		logger.debug("ReportController.getMerchantCard() start to get merchant card Id " + merchantId );
		try {
		    List<UserCardDto> dtos = reportService.getMerchantCard(merchantId);
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("ReportController.getMerchantCard() [FAILURE] faild to get merchant card id " + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/admin/{adminId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getAdminMerchantReport(@PathVariable("adminId") Long adminId) {
		logger.debug("ReportController.getMerchantCard() start to get merchant card for admin report " + adminId );
		try {
		    List<MerchantCardReportData> dtos = reportService.getAdminMerchantReport(adminId);
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("ReportController.getMerchantCard() [FAILURE] faild to get merchant card for admin report " + adminId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	@RequestMapping(value = "/summary/merchant/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getAdminMerchantSummary(@PathVariable("merchantId") Long merchantId) {
		logger.debug("ReportController.MerchantSummary() start to get merchant summary for admin report " + merchantId );
		try {
			MerchantSummary dtos = reportService.getAdminMerchantSummary(merchantId);
			return ResponseEntity.ok(new Response(dtos));
		}catch (Exception e) {
			logger.debug("ReportController.MerchantSummary() [FAILURE] faild to get merchant summary for admin report " + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}	
    }
	
	

}
