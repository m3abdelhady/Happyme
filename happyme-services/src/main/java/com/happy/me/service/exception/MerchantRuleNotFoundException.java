package com.happy.me.service.exception;


public class MerchantRuleNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public MerchantRuleNotFoundException() {
        super();
    }

    public MerchantRuleNotFoundException(String message) {
        super(message, null);
    }

    public MerchantRuleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
