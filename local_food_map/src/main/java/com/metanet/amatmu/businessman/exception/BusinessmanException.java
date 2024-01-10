package com.metanet.amatmu.businessman.exception;

import lombok.Getter;

@Getter
public class BusinessmanException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final BusinessmanErrorCode errorCode;
	
	public BusinessmanException(BusinessmanErrorCode e) {
		super(e.getMsg());
		this.errorCode = e;
	}
}