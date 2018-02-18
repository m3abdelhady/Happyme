package com.happy.me.dataaccess.dao.impl;

import java.math.BigDecimal;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.happy.me.dataaccess.dao.ReferenceNumberSeqDao;
import com.happy.me.dataaccess.exception.DataAccessException;

@Repository("referenceNumberSeqDao")
public class ReferenceNumberSeqDaoJpa extends JPADao<Object> implements ReferenceNumberSeqDao {

	private static final long serialVersionUID = -1602475534300427978L;
	
	private static final Logger LOGGER = LoggerFactory
            .getLogger(ReferenceNumberSeqDaoJpa.class);

	public ReferenceNumberSeqDaoJpa() {
		super();
	}

	@Override	
	public long getNextReferenceNumberSeq() throws DataAccessException {
		LOGGER.debug("[getNextReferenceNumberSeq()] Starting to get reference number sequence ");
		try {
			Query q = em.createNativeQuery("SELECT reference_number_seq.nextval FROM dual");
			BigDecimal number = (BigDecimal) q.getSingleResult();
			LOGGER.debug("[getNextReferenceNumberSeq()] Successflly get reference number sequence: " + number);
			return number.longValue();			
		} catch (Exception e) {
			LOGGER.error("[getNextReferenceNumberSeq()] failed while getting reference number sequence", e);
			throw new DataAccessException(
					"failed while getting reference number sequence", e);
		}
	}

	
}
