package com.happy.me.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.happy.me.common.enums.AppErrorCode;
import com.happy.me.common.rest.AppResponse;
import com.happy.me.common.rest.Error;
import com.happy.me.common.rest.ErrorsResponse;




public class WebUtils {

	private WebUtils() {

	}

	/**
	 * Prepares a single error response
	 * 
	 * @param error
	 *            the error code to include
	 * @param status
	 *            the HTTP status to include
	 * @param parameters
	 *            the messages parameters to include
	 * @return a single error {@link ResponseEntity} object
	 */
	public static ResponseEntity<AppResponse> prepareErrorResponse(
			HttpStatus status, AppErrorCode error, String... parameters) {
		List<Error> errors = new ArrayList<Error>();
		if (parameters != null) {
			errors.add(new Error(error.getCode(), error.getKey(), parameters));
		} else {
			errors.add(new Error(error.getCode(), error.getKey()));
		}
		return new ResponseEntity<AppResponse>(new ErrorsResponse(errors),
				status);
	}


}