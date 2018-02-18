package com.happy.me.service.exception;


public class ResellerNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public ResellerNotFoundException() {
        super();
    }

    public ResellerNotFoundException(String message) {
        super(message, null);
    }

    public ResellerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
