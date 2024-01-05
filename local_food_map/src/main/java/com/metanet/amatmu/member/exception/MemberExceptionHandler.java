package com.metanet.amatmu.member.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<String> memberException(MemberException e){
		return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getErrorCode().getMsg());
	}
}
