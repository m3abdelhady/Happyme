package com.happy.me.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happy.me.business.CardBusiness;
import com.happy.me.business.CouponsBusiness;
import com.happy.me.business.MerchantBusiness;
import com.happy.me.business.UserBusiness;
import com.happy.me.business.exception.BusinessException;
import com.happy.me.common.dto.CardSummaryDto;
import com.happy.me.common.dto.MerchantDto;
import com.happy.me.service.JobService;
import com.happy.me.service.exception.ServiceException;

@Service("jobService")
public class JobServiceImpl implements JobService {

	
	@Autowired
	CouponsBusiness couponsBusiness;

	@Autowired
	UserBusiness userBusiness;

	@Autowired
	MerchantBusiness merchantBusiness;

	@Autowired
	CardBusiness cardBusiness;
	
	@Override
	public void job() throws ServiceException {
		System.out.println("JobServiceImpl.job()");
		
	}

	@Override
	public void calculateExpirePoint() throws ServiceException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date startDate = cal.getTime();
//			System.err.println("firstDayOfTheMonth : " + startDate);
			List<MerchantDto> merchantDtos = merchantBusiness.getAllMerchantWithRule();
			merchantDtos.forEach(m -> System.out.println(m.getId()));
			for (MerchantDto merchantDto : merchantDtos) {
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(new Date(System.currentTimeMillis()));
				cal1.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//				System.out.println("Rule " + merchantDto.getMerchantRuleDto().getExpiry());
				cal1.add(Calendar.MONTH, Math.toIntExact(merchantDto.getMerchantRuleDto().getExpiry()) * -1);
				Date endDate = cal1.getTime();
				cal1.add(Calendar.MONTH, -1);
				Date expiryDate = cal1.getTime();
//				System.out.println("firstDayOfTheMonth : " + startDate + "    ,expiryDate : " + expiryDate +"   , End"+ endDate );
				List<CardSummaryDto> cardSummaryDtos = cardBusiness.getCardSummaryByMerchant(merchantDto.getId());
//				System.out.println("JobServiceImpl.calculateExpirePoint() " + cardSummaryDtos.size());
				for (CardSummaryDto cardSummaryDto : cardSummaryDtos) {
					Long points = cardBusiness.getTotalPoint(cardSummaryDto.getUserCardDto().getId(), startDate, endDate);
					Long allPoints = cardBusiness.getTotalPoint(cardSummaryDto.getUserCardDto().getId(), startDate, expiryDate);
					Long reedemPoints = cardBusiness.getTotalReedemPoint(cardSummaryDto.getUserCardDto().getId(), startDate, expiryDate);
					if ((allPoints - points) > reedemPoints) {
//						System.err.println((allPoints - points) - reedemPoints + " is expire ,point should : " + points);
						cardBusiness.updateCardPoint(cardSummaryDto.getId(), points);
					}					
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new ServiceException("Exception while getting all merchant", e);
		}
		
	}

	

}
