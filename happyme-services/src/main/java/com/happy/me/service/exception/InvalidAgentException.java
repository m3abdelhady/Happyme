package com.happy.me.service.exception;


public class InvalidAgentException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public InvalidAgentException() {
        super();
    }

    public InvalidAgentException(String message) {
        super(message, null);
    }

    public InvalidAgentException(String message, Throwable cause) {
        super(message, cause);
    }
}
