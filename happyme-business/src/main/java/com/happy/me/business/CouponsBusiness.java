package com.happy.me.business;

import java.util.List;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CouponsDto;

public interface CouponsBusiness {
	
	public CouponsDto addCoupons(CouponsDto couponsDto) throws BusinessException;
	
	public List<CouponsDto> getCoupons(Long merchantId) throws BusinessException;

	public CouponsDto updateCoupons(CouponsDto dto) throws BusinessException;

	public void deleteCoupons(Long couponsid) throws BusinessException;
	
	public List<CouponsDto> getUserCoupons(Long userId) throws BusinessException;

	public List<CouponsDto> getActiveCoupons(Long merchantId) throws BusinessException;

	public CouponsDto getActiveCoupon(Long coponeId) throws BusinessException;

	public void addCoponeToUser(Long userId, Long couponeId) throws BusinessException;

	public List<CouponsDto> getCouponsForUser(Long  userId, Long merchantId) throws BusinessException;

	public void redeemCopone(Long coponeId) throws BusinessException;



	
}
