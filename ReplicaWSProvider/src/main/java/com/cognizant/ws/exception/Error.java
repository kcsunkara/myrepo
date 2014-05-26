package com.cognizant.ws.exception;

import java.util.List;

public class Error {
	private List<MessageResponse> validationErrors;
	
	public List<MessageResponse> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<MessageResponse> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
