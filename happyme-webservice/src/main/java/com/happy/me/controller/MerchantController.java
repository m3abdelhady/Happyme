package com.happy.me.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
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

import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.dto.UserDto;
import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.rest.AddressData;
import com.happy.me.common.rest.FeedbackRest;
import com.happy.me.common.rest.MerchantData;
import com.happy.me.common.rest.MerchantRuleData;
import com.happy.me.common.rest.MerchantsData;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.service.MerchantService;
import com.happy.me.utils.WebUtils;

@RestController
@RequestMapping("/merchant")
@EnableAutoConfiguration
public class MerchantController {
	
	@Autowired
	MerchantService merchantService;
	
	@Autowired
	Mapper mapper;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addMerchant(@RequestBody MerchantData data, @PathVariable("id") Long id) {
		try {
			MerchantDto dto = mapper.map(data, MerchantDto.class, DozerMapping.MERCHANTDATA_VS_MERCHANTDTO.getKey());
			dto.setUserDto(new UserDto(id));
			if (data.getAddressDatas() != null) {
				List<AddressDto> addressDtos = DozerHelper.map(mapper, data.getAddressDatas(), AddressDto.class);
				dto.setAddressDtos(addressDtos);
			}
			dto = merchantService.addMerchant(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateMerchant(@RequestBody MerchantData data, @PathVariable("id") Long merchantId) {
		try {
			MerchantDto dto = mapper.map(data, MerchantDto.class, DozerMapping.MERCHANTDATA_VS_MERCHANTDTO.getKey());
			dto.setId(merchantId);
			dto = merchantService.updateMerchant(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addAddress(@RequestBody AddressData data, @PathVariable("id") Long id) {
		try {
			AddressDto dto = mapper.map(data, AddressDto.class);
			dto.setMerchantDto(new MerchantDto(id));
			
			dto = merchantService.addAddress(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/address/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id) {
		try {
			merchantService.deleteAddress(id);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	@RequestMapping(value = "/rule/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addMerchantRule(@RequestBody MerchantRuleData data, @PathVariable("id") Long id) {
		try {
			MerchantRuleDto dto = mapper.map(data, MerchantRuleDto.class);	
			dto = merchantService.addMerchantRule(dto, id);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/rule/{id}", method = RequestMethod.PUT, produces = { "application/json" })
    public ResponseEntity<?> updateMerchantRule(@RequestBody MerchantRuleData data, @PathVariable("id") Long id) {
		try {
			MerchantRuleDto dto = mapper.map(data, MerchantRuleDto.class);	
			dto.setId(id);
			dto = merchantService.updateMerchantRule(dto);
			return ResponseEntity.ok(dto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchant() {
		try {
//			InputStream inputStream = this.getClass().getClassLoader()  
//	                .getResourceAsStream("image/pepsi.png"); 
//			byte[] bytes = IOUtils.toByteArray(inputStream);
			List<MerchantDto> merchantDtos = merchantService.getActiveMerchant();
//			merchantDtos.forEach(x ->{
//				x.setLogo(bytes);
//				x.setBackgroundColor("#4295f4");
//			});
			return ResponseEntity.ok(new MerchantsData(merchantDtos));
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchant(@PathVariable("id") Long id) {
		try {
//			InputStream inputStream = this.getClass().getClassLoader()  
//	                .getResourceAsStream("image/pepsi.png"); 
//			byte[] bytes = IOUtils.toByteArray(inputStream);
			MerchantDto merchantDto = merchantService.getMerchant(id);
			
			return ResponseEntity.ok(merchantDto);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMerchant() {
		try {
			InputStream inputStream = this.getClass().getClassLoader()  
	                .getResourceAsStream("image/pepsi.png"); 
			byte[] bytes = IOUtils.toByteArray(inputStream);
			List<MerchantDto> merchantDtos = merchantService.getAllMerchant();
			merchantDtos.forEach(x ->{
				x.setLogo(bytes);
				x.setBackgroundColor("#4295f4");
			});
			return ResponseEntity.ok(new MerchantsData(merchantDtos));
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	
	
	
	@RequestMapping(value = "/logo/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadLogo(@RequestParam("logo") MultipartFile logo, @PathVariable("id") Long id) {
		try {
			byte[] logos = merchantService.updateMerchantLogo(logo.getBytes(), id); 
			return ResponseEntity.ok(logos);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/background/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadBackground(@RequestParam("background") MultipartFile background, @PathVariable("id") Long id) {
		try {
			byte[] logos = merchantService.updateMerchantBackground(background.getBytes(), id); 
			return ResponseEntity.ok(logos);
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}
    }
	
	@RequestMapping(value = "/{merchant}/feedback/{user}", method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<?> addFeedback(@RequestBody  FeedbackDto feedbackDto, @PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		try {
			merchantService.addFeedback(feedbackDto, merchantId, userId);
			return ResponseEntity.ok().build();
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
	@RequestMapping(value = "/{merchant}/feedback/{user}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getFeedback(@PathVariable("user") Long userId , @PathVariable("merchant") Long merchantId ) {
		try {
			List<FeedbackDto> feedbackDtos = merchantService.getFeedbacks(merchantId, userId);
			
			return ResponseEntity.ok(new FeedbackRest(feedbackDtos));
		}catch (Exception e) {
			return WebUtils.prepareErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppErrorCode.FAILURE);
		}		
    }
	
}