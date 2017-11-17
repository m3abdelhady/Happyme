package com.happy.me.dataaccess.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.Offer;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {
	
    @Query("select o from Offer o where o.start <= :date and o.end >= :date")
	List<Offer> getActiveOffer(@Param("date") Date date);
    
    @Query("select o from Offer o where o.merchant = :merchant")
	List<Offer> getMerchantOffer(@Param("merchant") Merchant merchant);
	
}

