package com.happy.me.service.exception;


public class UserNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message, null);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
