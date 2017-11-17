package com.happy.me.service.exception;


public class NotAuthorizedException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message, null);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
