package com.cognizant.ws.exception;

public class DocumentumException extends Exception {
	private static final long serialVersionUID = -4372776309073614775L;
	String description;
	public DocumentumException() {
		super();
	}

	public DocumentumException(String message) {
		super(message);
	}
	
	public DocumentumException(String message,Exception e) {
		super(message,e);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
