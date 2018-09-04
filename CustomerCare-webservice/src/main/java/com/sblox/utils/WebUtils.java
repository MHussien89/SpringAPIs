//package com.sblox.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import com.sblox.common.exception.BaseException;
//import com.sblox.common.model.AppResponse;
//
//public class WebUtils {
//
//	private WebUtils() {
//
//	}
//
//	/**
//	 * Prepares a single error response
//	 * 
//	 * @param error
//	 *            the error code to include
//	 * @param status
//	 *            the HTTP status to include
//	 * @param parameters
//	 *            the messages parameters to include
//	 * @return a single error {@link ResponseEntity} object
//	 */
//	public static ResponseEntity<AppResponse> prepareErrorResponse(HttpStatus status, BaseException exception) {
//		List<Error> errors = new ArrayList<Error>();
//			errors.add(new Error(exception.getErrorCode(), exception.getMessage()));
//		return new ResponseEntity<AppResponse>(new ErrorsResponse(errors), status);
//	}
//
//}
