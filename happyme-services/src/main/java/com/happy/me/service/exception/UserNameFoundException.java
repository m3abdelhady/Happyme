package com.happy.me.service.exception;


public class UserNameFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public UserNameFoundException() {
        super();
    }

    public UserNameFoundException(String message) {
        super(message, null);
    }

    public UserNameFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
