package com.metanet.amatmu.review.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewResultDto {

	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
	private Date	revwCreateDate;
	private String	revwImg;
	private Long	restId;
	private Long	membId;
	private Long	resvId;
	private String restName;
}
