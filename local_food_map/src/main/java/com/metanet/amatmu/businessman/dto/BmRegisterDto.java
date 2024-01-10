package com.metanet.amatmu.businessman.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmRegisterDto {
	
	private String email;
	private String password;
	private String nickname;
	private String name;
	private String gender;
	private String birthDate;
	private String phoneNumber;
	
	private String companyName;
	private String licenseNumber;
	private String registration;
	private String report;
	private String bankbook;
	private String account;
}
