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
public class ReviewImageUpdateDto {
	private double	revwStarRate;
	private String	revwContent;

	private String	revwImg;

}
