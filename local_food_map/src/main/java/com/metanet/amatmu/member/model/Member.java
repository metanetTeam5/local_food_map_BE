package com.metanet.amatmu.member.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {

	private Long memberId;
	private String email;
	private String password;
	private String nickname;
	private String name;
	private Character gender;
	private String birthDate;
	private String phoneNumber;
	private String profileImg;
	private Date createDate;
	private Date updateDate;
	private String role;
}
