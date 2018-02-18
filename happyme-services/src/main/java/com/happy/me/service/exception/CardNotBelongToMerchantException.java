package com.happy.me.service.exception;


public class CardNotBelongToMerchantException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public CardNotBelongToMerchantException() {
        super();
    }

    public CardNotBelongToMerchantException(String message) {
        super(message, null);
    }

    public CardNotBelongToMerchantException(String message, Throwable cause) {
        super(message, cause);
    }
}
