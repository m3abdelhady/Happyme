package com.happy.me.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	
}

