package com.happy.me.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.OfferDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.OfferData;
import com.happy.me.common.rest.OfferResponse;
import com.happy.me.common.rest.OffersResponse;
import com.happy.me.service.OfferService;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/offer")
@EnableAutoConfiguration
public class OfferController {
	
	@Autowired
	OfferService offerService ;
	
	@Autowired
	Mapper mapper;
	
	Logger logger = Logger.getLogger(OfferController.class);

	@RequestMapping(value = "/active", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getActiveOffer() {
		logger.debug("OfferController.getActiveOffer() start to get active affers");
		try {
			List<OfferDto> list = offerService.getActiveOffer();
			OffersResponse offerResponse = new OffersResponse(list);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			logger.debug("OfferController.getActiveOffer() [FAILURE] failed to get active affers", e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantOffer( @PathVariable("merchantId") Long merchantId) {
		logger.debug("OfferController.getMerchantOffer() start to get offer for merchant " + merchantId);
		try {
			List<OfferDto> list = offerService.getOffers(merchantId);
			OffersResponse offerResponse = new OffersResponse(list);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			logger.debug("OfferController.getMerchantOffer() [FAILURE] failed to get offer for merchant " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addOffer(@RequestBody OfferData data, @PathVariable("id") Long merchantId) {
		logger.debug("OfferController.addOffer() start to add offer for merchant " + merchantId);
		try {
			OfferDto dto = mapper.map(data, OfferDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = offerService.addOffer(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("OfferController.addOffer() [FAILURE] failed to add offer for merchant " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateOffer(@RequestBody OfferData data, @PathVariable("merchantId") Long merchantId) {
		logger.debug("OfferController.updateOffer() start to update offer for merchant " + merchantId);
		try {
			OfferDto dto = mapper.map(data, OfferDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = offerService.updateOffer(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			logger.debug("OfferController.updateOffer() [FAILURE] failed to update offer for merchant " + merchantId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{offerid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOffer( @PathVariable("offerid") Long offerid) {
		logger.debug("OfferController.deleteOffer() start to delete offer " + offerid);
		try {
			offerService.deleteOffer(offerid); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("OfferController.deleteOffer() [FAILURE] failed to delete offer " + offerid, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable("id") Long offerid) {
		logger.debug("OfferController.uploadImage() start to upload image for offer " + offerid);
		try {
			offerService.updateOfferImage(image.getBytes(), offerid); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("OfferController.uploadImage() [FAILURE] failed to upload image for offer " + offerid, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	
	@RequestMapping(value = "/images/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadOfferImages(@RequestParam("file") List<MultipartFile> files , @PathVariable("id") Long offerid) {
		logger.debug("OfferController.uploadOfferImages() start to add images for offer id " + offerid + " files size " + files.size());
		try {
			List<byte[]> list = new ArrayList<>();
			for (MultipartFile f : files) {
				list.add(f.getBytes());
			}
			offerService.updateOfferImage(list, offerid); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("OfferController.uploadOfferImages() [FAILURE] failed to add images for offer id " + offerid + " files size " + files.size(), e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/images/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOfferImage(@PathVariable("imageId") Long imageId) {
		logger.debug("OfferController.deleteOfferImage() start to delete offer image " + imageId);
		try {
			offerService.deleteOfferImage(imageId); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			logger.debug("OfferController.deleteOfferImage() [FAILURE] failed to delete offer image " + imageId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{offerId}/data", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getOffer(@PathVariable("offerId") Long offerId) {
		logger.debug("OfferController.getOffer() start to get offer by id " + offerId);
		try {
			OfferDto dto = offerService.getOffer(offerId);
			OfferResponse offerResponse = new OfferResponse(dto);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			logger.debug("OfferController.getOffer() [FAILURE] failed to get offer by id " + offerId, e);
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}/active", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantActiveOffer( @PathVariable("merchantId") Long merchantId) {
		logger.debug("OfferController.getMerchantActiveOffer() start to get active offer for merchant" + merchantId );
		try {
			List<OfferDto> list = offerService.getMerchantActiveOffer(merchantId);
			OffersResponse offerResponse = new OffersResponse(list);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			logger.debug("OfferController.getMerchantActiveOffer() [FAILURE] failed to get active offer for merchant" + merchantId, e );
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	
}
