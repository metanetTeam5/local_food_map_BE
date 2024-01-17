package com.metanet.amatmu.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberLoginResultDto {
	private long userId;
	private long bmId;
	private String userEmail;
	private String token;
	private String userProfileImg;
}
