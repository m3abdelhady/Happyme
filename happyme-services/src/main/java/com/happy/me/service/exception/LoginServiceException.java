package com.happy.me.service.exception;


public class LoginServiceException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public LoginServiceException() {
        super();
    }

    public LoginServiceException(String message) {
        super(message, null);
    }

    public LoginServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
