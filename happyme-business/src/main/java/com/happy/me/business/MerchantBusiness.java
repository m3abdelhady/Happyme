package com.happy.me.business;

import java.util.List;
import java.util.Optional;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.rest.MerchantPaymentInfoData;

public interface MerchantBusiness {
	
	public MerchantDto addMerchant(MerchantDto merchantDto) throws BusinessException;
	
	public List<MerchantDto> getActiveMerchant() throws BusinessException;

	public AddressDto addAddress(AddressDto dto) throws BusinessException;

	public void deleteAddress(Long id) throws BusinessException;

	public MerchantRuleDto updateMerchantRule(MerchantRuleDto dto) throws BusinessException;

	public MerchantRuleDto addMerchantRule(MerchantRuleDto dto, Long id) throws BusinessException;

	public MerchantDto updateMerchant(MerchantDto dto) throws BusinessException;

	public List<MerchantDto> getAllMerchant() throws BusinessException;

	public MerchantDto getMerchantByAdminId(Long id) throws BusinessException;

	public byte[] updateMerchantLogo(byte[] bytes, Long id) throws BusinessException;

	public byte[] updateMerchantBackground(byte[] bytes, Long id) throws BusinessException;

	public void addFeedback(FeedbackDto feedbackDto, Long merchantId, Long userId) throws BusinessException;

	public List<FeedbackDto> getFeedbacks(Long merchantId) throws BusinessException;

	public boolean isPresent(Long id) throws BusinessException;
	
	public MerchantRuleDto getMerchantRule(Long merchantId) throws BusinessException;

	public void changeMerchantFlag(Long merchantId) throws BusinessException;

	public void deleteAgent(Long agentId) throws BusinessException;

	public List<MerchantDto> getMerchantCreatedByUser(Long userId) throws BusinessException;

	public Optional<MerchantDto> getMerchantById(Long merchantId) throws BusinessException;

	public List<MerchantDto> getAllMerchantWithRule() throws BusinessException;

	public void addMerchantPaymetInfo(Long merchantId, MerchantPaymentInfoData paymentInfoData)  throws BusinessException;

	
}
