package com.metanet.amatmu.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final MemberErrorCode errorCode;
	
	public MemberException(MemberErrorCode e) {
		super(e.getMsg());
		this.errorCode = e;
	}
}
