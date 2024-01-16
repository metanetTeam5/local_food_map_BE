package com.metanet.amatmu.reservation.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationInsertDto {

	private Long restId;
	private String resvDate;
	private int resvHeadCount;
	private String resvHour;
	private String resvRequirement;
}
