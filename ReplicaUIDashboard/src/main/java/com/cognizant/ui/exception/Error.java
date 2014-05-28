package com.cognizant.ui.exception;

import java.util.List;

public class Error {
	private List<ErrorMessage> errors;
	
	public List<ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorMessage> errors) {
		this.errors = errors;
	}
}
