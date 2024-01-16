package com.metanet.amatmu.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateMemberInfoDto {

	private String password;
	private String newPassword;
	private String nickname;
}
