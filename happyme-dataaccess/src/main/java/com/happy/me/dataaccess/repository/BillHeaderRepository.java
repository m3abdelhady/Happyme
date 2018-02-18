package com.happy.me.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.BillHeader;
import com.happy.me.dataaccess.model.Merchant;

@Repository
public interface BillHeaderRepository extends CrudRepository<BillHeader, Long> {

	List<BillHeader> findByMerchant(Merchant merchant);


	
}

