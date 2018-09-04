package com.sblox.common.exception;

public class BaseException extends Exception {

	private static final long serialVersionUID = 981549330322374115L;

	private final String errorCode;
	private final String message;
	private final Throwable cause;

	public BaseException() {
		super();
		this.errorCode = null;
		this.message = null;
		this.cause = null;
	}

	public BaseException(String errorCode) {
		super();
		this.errorCode = errorCode;
		this.message = null;
		this.cause = null;
	}
	
//	public BaseException(AppErrorCode error) {
//		super();
//		this.errorCode = errorCode;
//		this.message = null;
//		this.cause = null;
//	}

	public BaseException(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.cause = null;
	}

	public BaseException(String errorCode, String message, Throwable cause) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.cause = cause;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

}