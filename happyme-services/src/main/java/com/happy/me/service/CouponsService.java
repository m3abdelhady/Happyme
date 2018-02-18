package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.CouponsDto;
import com.happy.me.service.exception.ServiceException;

public interface CouponsService {

	public CouponsDto addCoupons(CouponsDto couponsDto) throws ServiceException;
	
	public List<CouponsDto> getCoupons(Long merchantId) throws ServiceException;

	public CouponsDto updateCoupons(CouponsDto dto) throws ServiceException;

	public void deleteCoupons(Long couponsid) throws ServiceException;
	
	public List<CouponsDto> getUserCoupons(Long userId) throws ServiceException;

	public List<CouponsDto> getActiveCoupons(Long merchantId) throws ServiceException;

	public void addCoponeToUser(Long userId, Long couponeId, Long merchantId) throws ServiceException;

	public void addCoponeToUserByAgent(Long userId, Long couponeId, Long agentId) throws ServiceException;

	public List<CouponsDto> getActiveCouponsByAgent(Long agentId) throws ServiceException;

	public void addCoponeToUserByCardNumber(String cardNumber, Long coponeId, Long agentId) throws ServiceException;

	public List<CouponsDto> getCouponsForUser(String card, Long merchantId) throws ServiceException;

	public void redeemCopone(Long coponeId, Long agentId) throws ServiceException;


}
