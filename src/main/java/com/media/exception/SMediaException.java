package com.media.exception;

import org.springframework.http.HttpStatus;

public class SMediaException extends RuntimeException {
	private HttpStatus status;
	private String message;
	
	
	public SMediaException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SMediaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public SMediaException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public SMediaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public SMediaException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	public SMediaException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	
	
}
