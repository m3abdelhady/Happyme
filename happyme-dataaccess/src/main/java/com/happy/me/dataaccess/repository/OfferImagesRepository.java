package com.happy.me.dataaccess.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.OfferImages;

@Repository
public interface OfferImagesRepository extends CrudRepository<OfferImages, Long> {
	 
	
	 @Modifying
	 @Query("delete from OfferImages o WHERE o.id = :id")
	 public void deleteOfferImages(@Param("id") Long id);

	
}

