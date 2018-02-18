package com.happy.me.business;

import java.util.List;

import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.OfferDto;

public interface OfferBusiness {
	
	public OfferDto addOffer(OfferDto offerDto) throws BusinessException;

	public byte[] updateOfferImage(byte[] bytes, Long offerid) throws BusinessException;

	public void updateOfferImages(List<byte[]> list, Long offerid) throws BusinessException;
	
	public List<OfferDto> getActiveOffer() throws BusinessException;

	public List<OfferDto> getOffers(Long merchantId) throws BusinessException;

	public OfferDto updateOffer(OfferDto dto) throws BusinessException;

	public void deleteOffer(Long offerid) throws BusinessException;

	public void deleteOfferImage(Long imageId) throws BusinessException;

	public OfferDto getOffer(Long offerId) throws BusinessException;

	public List<OfferDto> getMerchantActiveOffer(Long merchantId) throws BusinessException;


	
}
