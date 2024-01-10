package com.metanet.amatmu.businessman.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateBmInfoDto {

	private Long businessmanId;
	private String licenseNumber;
	private String companyName;
	private String account;
}
