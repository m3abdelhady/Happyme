package com.happy.me.service.exception;


public class WrongPasswordException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String message) {
        super(message, null);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
