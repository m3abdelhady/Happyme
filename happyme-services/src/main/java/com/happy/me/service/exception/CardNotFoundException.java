package com.happy.me.service.exception;


public class CardNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public CardNotFoundException() {
        super();
    }

    public CardNotFoundException(String message) {
        super(message, null);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
