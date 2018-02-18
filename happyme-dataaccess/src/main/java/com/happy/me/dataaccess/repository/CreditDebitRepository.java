package com.happy.me.dataaccess.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.CreditDebit;
import com.happy.me.dataaccess.model.Merchant;

@Repository
public interface CreditDebitRepository extends CrudRepository<CreditDebit, Long> {

	List<CreditDebit> findByMerchant(Merchant merchant);


}

