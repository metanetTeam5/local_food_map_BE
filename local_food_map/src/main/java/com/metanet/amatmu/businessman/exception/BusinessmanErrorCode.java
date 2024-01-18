package com.metanet.amatmu.businessman.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessmanErrorCode implements Code{

	REGISTER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 실패"),
	INVALID_REGISTER(HttpStatus.BAD_REQUEST, "회원가입 양식 오류"),
	INVALID_ROLE(HttpStatus.UNAUTHORIZED, "권한 없는 사용자"),
	RESTAURANT_DUPLICATED(HttpStatus.CONFLICT, "중복된 식당"),
	BM_NOT_FOUND(HttpStatus.BAD_REQUEST, "사업자 정보를 찾을 수 없습니다."),
	RESTAURANT_NOT_MATCHED(HttpStatus.BAD_REQUEST, "관리하는 식당이 아닙니다."),
	LICENSE_DUPLICATED(HttpStatus.CONFLICT, "중복된 사업자번호");

	private final HttpStatus status;
	private final String msg;
}
