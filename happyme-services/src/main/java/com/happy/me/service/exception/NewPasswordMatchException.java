package com.happy.me.service.exception;


public class NewPasswordMatchException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public NewPasswordMatchException() {
        super();
    }

    public NewPasswordMatchException(String message) {
        super(message, null);
    }

    public NewPasswordMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
