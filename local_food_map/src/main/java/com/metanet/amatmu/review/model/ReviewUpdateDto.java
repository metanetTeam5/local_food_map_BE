package com.metanet.amatmu.review.model;

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
public class ReviewUpdateDto {
	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
//	private Date	revwCreateDate;
	private String	revwImg;
	private Long	restId;
//	private Long	membId;
	private Long	resvId;
}
