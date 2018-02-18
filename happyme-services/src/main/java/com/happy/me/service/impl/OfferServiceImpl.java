package com.happy.me.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happy.me.business.OfferBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.OfferDto;
import com.happy.me.service.OfferService;
import com.happy.me.service.exception.ServiceException;

@Service("offerService")
public class OfferServiceImpl implements OfferService {

	@Autowired
	OfferBusiness offerBusiness;

	@Override
	public OfferDto addOffer(OfferDto offerDto) throws ServiceException {
		try {
			return offerBusiness.addOffer(offerDto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Override
	public byte[] updateOfferImage(byte[] bytes, Long offerid) throws ServiceException {
		try {
			return offerBusiness.updateOfferImage(bytes, offerid);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Override
	public void updateOfferImage(List<byte[]> list, Long offerid) throws ServiceException {
		try {
			offerBusiness.updateOfferImages(list, offerid);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<OfferDto> getActiveOffer() throws ServiceException {
		try {
			return offerBusiness.getActiveOffer();
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<OfferDto> getOffers(Long merchantId) throws ServiceException {
		try {
			return offerBusiness.getOffers(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Override
	public OfferDto updateOffer(OfferDto dto) throws ServiceException {
		try {
			return offerBusiness.updateOffer(dto);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteOffer(Long offerid) throws ServiceException {
		try {
			offerBusiness.deleteOffer(offerid);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}

	}

	@Transactional(readOnly = false)
	@Override
	public void deleteOfferImage(Long imageId) throws ServiceException {
		try {
			offerBusiness.deleteOfferImage(imageId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}

	}

	@Override
	public OfferDto getOffer(Long offerId) throws ServiceException {
		try {
			return offerBusiness.getOffer(offerId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public List<OfferDto> getMerchantActiveOffer(Long merchantId) throws ServiceException {
		try {
			return offerBusiness.getMerchantActiveOffer(merchantId);
		} catch (BusinessException e) {
			throw new ServiceException("Exception while creating offer", e);
		}
	}

}
