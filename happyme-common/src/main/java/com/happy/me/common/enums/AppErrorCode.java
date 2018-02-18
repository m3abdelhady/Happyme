package com.happy.me.common.enums;



public enum AppErrorCode {

    SUCCESS 	(0, "success"),
    FAILURE 	(1, "failure"),
    EMAIL_ALREADY_FOUND (2, "email.already.found"),
    USERNAME_ALREADY_FOUND (3, "username.already.found"),
    PASSWORD_ERROR (4, "password.error"),
    EMAIL_NOT_FOUND (5, "email.not.found"),
    NON_AUTHORITATIVE_INFORMATION(6, "non.authoritative.information"),
    INVALID_COUPONE(7, "invalid.coupone"),
    MERCHANT_COUPONE_INVALID(8, "merchant.coupone.invalid"),
    INVALID_AGENT(9, "invalid.agent"),
    INVALID_USER(10, "invalid.user"),
    INVALID_CARD(11, "invalid.card"),
    INVALID_MERCHANT_CARD(12, "invalid.merchant.card"),
    INVALID_MERCHANT(13, "invalid.merchant.card"),
    PASSWORD_MATCH(14, "password.match"),
    INVALID_RESELLER(15, "invalid.reseller"),
    INVALID_MERCHANT_RULE(16, "invalid.merchant.rule"),
    MAX_POINT_REDEEM(17, "max.point.redeem"),
    MIN_POINT_REDEEM(18, "min.point.redeem"),
    USER_MAX_POINT_EXCEED(19, "user.max.point.exceed"),
    INVALID_MERCHANT_ASSIGN(20, "invalid.merchant.assign"),
    
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
