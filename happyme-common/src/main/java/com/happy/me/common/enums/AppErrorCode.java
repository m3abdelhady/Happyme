package com.happy.me.common.enums;



public enum AppErrorCode {

    SUCCESS 	(0, "success"),
    FAILURE 	(1, "failure"),
    EMAIL_ALREADY_FOUND (2, "email.already.found"),
    USERNAME_ALREADY_FOUND (3, "username.already.found"),
    PASSWORD_ERROR (4, "password.error"),
    EMAIL_NOT_FOUND (5, "email.not.found"),
    NON_AUTHORITATIVE_INFORMATION(6, "non.authoritative.information")
    
    
    ;

    private int code;
    private String key;

    AppErrorCode(int code, String key) {
        this.code = code;
        this.key = key;
    }

    public int getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
