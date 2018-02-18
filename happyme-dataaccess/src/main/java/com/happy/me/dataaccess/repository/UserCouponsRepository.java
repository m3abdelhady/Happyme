package com.happy.me.dataaccess.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.model.UserCoupons;

@Repository
public interface UserCouponsRepository extends CrudRepository<UserCoupons, Long> {

	@Query("select o from UserCoupons o where o.user = :user and o.coupons.start <= :date and o.coupons.end >= :date")
	List<UserCoupons> getCoupons(@Param("user") User user, @Param("date") Date date);

	@Query("select o from UserCoupons o where o.user = :user and o.coupons.merchant = :merchant")
	List<UserCoupons> getCouponsForUser(@Param("user") User user,  @Param("merchant") Merchant merchant);
	
}

