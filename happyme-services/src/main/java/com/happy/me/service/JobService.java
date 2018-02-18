package com.happy.me.service;

import com.happy.me.service.exception.ServiceException;

public interface JobService {


	public void job() throws ServiceException;

	public void calculateExpirePoint() throws ServiceException;

}
