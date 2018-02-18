package com.happy.me.dataaccess.dao;

import com.happy.me.dataaccess.exception.DataAccessException;

public interface ReferenceNumberSeqDao {


	public long getNextReferenceNumberSeq() throws DataAccessException;
	
}
