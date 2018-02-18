package com.happy.me.dataaccess.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Coupons;
import com.happy.me.dataaccess.model.Merchant;

@Repository
public interface CouponsRepository extends CrudRepository<Coupons, Long> {
	
    @Query("select o from Coupons o where o.start <= :date and o.end >= :date")
	List<Coupons> getActiveCoupons(@Param("date") Date date);
    
    @Query("select o from Coupons o where o.merchant = :merchant")
	List<Coupons> getMerchantCoupons(@Param("merchant") Merchant merchant);
   
    @Query("select o from Coupons o where o.merchant = :merchant and  o.start <= :date and o.end >= :date")
	List<Coupons> getMerchantActiveCoupons(@Param("merchant") Merchant merchant, @Param("date") Date time);
   
    @Query("select o from Coupons o where o.id = :couponId and  o.start <= :date and o.end >= :date")
	Optional<Coupons> getActiveCoupon(@Param("couponId") Long couponId, @Param("date") Date time);
	
}

