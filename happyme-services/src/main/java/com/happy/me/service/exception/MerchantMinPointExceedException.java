package com.happy.me.service.exception;


public class MerchantMinPointExceedException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public MerchantMinPointExceedException() {
        super();
    }

    public MerchantMinPointExceedException(String message) {
        super(message, null);
    }

    public MerchantMinPointExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
