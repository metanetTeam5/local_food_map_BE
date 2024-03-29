package com.metanet.amatmu.review.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Review {
	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
	private Date	revwCreateDate;
	private String	revwImg;
	private Long	restId;
	private Long	membId;
	private Long	resvId;
}
