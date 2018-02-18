package com.happy.me.service.exception;

public class MerchantCouponeInvalidException extends ServiceException {

    private static final long serialVersionUID = 2353089555858082672L;

    public MerchantCouponeInvalidException() {
        super();
    }

    public MerchantCouponeInvalidException(String message) {
        super(message, null);
    }

    public MerchantCouponeInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
