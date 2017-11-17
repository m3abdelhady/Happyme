package com.happy.me.service.exception;


public class UserNameFoundServiceException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public UserNameFoundServiceException() {
        super();
    }

    public UserNameFoundServiceException(String message) {
        super(message, null);
    }

    public UserNameFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
