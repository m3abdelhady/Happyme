package com.happy.me.business;

import java.util.List;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;

public interface MerchantBusiness {
	
	public MerchantDto addMerchant(MerchantDto merchantDto) throws BusinessException;
	
	public List<MerchantDto> getActiveMerchant() throws BusinessException;

	public AddressDto addAddress(AddressDto dto) throws BusinessException;

	public void deleteAddress(Long id) throws BusinessException;

	public MerchantRuleDto updateMerchantRule(MerchantRuleDto dto) throws BusinessException;

	public MerchantRuleDto addMerchantRule(MerchantRuleDto dto, Long id) throws BusinessException;

	public MerchantDto updateMerchant(MerchantDto dto) throws BusinessException;

	public List<MerchantDto> getAllMerchant() throws BusinessException;

	public MerchantDto getMerchant(Long id) throws BusinessException;

	public byte[] updateMerchantLogo(byte[] bytes, Long id) throws BusinessException;

	public byte[] updateMerchantBackground(byte[] bytes, Long id) throws BusinessException;

	public void addFeedback(FeedbackDto feedbackDto, Long merchantId, Long userId) throws BusinessException;

	public List<FeedbackDto> getFeedbacks(Long merchantId) throws BusinessException;
	
}
