package com.metanet.amatmu.reservation.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationResultDto {
	
	private Long resvId;
	private Date resvDate;
	private int resvHeadCount;
	private Date resvCreateDate;
	private String resvHour;
	private String resvStatus;
	private String resvRequirement;
	private int resvPayAmount;
	private Long membId;
	private Long restId;
	private String restName;
	private String restImg;
	private boolean isReviewCreated;
	private Long revwId;
}
