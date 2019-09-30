package com.instra.superrduperr.exception;

public class SuperrDuperrException extends Exception {

	private String message;
	private String code;
	
	public SuperrDuperrException(String message) {
		super(message);
		this.message = message;
	}
	
	public SuperrDuperrException(String code, String message) {
		super(message);
		this.message = message;
		this.code = code;
	}

	public SuperrDuperrException(Throwable cause) {
		super(cause);
		this.message = cause.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

}
