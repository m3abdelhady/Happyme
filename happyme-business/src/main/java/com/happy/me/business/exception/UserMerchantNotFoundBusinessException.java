package com.happy.me.business.exception;

import com.happy.me.common.exception.BaseException;

public class UserMerchantNotFoundBusinessException extends BaseException {

    private static final long serialVersionUID = 2353089555858082672L;

    public UserMerchantNotFoundBusinessException() {
        super();
    }

    public UserMerchantNotFoundBusinessException(String message) {
        super(message, null);
    }

    public UserMerchantNotFoundBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
