package com.metanet.amatmu.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberLoginDto {

	private String email;
	private String password;
}
