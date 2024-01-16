package com.metanet.amatmu.businessman.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmRestaurantInfoDto {
	
	private long restId;
	private String restName;
	private String restKeyword;
	private String restOpenTime;
	private String restCloseTime;
	private String restPhoneNumber;
	private int restDeposit;
	private String restImg;
}
