package com.happy.me.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.extra.MerchantCardReport;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;
import com.happy.me.dataaccess.model.UserCard;

@Repository
public interface UserCardRepository extends CrudRepository<UserCard, Long> {


    @Query("SELECT m FROM UserCard m where m.user = :user and m.merchant = :merchant")
	public Optional<UserCard> getUserCard(@Param("user") User user, @Param("merchant") Merchant merchant);
    
    public Optional<UserCard> findByCardNumber(String cardNumber);
    
    @Query("SELECT m FROM UserCard m where m.user = :user")
	public List<UserCard> getUserCards(@Param("user") User user);

    @Query("SELECT m FROM UserCard m where m.merchant = :merchant")
	public List<UserCard> getMerchantCard(@Param("merchant") Merchant merchant);
    
    @Query("select new com.happy.me.dataaccess.extra.MerchantCardReport (r.merchant, count(r.merchant)) from UserCard r group by r.merchant ")
	public List<MerchantCardReport> getMerchantReport();

    @Query("SELECT count(m) FROM UserCard m where m.merchant = :merchant")
	public Long getCardsNumber(@Param("merchant") Merchant merchant);

    @Query("SELECT sum(m.point) FROM UserCard m where m.merchant = :merchant")
	public Long getPointsNumber(@Param("merchant") Merchant merchant);
	
}

