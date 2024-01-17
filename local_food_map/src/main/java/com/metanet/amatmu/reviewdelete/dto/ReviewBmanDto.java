package com.metanet.amatmu.reviewdelete.dto;

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
public class ReviewBmanDto {
	private Long	rdelId;
	private Long	bmanId;
	private Long	revwId;
	private	String	revwContent;
	private double	revwStarRate;
}
