package com.happy.me.common.enums;

public enum TransactionType {

	INCOME("INCOME"),
    OUTCOME("OUTCOME")
 

    ;

    private String key;

    private TransactionType(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}