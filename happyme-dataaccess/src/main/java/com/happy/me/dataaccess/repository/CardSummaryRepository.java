package com.happy.me.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.CardSummary;
import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.UserCard;

@Repository
public interface CardSummaryRepository extends CrudRepository<CardSummary, Long> {

	public Optional<CardSummary> findByUserCard(UserCard userCard);

	public List<CardSummary> findByUserCardIn(List<UserCard> card);

	@Query("select c from CardSummary c where c.userCard.merchant = :merchant")
	public List<CardSummary> findByMerchant(@Param("merchant") Merchant merchant);

	
}

