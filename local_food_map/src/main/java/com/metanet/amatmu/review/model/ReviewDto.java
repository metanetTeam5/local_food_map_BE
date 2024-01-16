package com.metanet.amatmu.review.model;

import java.sql.Date;

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
public class ReviewDto {
//	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
//	private Date	revwCreateDate;
	private String	revwImg;		//update 할때만 사
	private Long	restId;
//	private Long	membId;
//	private Long	resvId;
}
