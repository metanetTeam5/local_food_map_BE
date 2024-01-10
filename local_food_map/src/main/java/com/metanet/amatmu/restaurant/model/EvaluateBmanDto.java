package com.metanet.amatmu.restaurant.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EvaluateBmanDto {
	Long	businessmanId;
	String	eval;
	Date	grantDate;
}
