package com.happy.me.service.exception;


public class EmailFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public EmailFoundException() {
        super();
    }

    public EmailFoundException(String message) {
        super(message, null);
    }

    public EmailFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
