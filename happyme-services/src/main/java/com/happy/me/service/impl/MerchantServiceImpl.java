package com.happy.me.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.common.rest.MerchantPaymentInfoData;
import com.happy.me.service.MerchantService;
import com.happy.me.service.exception.NotAuthorizedException;
import com.happy.me.service.exception.ServiceException;
import com.happy.me.service.exception.UserNotFoundException;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantBusiness merchantBusiness;

	@Autowired
	private UserBusiness userBusiness;

	@Override
	@Transactional
	public MerchantDto addMerchant(MerchantDto merchantDto) throws ServiceException {
		try {
			return merchantBusiness.addMerchant(merchantDto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating merchant", e);
		}
	}

	@Override
	public List<MerchantDto> getActiveMerchant() throws ServiceException {
		try {
			return merchantBusiness.getActiveMerchant();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting active merchant", e);
		}
	}

	@Override
	public AddressDto addAddress(AddressDto dto) throws ServiceException {
		try {
			return merchantBusiness.addAddress(dto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating address", e);
		}
	}

	@Override
	public void deleteAddress(Long id) throws ServiceException {
		try {
			merchantBusiness.deleteAddress(id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while delete address", e);
		}

	}

	@Override
	public MerchantRuleDto updateMerchantRule(MerchantRuleDto dto) throws ServiceException {
		try {
			return merchantBusiness.updateMerchantRule(dto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating address", e);
		}
	}

	@Override
	public MerchantRuleDto addMerchantRule(MerchantRuleDto dto, Long id) throws ServiceException {
		try {
			return merchantBusiness.addMerchantRule(dto, id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating address", e);
		}
	}

	@Override
	public MerchantDto updateMerchant(MerchantDto dto) throws ServiceException {
		try {
			return merchantBusiness.updateMerchant(dto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while update Merchant", e);
		}
	}

	@Override
	public List<MerchantDto> getAllMerchant() throws ServiceException {
		try {
			return merchantBusiness.getAllMerchant();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting all merchant", e);
		}
	}

	@Override
	public MerchantDto getMerchant(Long id) throws ServiceException {
		try {
			return merchantBusiness.getMerchantByAdminId(id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting merchant", e);
		}
	}

	@Override
	public byte[] updateMerchantLogo(byte[] bytes, Long id) throws ServiceException {
		try {
			return merchantBusiness.updateMerchantLogo(bytes, id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating merchant logo", e);
		}
	}

	@Override
	public byte[] updateMerchantBackground(byte[] bytes, Long id) throws ServiceException {
		try {
			return merchantBusiness.updateMerchantBackground(bytes, id);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating merchant logo", e);
		}
	}

	@Override
	public void addFeedback(FeedbackDto feedbackDto, Long merchantId, Long userId) throws ServiceException {
		try {
			merchantBusiness.addFeedback(feedbackDto, merchantId, userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating feedback", e);
		}

	}

	@Override
	public List<FeedbackDto> getFeedbacks(Long merchantId, Long userId) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(userId);
			if (dto.isPresent()) {
				if (!dto.get().getRoleKey().getValue().equals(RoleKey.MERCHANT.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			} else {
				throw new NotAuthorizedException("Exception while get feedback");
			}

			MerchantDto merchantDto = merchantBusiness.getMerchantByAdminId(userId);
			if (merchantDto == null || merchantDto.getUserDto().getId() != userId) {
				throw new NotAuthorizedException("Exception while get feedback");
			}

			List<FeedbackDto> feedbackDtos = merchantBusiness.getFeedbacks(merchantId);

			return feedbackDtos;
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}
	}

	@Override
	public void changeMerchantFlag(Long userId, Long merchantId) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(userId);
			if (dto.isPresent()) {
				if (!dto.get().getRoleKey().getValue().equals(RoleKey.ADMIN.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			} else {
				throw new UserNotFoundException("Exception while get feedback");
			}

			merchantBusiness.changeMerchantFlag(merchantId);

		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}

	}

	@Override
	public List<UserDto> getPendingMerchant() throws ServiceException {
		try {
			return userBusiness.getPendingMerchant();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}

	}

	@Override
	public void deleteAgent(Long userId, Long agentId) throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(userId);
			if (dto.isPresent()) {
				if (!dto.get().getRoleKey().getValue().equals(RoleKey.MERCHANT.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			} else {
				throw new UserNotFoundException("Exception while get feedback");
			}

			Optional<UserDto> agentDto = userBusiness.getUserById(agentId);
			if (agentDto.isPresent()) {
				if (!agentDto.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			} else {
				throw new UserNotFoundException("Exception while get feedback");
			}

			if (dto.get().getMerchantDto().getId() != agentDto.get().getMerchantDto().getId()) {
				throw new NotAuthorizedException("Exception while get feedback");
			}

			merchantBusiness.deleteAgent(agentId);

		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}

	}

	@Override
	public List<MerchantDto> getMerchantCreatedByUser(Long userId) throws ServiceException {
		try {
			return merchantBusiness.getMerchantCreatedByUser(userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}
	}

	@Override
	public void addMerchantPaymetInfo(Long userId, Long merchantId, MerchantPaymentInfoData paymentInfoData)
			throws ServiceException {
		try {
			Optional<UserDto> dto = userBusiness.getUserById(userId);
			if (dto.isPresent()) {
				if (!dto.get().getRoleKey().getValue().equals(RoleKey.ADMIN.getValue()) || ! !dto.get().getRoleKey().getValue().equals(RoleKey.RESELLER.getValue())) {
					throw new NotAuthorizedException("Exception while get feedback");
				}
			} else {
				throw new NotAuthorizedException("Exception while get feedback");
			}
			merchantBusiness.addMerchantPaymetInfo(merchantId, paymentInfoData);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while getting feedback", e);
		}

	}

}
