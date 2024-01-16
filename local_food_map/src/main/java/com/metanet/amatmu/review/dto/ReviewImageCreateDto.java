package com.metanet.amatmu.review.dto;

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
public class ReviewImageCreateDto {
//	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
//	private Date	revwCreateDate;
	private String	revwImg;
	private Long	restId;
//	private Long	membId;
//	private Long	resvId;

}
