package com.happy.me.service.exception;

import com.happy.me.common.exception.BaseException;


public class ServiceException extends BaseException {

    private static final long serialVersionUID = 2353089555858082672L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message, null);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
