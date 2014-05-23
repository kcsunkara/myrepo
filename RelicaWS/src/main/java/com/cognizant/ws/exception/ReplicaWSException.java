package com.cognizant.ws.exception;

import java.util.LinkedHashMap;

public class ReplicaWSException extends Exception {
	private static final long serialVersionUID = -4372776309073614775L;

	public LinkedHashMap<String, String> response;

    public ReplicaWSException(String message,LinkedHashMap<String, String> response)
    {
    	super(message);
        this.response = response;
    }
	public ReplicaWSException() {
		super();
	}

	public ReplicaWSException(String message) {
		super(message);
	}
	
	public ReplicaWSException(String message,Exception e) {
		super(message,e);
	}
}
