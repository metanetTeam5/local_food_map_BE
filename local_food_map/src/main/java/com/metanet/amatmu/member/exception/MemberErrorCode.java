package com.metanet.amatmu.member.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements Code{

	REGISTER_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 실패"),
	INVALID_REGISTER(HttpStatus.BAD_REQUEST, "회원가입 양식 오류"),
	EMAIL_DUPLICATED(HttpStatus.CONFLICT, "중복된 이메일"),
	NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "중복된 닉네임"),
	PHONE_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "중복된 전화번호"),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 형식"),
	EMAIL_NOT_EXISTS(HttpStatus.BAD_REQUEST, "이메일이 존재하지 않습니다."),
	WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
	UPLOAD_PROFILEIMG_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 실패"),
	MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),
	MEMBER_NOT_ADMIN(HttpStatus.BAD_REQUEST, "관리자 계정이 아닙니다.");

	private final HttpStatus status;
	private final String msg;
}
