package com.happy.me.service.exception;


public class MerchantAssignException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public MerchantAssignException() {
        super();
    }

    public MerchantAssignException(String message) {
        super(message, null);
    }

    public MerchantAssignException(String message, Throwable cause) {
        super(message, cause);
    }
}
