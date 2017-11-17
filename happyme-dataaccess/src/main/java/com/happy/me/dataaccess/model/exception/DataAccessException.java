package com.happy.me.dataaccess.model.exception;

import com.happy.me.common.exception.BaseException;

public class DataAccessException extends BaseException {

    private static final long serialVersionUID = 2353089555858082672L;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message, null);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
