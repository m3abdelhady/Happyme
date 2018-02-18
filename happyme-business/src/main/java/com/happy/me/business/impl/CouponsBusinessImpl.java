package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.CouponsBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CouponsDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.dataaccess.model.Coupons;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.model.UserCoupons;
import com.happy.me.dataaccess.repository.CouponsRepository;
import com.happy.me.dataaccess.repository.UserCouponsRepository;

@Service("couponsBusiness")
public class CouponsBusinessImpl implements CouponsBusiness {

	@Autowired
	CouponsRepository couponsRepository;

	@Autowired
	UserCouponsRepository userCouponsRepository;

	@Autowired
	private Mapper mapper;

	@Override
	public CouponsDto addCoupons(CouponsDto couponsDto) throws BusinessException {
		try {
			Coupons coupons = mapper.map(couponsDto, Coupons.class);
			Merchant merchant = new Merchant();
			merchant.setId(couponsDto.getMerchantDto().getId());
			coupons.setMerchant(merchant);
			coupons = couponsRepository.save(coupons);

			return mapper.map(coupons, CouponsDto.class);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public List<CouponsDto> getCoupons(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			List<Coupons> cList = couponsRepository.getMerchantCoupons(merchant);

			List<CouponsDto> couponsDtos = new ArrayList<>();
			for (Coupons coupons : cList) {
				CouponsDto dto = mapper.map(coupons, CouponsDto.class);
				couponsDtos.add(dto);
			}

			return couponsDtos;

		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public CouponsDto updateCoupons(CouponsDto dto) throws BusinessException {
		try {
			Coupons coupons = couponsRepository.findOne(dto.getId());
			coupons.setEnd(dto.getEnd());
			coupons.setStart(dto.getStart());
			coupons.setTitle(dto.getTitle());
			coupons.setDescription(dto.getDescription());
			coupons = couponsRepository.save(coupons);
			return mapper.map(coupons, CouponsDto.class);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void deleteCoupons(Long couponsid) throws BusinessException {
		try {
			couponsRepository.delete(couponsid);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}

	}

	@Override
	public List<CouponsDto> getUserCoupons(Long userId) throws BusinessException {
		try {
			User user = new User();
			user.setId(userId);
			Calendar calendar = Calendar.getInstance();
			List<UserCoupons> coupons = userCouponsRepository.getCoupons(user, calendar.getTime());
			List<CouponsDto> dtos = new ArrayList<CouponsDto>();
			for (UserCoupons userCoupons : coupons) {
				CouponsDto couponsDto = mapper.map(userCoupons.getCoupons(), CouponsDto.class,
						DozerMapping.COUPONS_VS_COUPONSDTO.getKey());
				dtos.add(couponsDto);
			}
			return dtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public List<CouponsDto> getActiveCoupons(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			Calendar calendar = Calendar.getInstance();
			List<Coupons> cList = couponsRepository.getMerchantActiveCoupons(merchant, calendar.getTime());

			List<CouponsDto> couponsDtos = new ArrayList<>();
			for (Coupons coupons : cList) {
				CouponsDto dto = mapper.map(coupons, CouponsDto.class);
				couponsDtos.add(dto);
			}

			return couponsDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public CouponsDto getActiveCoupon(Long couponId) throws BusinessException {
		try {
			Calendar calendar = Calendar.getInstance();
			Optional<Coupons> coupon = couponsRepository.getActiveCoupon(couponId, calendar.getTime());
			if (!coupon.isPresent()) {
				return null;
			}

			CouponsDto dto = mapper.map(coupon.get(), CouponsDto.class, DozerMapping.COUPONS_VS_COUPONSDTO.getKey());

			return dto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void addCoponeToUser(Long userId, Long couponeId) throws BusinessException {
		try {
			User user = new User();
			user.setId(userId);
			Coupons coupons = new Coupons();
			coupons.setId(couponeId);
			UserCoupons userCoupons = new UserCoupons();
			userCoupons.setCoupons(coupons);
			userCoupons.setUser(user);
			userCouponsRepository.save(userCoupons);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
		
	}

	@Override
	public List<CouponsDto> getCouponsForUser(Long userId, Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			
			User user = new User(userId);
			
			List<UserCoupons> cList = userCouponsRepository.getCouponsForUser(user, merchant);

			List<CouponsDto> couponsDtos = new ArrayList<>();
			for (UserCoupons coupons : cList) {
				CouponsDto dto = mapper.map(coupons.getCoupons(), CouponsDto.class);
				dto.setUserCouponId(coupons.getId());
				couponsDtos.add(dto);
			}

			return couponsDtos;

		} catch (Exception e) {
			throw new BusinessException("Exception while creating Coupons", e);
		}
	}

	@Override
	public void redeemCopone(Long coponeId) throws BusinessException {
		try {
		
			userCouponsRepository.delete(coponeId);
		} catch (Exception e) {
			throw new BusinessException("E xception while creating Coupons", e);
		}
		
	}

}
