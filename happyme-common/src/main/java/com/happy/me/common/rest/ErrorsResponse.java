package com.happy.me.common.rest;

import java.io.Serializable;
import java.util.List;



public class ErrorsResponse implements Serializable, AppResponse{


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Error> errors ;
    /**
     * 
     */
    public ErrorsResponse() {
        super();
    }

    /**
     * @param status
     */
    public ErrorsResponse(List<Error> errors) {
        super();
        this.errors = errors;
    }

    /**
     * @return the errors
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

}
