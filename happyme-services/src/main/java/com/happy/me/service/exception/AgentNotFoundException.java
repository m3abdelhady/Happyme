package com.happy.me.service.exception;


public class AgentNotFoundException extends ServiceException {

    private static final long serialVersionUID = 6359019708415534922L;

    public AgentNotFoundException() {
        super();
    }

    public AgentNotFoundException(String message) {
        super(message, null);
    }

    public AgentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
