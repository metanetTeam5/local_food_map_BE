package com.metanet.amatmu.exception;

public class UpdateFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UpdateFailedException(String message) {
		super(message);
	}

}
