package com.metanet.amatmu.reservation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmReservationDto {

	private Long resvId;
	private Long membId;
	private String membEmail;
	private int headCount;
	private String resvDate;
	private String phoneNumber;
	private String requirement;
}
