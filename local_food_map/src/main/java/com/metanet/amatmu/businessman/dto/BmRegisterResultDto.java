package com.metanet.amatmu.businessman.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BmRegisterResultDto {

	private String email;
	private String nickname;
	private String name;
	private String phoneNumber;
	private String companyName;
	private String licenseNumber;
	private String account;
}
