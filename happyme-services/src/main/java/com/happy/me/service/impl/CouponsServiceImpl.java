package com.happy.me.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.me.business.CardBusiness;
import com.happy.me.business.CouponsBusiness;
import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CouponsDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.UserCardDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.RoleKey;
import com.happy.me.service.CouponsService;
import com.happy.me.service.exception.CardNotBelongToMerchantException;
import com.happy.me.service.exception.CardNotFoundException;
import com.happy.me.service.exception.InvalidAgentException;
import com.happy.me.service.exception.InvalidCouponeException;
import com.happy.me.service.exception.MerchantCouponeInvalidException;
import com.happy.me.service.exception.ServiceException;
import com.happy.me.service.exception.UserNotFoundException;

@Service("couponsService")
public class CouponsServiceImpl implements CouponsService {

	@Autowired
	CouponsBusiness couponsBusiness;
	
	@Autowired
	UserBusiness userBusiness;
	
	@Autowired
	MerchantBusiness merchantBusiness; 
	
	@Autowired
	CardBusiness cardBusiness;

	@Override
	public CouponsDto addCoupons(CouponsDto couponsDto) throws ServiceException {
		try {
			return couponsBusiness.addCoupons(couponsDto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	
	@Override
	public List<CouponsDto> getCoupons(Long merchantId) throws ServiceException {
		try {
			return couponsBusiness.getCoupons(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Override
	public CouponsDto updateCoupons(CouponsDto dto) throws ServiceException {
		try {
			return couponsBusiness.updateCoupons(dto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteCoupons(Long couponsid) throws ServiceException {
		try {
			couponsBusiness.deleteCoupons(couponsid);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}

	}

	@Transactional(readOnly = false)
	@Override
	public List<CouponsDto> getUserCoupons(Long userId) throws ServiceException {
		try {
			return couponsBusiness.getUserCoupons(userId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}


	@Override
	public List<CouponsDto> getActiveCoupons(Long merchantId) throws ServiceException {
		try {
			return couponsBusiness.getActiveCoupons(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void addCoponeToUser(Long userId, Long couponeId, Long merchantId) throws ServiceException {
		try {
			CouponsDto couponsDto = couponsBusiness.getActiveCoupon(couponeId);
			if (couponsDto == null) {
				throw new InvalidCouponeException("Coupons Not valid");
			}
			if (couponsDto.getMerchantDto().getId() != merchantId) {
				throw new MerchantCouponeInvalidException("Coupons Not for merchant");
			}
			couponsBusiness.addCoponeToUser(userId,  couponeId);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
		
	}

	@Transactional(readOnly = false)
	@Override
	public void addCoponeToUserByAgent(Long userId, Long couponeId, Long agentId) throws ServiceException {
		try {
			CouponsDto couponsDto = couponsBusiness.getActiveCoupon(couponeId);
			if (couponsDto == null) {
				throw new InvalidCouponeException("Coupons Not valid");
			}
			Optional<UserDto> userDto = userBusiness.getUserById(agentId);
			if (! userDto.isPresent()) {
				throw new UserNotFoundException("Coupons Not for merchant");
			}
			if (!userDto.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue())) {
				throw new InvalidAgentException("InvalidAgentException");
			}
			MerchantDto merchantDto = userBusiness.getUserMerchantById(userDto.get().getId()); 
			if (couponsDto.getMerchantDto().getId() != merchantDto.getId() ) {
				throw new MerchantCouponeInvalidException("Coupons Not for merchant");
			}
			couponsBusiness.addCoponeToUser(userId,  couponeId);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
		
	}


	@Override
	public List<CouponsDto> getActiveCouponsByAgent(Long agentId) throws ServiceException {
		try {
			Optional<UserDto> userDto = userBusiness.getUserById(agentId);
			if (! userDto.isPresent()) {
				throw new UserNotFoundException("Coupons Not for merchant");
			}
			return couponsBusiness.getActiveCoupons(userDto.get().getMerchantDto().getId());
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void addCoponeToUserByCardNumber(String cardNumber, Long coponeId, Long agentId) throws ServiceException {
		try {
			CouponsDto couponsDto = couponsBusiness.getActiveCoupon(coponeId);
			if (couponsDto == null) {
				throw new InvalidCouponeException("Coupons Not valid");
			}
			Optional<UserDto> userDto = userBusiness.getUserById(agentId);
			if (! userDto.isPresent()) {
				throw new UserNotFoundException("Coupons Not for merchant");
			}
			if (!userDto.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue())) {
				throw new InvalidAgentException("InvalidAgentException");
			}
			MerchantDto merchantDto = userBusiness.getUserMerchantById(userDto.get().getId()); 
			if (couponsDto.getMerchantDto().getId() != merchantDto.getId() ) {
				throw new MerchantCouponeInvalidException("Coupons Not for merchant");
			}
			
			Optional<UserCardDto> cardDto = cardBusiness.getCardByNumber(cardNumber);
			if (!cardDto.isPresent()) {
				throw new CardNotFoundException("Card Not Found Exception");
			}
			if (cardDto.get().getMerchantDto().getId() != merchantDto.getId()) {
				throw new CardNotBelongToMerchantException("Card Not Belong To Merchant Exception");
			}
			couponsBusiness.addCoponeToUser(cardDto.get().getUserDto().getId(),  coponeId);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
		
	}


	@Override
	public List<CouponsDto> getCouponsForUser(String card, Long agentId) throws ServiceException {
		try {
			Optional<UserDto> userDto = userBusiness.getUserById(agentId);
			if (! userDto.isPresent()) {
				throw new UserNotFoundException("Coupons Not for merchant");
			}
			if (!userDto.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue())) {
				throw new InvalidAgentException("InvalidAgentException");
			}
			MerchantDto merchantDto = userBusiness.getUserMerchantById(userDto.get().getId()); 
			
			Optional<UserCardDto> cardDto = cardBusiness.getCardByNumber(card);
			if (!cardDto.isPresent()) {
				throw new CardNotFoundException("Card Not Found Exception");
			}
			if (cardDto.get().getMerchantDto().getId() != merchantDto.getId()) {
				throw new CardNotBelongToMerchantException("Card Not Belong To Merchant Exception");
			}
			return couponsBusiness.getCouponsForUser(cardDto.get().getUserDto().getId(),  merchantDto.getId());
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
	}


	@Override
	public void redeemCopone(Long coponeId, Long agentId) throws ServiceException {
		try {
			Optional<UserDto> userDto = userBusiness.getUserById(agentId);
			if (! userDto.isPresent()) {
				throw new UserNotFoundException("Coupons Not for merchant");
			}
			if (!userDto.get().getRoleKey().getValue().equals(RoleKey.AGENT.getValue())) {
				throw new InvalidAgentException("InvalidAgentException");
			}
			
			couponsBusiness.redeemCopone(coponeId);
			
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating Coupons", e);
		}
		
	}


}
