package com.metanet.amatmu.businessman.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BmReviewDto {

	private Long	revwId;
	private double	revwStarRate;
	private String	revwContent;
	private Date	revwCreateDate;
	private String	revwImg;
	private String membEmail;
	private String membNickname;
}
