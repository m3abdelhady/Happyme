package com.happy.me.service.exception;


public class MerchantNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public MerchantNotFoundException() {
        super();
    }

    public MerchantNotFoundException(String message) {
        super(message, null);
    }

    public MerchantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
