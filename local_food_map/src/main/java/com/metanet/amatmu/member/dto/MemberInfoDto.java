package com.metanet.amatmu.member.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInfoDto {
	private String email;
	private String nickname;
	private String name;
	private Character gender;
	private String birthDate;
	private String phoneNumber;
	private String profileImg;
	private Date createDate;
}
