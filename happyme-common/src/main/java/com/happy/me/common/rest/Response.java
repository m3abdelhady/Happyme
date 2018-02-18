package com.happy.me.common.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Response {
	
	List<?> response;
	
	Object object;
	

	public Response(List<?> list) {
		super();
		this.response = list;
	}

	public Response(Object object) {
		super();
		this.object = object;
	}



	public List<?> getResponse() {
		return response;
	}


	public void setResponse(List<?> response) {
		this.response = response;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	
	
	

}
