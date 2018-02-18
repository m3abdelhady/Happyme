package com.happy.me.service.exception;

public class InvalidCouponeException extends ServiceException {

    private static final long serialVersionUID = 2353089555858082672L;

    public InvalidCouponeException() {
        super();
    }

    public InvalidCouponeException(String message) {
        super(message, null);
    }

    public InvalidCouponeException(String message, Throwable cause) {
        super(message, cause);
    }
}
