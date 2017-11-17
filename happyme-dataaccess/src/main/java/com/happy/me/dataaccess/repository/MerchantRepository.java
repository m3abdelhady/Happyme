package com.happy.me.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.User;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, Long> {

	
	@Query("SELECT m FROM Merchant m where m.active is true")
	public List<Merchant> getActiveMerchant();
	
    @Query("SELECT m FROM Merchant m where m.user = :id")
	public Optional<Merchant> getMerchantByUser(@Param("id") User user);
}

