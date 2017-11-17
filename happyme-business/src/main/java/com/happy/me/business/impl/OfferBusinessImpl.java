package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.OfferBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.OfferDto;
import com.happy.me.common.dto.OfferImagesDto;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.Offer;
import com.happy.me.dataaccess.model.OfferImages;
import com.happy.me.dataaccess.repository.OfferImagesRepository;
import com.happy.me.dataaccess.repository.OfferRepository;

@Service("offerBusiness")
public class OfferBusinessImpl implements OfferBusiness {

	@Autowired
	OfferRepository offerRepository;

	@Autowired
	OfferImagesRepository offerImagesRepository;
	
	@Autowired
	private Mapper mapper;

	@Override
	public OfferDto addOffer(OfferDto offerDto) throws BusinessException {
		try {
			Offer offer = mapper.map(offerDto, Offer.class);
			Merchant merchant = new Merchant();
			merchant.setId(offerDto.getMerchantDto().getId());
			offer.setMerchant(merchant);
			offer = offerRepository.save(offer);

			return  mapper.map(offer, OfferDto.class);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public byte[] updateOfferImage(byte[] bytes, Long offerid) throws BusinessException {
		try {
			Offer offer = offerRepository.findOne(offerid);
			offer.setImage(bytes);
			offer = offerRepository.save(offer);
			return offer.getImage();
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public void updateOfferImages(List<byte[]> list, Long offerid) throws BusinessException {
		try {
			Offer offer = new Offer();
			offer.setId(offerid);
			for (byte[] bs : list) {
				OfferImages offerImages = new OfferImages();
				offerImages.setImage(bs);
				offerImages.setOffer(offer);
				offerImagesRepository.save(offerImages);
			}
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public List<OfferDto> getActiveOffer() throws BusinessException {
		try {
			Calendar calendar = Calendar.getInstance();
			List<Offer> offers = offerRepository.getActiveOffer(calendar.getTime());
			
			List<OfferDto> offerDtos = new ArrayList<>();
			for (Offer offer : offers) {
				OfferDto dto = mapper.map(offer, OfferDto.class);
				List<OfferImagesDto> dtos = DozerHelper.map(mapper, offer.getImages(), OfferImagesDto.class);
				dto.setImages(dtos);
				MerchantDto merchantDto = mapper.map(offer.getMerchant(), MerchantDto.class);
				if (offer.getMerchant().getAddresses() != null) {
					List<AddressDto> addressDtos = DozerHelper.map(mapper, offer.getMerchant().getAddresses(), AddressDto.class);
					merchantDto.setAddressDtos(addressDtos);		
				}
				dto.setMerchantDto(merchantDto);
				offerDtos.add(dto);
			}
			
			return offerDtos;
			
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public List<OfferDto> getOffers(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			List<Offer> offers = offerRepository.getMerchantOffer(merchant);
			
			List<OfferDto> offerDtos = new ArrayList<>();
			for (Offer offer : offers) {
				OfferDto dto = mapper.map(offer, OfferDto.class);
				List<OfferImagesDto> dtos = DozerHelper.map(mapper, offer.getImages(), OfferImagesDto.class);
				dto.setImages(dtos);
				offerDtos.add(dto);
			}
			
			return offerDtos;
			
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public OfferDto updateOffer(OfferDto dto) throws BusinessException {
		try {
			Offer offer = offerRepository.findOne(dto.getId());
			offer.setEnd(dto.getEnd());
			offer.setStart(dto.getStart());
			offer.setTitle(dto.getTitle());
			offer.setDescription(dto.getDescription());
			offer = offerRepository.save(offer);
			return mapper.map(offer, OfferDto.class);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
	}

	@Override
	public void deleteOffer(Long offerid) throws BusinessException {
		try {
			offerRepository.delete(offerid);
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
		
	}

	@Override
	public void deleteOfferImage(Long imageId) throws BusinessException {
		try {
			offerImagesRepository.deleteOfferImages(imageId); 
		} catch (Exception e) {
			throw new BusinessException("Exception while creating offer", e);
		}
		
		
	}

}
