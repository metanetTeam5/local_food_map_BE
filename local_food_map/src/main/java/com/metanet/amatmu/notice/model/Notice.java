package com.metanet.amatmu.notice.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Notice {
	private Long			notiId;
	private String			notiTitle;
	private String			notiContent;
	private LocalDateTime	notiCreateDate;
	private LocalDateTime	notiUpdateDate;
	private int				notiViews;
	private Long			membId;	
}
