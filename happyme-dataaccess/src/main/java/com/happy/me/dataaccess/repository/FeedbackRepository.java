package com.happy.me.dataaccess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Feedback;
import com.happy.me.dataaccess.model.Merchant;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Query("SELECT m FROM Feedback m where m.merchant = :id")
	public List<Feedback> getFeedbackByMerchantId(@Param("id") Merchant merchant);
	
}

