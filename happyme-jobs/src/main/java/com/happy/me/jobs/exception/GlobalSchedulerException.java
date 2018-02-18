package com.happy.me.jobs.exception;

import com.happy.me.common.exception.BaseException;

/**
 * 
 * @author MBaumy this exception is throw whenever a business exception occurs
 */
public class GlobalSchedulerException extends BaseException {

    private static final long serialVersionUID = 2353089555858082672L;

    public GlobalSchedulerException() {
        super();
    }

    public GlobalSchedulerException(String message) {
        super(message, null);
    }

    public GlobalSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
