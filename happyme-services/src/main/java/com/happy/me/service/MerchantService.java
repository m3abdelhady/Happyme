package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.rest.MerchantPaymentInfoData;
import com.happy.me.service.exception.ServiceException;

public interface MerchantService {

	public MerchantDto addMerchant(MerchantDto merchantDto) throws ServiceException;
	
	public List<MerchantDto> getActiveMerchant() throws ServiceException;

	public AddressDto addAddress(AddressDto dto) throws ServiceException;

	public void deleteAddress(Long id) throws ServiceException;

	public MerchantRuleDto updateMerchantRule(MerchantRuleDto dto) throws ServiceException;

	public MerchantRuleDto addMerchantRule(MerchantRuleDto dto, Long id) throws ServiceException;

	public MerchantDto updateMerchant(MerchantDto dto) throws ServiceException;

	public List<MerchantDto> getAllMerchant() throws ServiceException;

	public MerchantDto getMerchant(Long id) throws ServiceException;

	public byte[] updateMerchantLogo(byte[] bytes, Long id) throws ServiceException;

	public byte[] updateMerchantBackground(byte[] bytes, Long id) throws ServiceException;

	public void addFeedback(FeedbackDto feedbackDto, Long merchantId, Long userId) throws ServiceException;

	public List<FeedbackDto> getFeedbacks(Long merchantId, Long userId) throws ServiceException;

	public void changeMerchantFlag(Long userId, Long merchantId) throws ServiceException;

	public List<UserDto> getPendingMerchant() throws ServiceException;

	public void deleteAgent(Long userId, Long agentId) throws ServiceException;

	public List<MerchantDto> getMerchantCreatedByUser(Long userId) throws ServiceException;

	public void addMerchantPaymetInfo(Long userId, Long merchantId, MerchantPaymentInfoData paymentInfoData) throws ServiceException;



}
