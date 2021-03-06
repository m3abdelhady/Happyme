package com.happy.me.service.exception;


public class UserMaxPointExceedException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public UserMaxPointExceedException() {
        super();
    }

    public UserMaxPointExceedException(String message) {
        super(message, null);
    }

    public UserMaxPointExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
