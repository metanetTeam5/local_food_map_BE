package com.metanet.amatmu.member.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberRegisterDto {

	private String email;
	private String password;
	private String nickname;
	private String name;
	private String gender;
	private String birthDate;
	private String phoneNumber;
}
