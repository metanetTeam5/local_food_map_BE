package com.metanet.amatmu.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberRegisterResultDto {

	private String email;
	private String nickname;
	private String name;
	private String phoneNumber;
}
