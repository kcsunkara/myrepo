package com.cognizant.ui.exception;


public class ErrorMessage  {
	private String title;
	private String code;
	private ErrorType errorType;
	private String exceptionName;
	private String message;

	
	public ErrorMessage() {
	
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	@Override
	public String toString() {
		return "ErrorMessage [title=" + title + ", code=" + code
				+ ", errorType=" + errorType
				+ ", exceptionName=" + exceptionName + ", message=" + message
				+ "]";
	}





	
}