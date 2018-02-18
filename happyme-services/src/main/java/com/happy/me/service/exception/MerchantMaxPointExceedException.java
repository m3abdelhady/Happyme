package com.happy.me.service.exception;


public class MerchantMaxPointExceedException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public MerchantMaxPointExceedException() {
        super();
    }

    public MerchantMaxPointExceedException(String message) {
        super(message, null);
    }

    public MerchantMaxPointExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
