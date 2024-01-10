package com.metanet.amatmu.exception;

public class QueryFailedException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public QueryFailedException(String message) {
		super(message);
	}

}
