package com.happy.me.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.Merchant;
import com.happy.me.dataaccess.model.PaymentLog;

@Repository
public interface PaymentLogRepository extends CrudRepository<PaymentLog, Long> {

	List<PaymentLog> findByMerchant(Merchant merchant);
	
	
}

