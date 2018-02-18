package com.happy.me.dataaccess.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.MerchantRule;

@Repository
public interface MerchantRuleRepository extends CrudRepository<MerchantRule, Long> {

	@Query("SELECT r FROM Merchant m join m.merchantRule r where r.id = m.merchantRule and  m.id = :id")
	public Optional<MerchantRule> getRuleByMerchant(@Param("id") Long id);

}
