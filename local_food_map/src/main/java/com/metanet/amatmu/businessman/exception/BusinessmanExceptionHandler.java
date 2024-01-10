package com.metanet.amatmu.businessman.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessmanExceptionHandler {

	@ExceptionHandler(BusinessmanException.class)
	public ResponseEntity<String> businessmanException(BusinessmanException e){
		return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getErrorCode().getMsg());
	}
}