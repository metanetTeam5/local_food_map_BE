package com.metanet.amatmu.menu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuRegisterDto {

	private String menuName;
	private int menuPrice;
	private String menuImg;
}
