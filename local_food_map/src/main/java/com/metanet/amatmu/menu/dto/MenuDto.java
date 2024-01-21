package com.metanet.amatmu.menu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDto {

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private String menuImg;
    private Long restId;
}
