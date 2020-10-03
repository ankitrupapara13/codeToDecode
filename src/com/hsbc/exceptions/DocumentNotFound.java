package com.hsbc.exceptions;

@SuppressWarnings("serial")
public class DocumentNotFound extends Exception {
	
	public DocumentNotFound(String message) {
		super(message);
	}
	
	public DocumentNotFound(String message, Throwable cause) {
		super(message, cause);
	}
}
