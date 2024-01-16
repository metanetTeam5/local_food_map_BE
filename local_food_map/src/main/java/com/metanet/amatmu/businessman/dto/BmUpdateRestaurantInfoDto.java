package com.metanet.amatmu.businessman.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmUpdateRestaurantInfoDto {

	private long restId;
	private String restOpenTime;
	private String restCloseTime;
}
