package com.happy.me.controller;

import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "/active", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getActiveOffer() {
		try {
			List<OfferDto> list = offerService.getActiveOffer();
			OfferResponse offerResponse = new OfferResponse(list);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getMerchantOffer( @PathVariable("merchantId") Long merchantId) {
		try {
			List<OfferDto> list = offerService.getOffers(merchantId);
			OfferResponse offerResponse = new OfferResponse(list);
			return ResponseEntity.ok(offerResponse);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addOffer(@RequestBody OfferData data, @PathVariable("id") Long merchantId) {
		try {
			OfferDto dto = mapper.map(data, OfferDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = offerService.addOffer(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchantId}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateOffer(@RequestBody OfferData data, @PathVariable("merchantId") Long merchantId) {
		try {
			OfferDto dto = mapper.map(data, OfferDto.class);
			MerchantDto merchantDto = new MerchantDto();
			merchantDto.setId(merchantId);
			dto.setMerchantDto(merchantDto);
			dto = offerService.updateOffer(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{offerid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOffer( @PathVariable("offerid") Long offerid) {
		try {
			offerService.deleteOffer(offerid); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/image/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable("id") Long offerid) {
		try {
			byte[] logos = offerService.updateOfferImage(image.getBytes(), offerid); 
			return ResponseEntity.ok(logos);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	
	@RequestMapping(value = "/images/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadOfferImages(@RequestParam("file") List<MultipartFile> files , @PathVariable("id") Long offerid) {
		try {
			List<byte[]> list = new ArrayList<>();
			for (MultipartFile f : files) {
				list.add(f.getBytes());
			}
			offerService.updateOfferImage(list, offerid); 
			return ResponseEntity.ok(list);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/images/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOfferImage(@PathVariable("imageId") Long imageId) {
		try {
			offerService.deleteOfferImage(imageId); 
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	
	
}
