package com.happy.me.dataaccess.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.model.BillLines;

@Repository
public interface BillLinesRepository extends CrudRepository<BillLines, Long> {


	
}

