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
public class RestaurantDto {
	//로그인한 멤버 아이디로 그 사람의 가게 리스트를 뿌려주고 그중에 고르게 하는식으로 id 값을 가져와야함. 
	private Long	businessmanId;
	
//	private Long	restId;
	private String	restName;
	private double	restLocationX;
	private double	restLocationY;
	private String	restLocationName;
	private String	restKeyword;
	private String	restPhoneNumber;
	private String	restOpenTime;
	private String	restCloseTime;
	private Date	restCreateDate;
	
	private String	restStation;
	private String	restCategory;
	private int		restMaxResv;
	private int		restDeposit;
}
