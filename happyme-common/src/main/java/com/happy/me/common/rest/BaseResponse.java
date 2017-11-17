package com.happy.me.common.rest;

import java.io.Serializable;

import com.happy.me.common.enums.AppErrorCode;


public class BaseResponse implements Serializable {

 
	private static final long serialVersionUID = 1L;
	protected Error status;

    public BaseResponse() {
        super();
    }

    public BaseResponse(Error status) {
        super();
        this.status = status;
    }

    public BaseResponse(AppErrorCode errorCode, String description) {
        this(new Error(errorCode, description));
    }

    public Error getStatus() {
        return status;
    }

    public void setStatus(Error status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseResponse [status=" + status + "]";
    }
}