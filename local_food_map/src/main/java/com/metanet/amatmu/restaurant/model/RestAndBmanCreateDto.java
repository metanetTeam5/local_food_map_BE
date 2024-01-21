package com.metanet.amatmu.restaurant.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestAndBmanCreateDto {
//Restaurant
	private String	restName;
	private double	restLocationX;
	private double	restLocationY;
	private String	restLocationName;
	private String	restKeyword;
	private String	restPhoneNumber;
	private String	restOpenTime;
	private String	restCloseTime;
	private String	restStation;
	private String	restCategory;
	private int		restMaxResv;
	private int		restDeposit;
	private String	restImg;
//Bman
	private String	companyName;
	private String	licenseNumber;
	private String	registration;
	private String	report;
	private String	bankbook;
	private String	residence;
	private String	account;
}