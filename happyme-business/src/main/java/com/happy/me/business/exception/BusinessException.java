package com.happy.me.business.exception;

import com.happy.me.common.exception.BaseException;

public class BusinessException extends BaseException {

    private static final long serialVersionUID = 2353089555858082672L;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message, null);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
