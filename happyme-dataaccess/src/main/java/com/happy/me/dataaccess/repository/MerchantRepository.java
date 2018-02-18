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
    
    @Query("select count(e)>0 from Merchant e where e.id = :id")
    boolean existsById(@Param("id") Long id);

	@Query("SELECT m FROM Merchant m where m.user.createdBy = :user")
	public List<Merchant> getMerchantCreatedByUser(@Param("user") User user);

	public Optional<Merchant> findById(Long merchantId);
}

