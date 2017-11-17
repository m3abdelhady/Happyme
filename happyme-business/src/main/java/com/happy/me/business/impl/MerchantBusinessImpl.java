package com.happy.me.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.AddressDto;
import com.happy.me.common.dto.FeedbackDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.common.dto.MerchantRuleDto;
import com.happy.me.common.enums.DozerMapping;
import com.happy.me.common.util.DozerHelper;
import com.happy.me.dataaccess.model.Address;
import com.happy.me.dataaccess.model.Feedback;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.MerchantRule;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.repository.AddressRepository;
import com.happy.me.dataaccess.repository.FeedbackRepository;
import com.happy.me.dataaccess.repository.MerchantRepository;
import com.happy.me.dataaccess.repository.MerchantRuleRepository;

@Service("merchantBusiness")
public class MerchantBusinessImpl implements MerchantBusiness {

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private MerchantRuleRepository merchantRuleRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private Mapper mapper;
	
	@Override
	public MerchantDto addMerchant(MerchantDto merchantDto) throws BusinessException {
		try {
			Merchant merchant = mapper.map(merchantDto, Merchant.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
			merchant.setActive(true);
			if (merchantDto.getAddressDtos() != null) {
				List<Address> addresses = DozerHelper.map(mapper, merchantDto.getAddressDtos(), Address.class);
				for (Address address : addresses) {
					address.setMerchant(merchant);
				}
				merchant.setAddresses(addresses);				
			}
			merchant = merchantRepository.save(merchant);
			MerchantDto dto = null;
			dto = mapper.map(merchant, MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
			if (merchant.getAddresses() != null) {
				List<AddressDto> addressDtos = DozerHelper.map(mapper, merchant.getAddresses(), AddressDto.class);
				dto.setAddressDtos(addressDtos);		
			}
			return dto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating merchant", e);
		}
	}

	@Override
	public List<MerchantDto> getActiveMerchant() throws BusinessException {
		try {
			List <Merchant> merchants = merchantRepository.getActiveMerchant();
			List <MerchantDto> merchantDtos = new ArrayList<>();
			for (Merchant merchant : merchants) {
				MerchantDto dto = mapper.map(merchant ,MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
				List<AddressDto> addressDtos = null;
				if (merchant.getAddresses() != null) {
					addressDtos = new ArrayList<>();
					for (Address address : merchant.getAddresses()) {
						AddressDto addressDto =  mapper.map(address ,AddressDto.class);
						addressDtos.add(addressDto);
					}
					dto.setAddressDtos(addressDtos);
				}
				merchantDtos.add(dto);
			}
//			List <MerchantDto> merchantDtos = DozerHelper.map(mapper, merchants, MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
			
			return merchantDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while get active merchant", e);
		}
	}

	@Override
	public AddressDto addAddress(AddressDto dto) throws BusinessException {
		try {
			Address address = mapper.map(dto, Address.class);
			Merchant merchant = new Merchant();
			merchant.setId(dto.getMerchantDto().getId());
			address.setMerchant(merchant);
			address = addressRepository.save(address);
			AddressDto addressDto = mapper.map(address, AddressDto.class);
			return addressDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while creating address", e);
		}
	}

	@Override
	public void deleteAddress(Long id) throws BusinessException {
		try {
			addressRepository.delete(id);
		} catch (Exception e) {
			throw new BusinessException("Exception while delete address", e);
		}
		
	}

	@Override
	public MerchantRuleDto updateMerchantRule(MerchantRuleDto dto) throws BusinessException {
		try {
			MerchantRule merchantRule  = merchantRuleRepository.findOne(dto.getId());
			dto.setCreated(merchantRule.getCreated());
			merchantRule = mapper.map(dto, MerchantRule.class);
			merchantRule = merchantRuleRepository.save(merchantRule);
			MerchantRuleDto merchantRuleDto = mapper.map(merchantRule, MerchantRuleDto.class);
			return merchantRuleDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while update merchant rule", e);
		}
	}

	@Override
	public MerchantRuleDto addMerchantRule(MerchantRuleDto dto, Long id) throws BusinessException {
		try {
			MerchantRule merchantRule = mapper.map(dto, MerchantRule.class);
			Merchant merchant = merchantRepository.findOne(id);
			merchant.setMerchantRule(merchantRule);
			merchant = merchantRepository.save(merchant);
			MerchantRuleDto merchantRuleDto = mapper.map(merchant.getMerchantRule(), MerchantRuleDto.class);
			return merchantRuleDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while update merchant rule", e);
		}
	}

	@Override
	public MerchantDto updateMerchant(MerchantDto dto) throws BusinessException {
		try {
			Merchant merchant = merchantRepository.findOne(dto.getId());
			merchant.setName(dto.getName());
			merchant.setBackgroundColor(dto.getBackgroundColor());
			merchant.setDescription(dto.getDescription());
			merchant.setPhone(dto.getPhone());
			
			merchant = merchantRepository.save(merchant);
			MerchantDto merchantDto = null;
			merchantDto = mapper.map(merchant, MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
			if (merchant.getAddresses() != null) {
				List<AddressDto> addressDtos = DozerHelper.map(mapper, merchant.getAddresses(), AddressDto.class);
				merchantDto.setAddressDtos(addressDtos);		
			}
			return merchantDto;
		} catch (Exception e) {
			throw new BusinessException("Exception while update merchant", e);
		}
	}

	@Override
	public List<MerchantDto> getAllMerchant() throws BusinessException {
		try {
			List <Merchant> merchants = (List<Merchant>) merchantRepository.findAll();
			List <MerchantDto> merchantDtos = new ArrayList<>();
			for (Merchant merchant : merchants) {
				MerchantDto dto = mapper.map(merchant ,MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
				List<AddressDto> addressDtos = null;
				if (merchant.getAddresses() != null) {
					addressDtos = new ArrayList<>();
					for (Address address : merchant.getAddresses()) {
						AddressDto addressDto =  mapper.map(address ,AddressDto.class);
						addressDtos.add(addressDto);
					}
					dto.setAddressDtos(addressDtos);
				}
				merchantDtos.add(dto);
			}
			return merchantDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while get all merchant", e);
		}
	}

	@Override
	public MerchantDto getMerchant(Long id) throws BusinessException {
		try {
			User user = new User();
			user.setId(id);
			Optional<Merchant> merchant =  merchantRepository.getMerchantByUser(user);
			if (!merchant.isPresent()) {
				return null;
			}
				MerchantDto dto = mapper.map(merchant.get() ,MerchantDto.class, DozerMapping.MERCHANT_VS_MERCHANTDTO.getKey());
				List<AddressDto> addressDtos = null;
				if (merchant.get().getAddresses() != null) {
					addressDtos = new ArrayList<>();
					for (Address address : merchant.get().getAddresses()) {
						AddressDto addressDto =  mapper.map(address ,AddressDto.class);
						addressDtos.add(addressDto);
					}
					dto.setAddressDtos(addressDtos);
				}
			return dto;
		} catch (Exception e) {
			throw new BusinessException("Exception while get all merchant", e);
		}
	}

	@Override
	public byte[] updateMerchantLogo(byte[] bytes, Long id) throws BusinessException {
		try {
			Merchant merchant = merchantRepository.findOne(id);
			merchant.setLogo(bytes);
			merchantRepository.save(merchant);
			return merchant.getLogo();
		} catch (Exception e) {
			throw new BusinessException("Exception while update logo", e);
		}
	}

	@Override
	public byte[] updateMerchantBackground(byte[] bytes, Long id) throws BusinessException {
		try {
			Merchant merchant = merchantRepository.findOne(id);
			merchant.setBackground(bytes);
			merchantRepository.save(merchant);
			return merchant.getBackground();
		} catch (Exception e) {
			throw new BusinessException("Exception while update background", e);
		}
	}

	@Override
	public void addFeedback(FeedbackDto feedbackDto, Long merchantId, Long userId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			User user = new User();
			user.setId(userId);
			Feedback feedback = new Feedback();
			feedback.setFeedback(feedbackDto.getFeedback());
			feedback.setMerchant(merchant);
			feedback.setUser(user);
			
			feedbackRepository.save(feedback);
		} catch (Exception e) {
			throw new BusinessException("Exception while update background", e);
		}
		
	}

	@Override
	public List<FeedbackDto> getFeedbacks(Long merchantId) throws BusinessException {
		try {
			Merchant merchant = new Merchant();
			merchant.setId(merchantId);
			List<Feedback> feedbacks = feedbackRepository.getFeedbackByMerchantId(merchant);
			List<FeedbackDto> feedbackDtos= null;
			if(feedbacks != null)
				feedbackDtos = DozerHelper.map(mapper, feedbacks, FeedbackDto.class, DozerMapping.FEEDBACK_VS_FEEDBACKDTO.getKey());
			
			return feedbackDtos;
		} catch (Exception e) {
			throw new BusinessException("Exception while update background", e);
		}
	}

}
