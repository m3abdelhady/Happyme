package com.happy.me.service;

import java.util.List;

import com.happy.me.common.dto.OfferDto;
import com.happy.me.service.exception.ServiceException;

public interface OfferService {

	public OfferDto addOffer(OfferDto offerDto) throws ServiceException;

	public byte[] updateOfferImage(byte[] bytes, Long offerid) throws ServiceException;

	public void updateOfferImage(List<byte[]> list, Long offerid) throws ServiceException;
	
	public List<OfferDto> getActiveOffer() throws ServiceException;

	public List<OfferDto> getOffers(Long merchantId) throws ServiceException;

	public OfferDto updateOffer(OfferDto dto) throws ServiceException;

	public void deleteOffer(Long offerid) throws ServiceException;

	public void deleteOfferImage(Long imageId) throws ServiceException;

	public OfferDto getOffer(Long offerId) throws ServiceException;

	public List<OfferDto> getMerchantActiveOffer(Long merchantId) throws ServiceException;
}
