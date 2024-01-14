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
public class Restaurant {
	private Long	restId;
	private String	restName;
	private double	restLocationX;
	private double	restLocationY;
	private String	restLocationName;
	private String	restKeyword;
	private String	restPhoneNumber;
	private String	restOpenTime;
	private String	restCloseTime;
	private Date	restCreateDate;
	
	private String	restStation;
	private String	restCategory;
	private int		restMaxResv;
	
	private int		restDeposit;
}
