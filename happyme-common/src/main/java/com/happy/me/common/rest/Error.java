package com.happy.me.common.rest;

import java.util.Arrays;
import java.util.List;

import com.happy.me.common.enums.AppErrorCode;

public class Error implements AppResponse {


	private static final long serialVersionUID = 1L;
	private final Integer code;
    private final String message;
    private final List<String> parameters;

    public Error() {
        super();
        this.code = null;
        this.message = null;
        this.parameters = null;
    }

    public Error(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
        this.parameters = null;
    }

    public Error(AppErrorCode errorCode, String message) {
        super();
        this.code = errorCode.getCode() ;
        this.message = message;
        this.parameters = null;
    }


    /**
     * @param code
     * @param message
     * @param parameter
     */
    public Error(Integer code, String message, List<String> parameter) {
        super();
        this.code = code;
        this.message = message;
        this.parameters = parameter;
    }


    /**
     * @param code
     * @param message
     * @param parameter
     */
    public Error(Integer code, String message, String... parameter) {
        super();
        this.code = code;
        this.message = message;
        this.parameters = Arrays.asList(parameter);
    }

    public Error(AppErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getKey();
        this.parameters = null;
    }

    public Error(AppErrorCode errorCode,List<String> parameter) {
        this.code = errorCode.getCode();
        this.message = errorCode.getKey();
        this.parameters = parameter;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
