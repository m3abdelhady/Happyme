package com.happy.me.service.exception;


public class EmailFoundServiceException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public EmailFoundServiceException() {
        super();
    }

    public EmailFoundServiceException(String message) {
        super(message, null);
    }

    public EmailFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
