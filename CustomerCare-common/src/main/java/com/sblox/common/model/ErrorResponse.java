package com.sblox.common.model;

import com.sblox.common.exception.BaseException;

public class ErrorResponse {

    private String errorCode;
    private String errorMessage;

    public ErrorResponse() {

    }

    public ErrorResponse(BaseException e) {
	errorCode = e.getErrorCode();
	errorMessage = e.getMessage();
    }

    public ErrorResponse(String errorCode, Throwable e) {
	this.errorCode = errorCode;
	errorMessage = e.getMessage();
    }
    
    public ErrorResponse(String errorCode, String message) {
	this.errorCode = errorCode;
	errorMessage = message;
    }

    public String getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

    public String getErrorMessage() {
	return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
    }

}
