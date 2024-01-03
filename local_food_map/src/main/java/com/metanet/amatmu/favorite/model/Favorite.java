package com.metanet.amatmu.favorite.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Favorite {
	private Long	favoId;
	private Long	membId;
	private Long	restId;
}

