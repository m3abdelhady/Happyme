package com.happy.me.dataaccess.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.CardTransaction;
import com.happy.me.dataaccess.model.UserCard;

@Repository
public interface CardTransactionRepository extends CrudRepository<CardTransaction, Long> {

    @Query("select sum(point) from CardTransaction where userCard = :userCard and transactionType = 'INCOME'")
	public Long getIncomeCardPoint(@Param("userCard") UserCard userCard);
    
    @Query("select sum(point) from CardTransaction where userCard = :userCard and transactionType = 'OUTCOME'")
	public Long getOutcomeCardPoint(@Param("userCard") UserCard userCard);
    
    public List<CardTransaction> findByUserCard(UserCard userCard);

    @Query("select sum(point) from CardTransaction where userCard = :userCard and transactionDate >= :end and transactionDate <= :start and transactionType = 'INCOME'")
	public Long getTotalPoint(@Param("userCard") UserCard userCard,@Param("start") Date startDate,@Param("end") Date endDate);

    @Query("select sum(point) from CardTransaction where userCard = :userCard and transactionDate >= :end and transactionDate <= :start and transactionType = 'OUTCOME'")
	public Long getTotalReedemPoint(@Param("userCard") UserCard userCard,@Param("start") Date startDate,@Param("end") Date endDate);
}

