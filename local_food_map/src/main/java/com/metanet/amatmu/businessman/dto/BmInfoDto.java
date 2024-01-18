package com.metanet.amatmu.businessman.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmInfoDto {

	private Long businessmanId; 
	private String companyName;
	private String licenseNumber;
	private String registration;
	private String report;
	private String bankbook;
	private String residence;
	private Date createDate;
	private Date grantDate;
	private String account;
	private String status;
}
